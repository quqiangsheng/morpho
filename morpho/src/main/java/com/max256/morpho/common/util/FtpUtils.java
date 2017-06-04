package com.max256.morpho.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.max256.morpho.common.exception.SystemException;

/**
 * ftp工具类
 * 
 */
public class FtpUtils {


	// 日志
	private static final Logger LOG = LoggerFactory.getLogger(FtpUtils.class);


	private static void uploadProcess(long localreadbytes, int c, long step,
			long process) {
		long lb = localreadbytes;
		lb += c;
		if (lb / step != process) {
			long pr = lb / step;
			LOG.info("上传进度:" + pr);
		}
	}

	/**
	 * <p>
	 * Field ftpClient: ftp客户端
	 * </p>
	 */
	private FTPClient ftpClient = null;

	/**
	 * <p>
	 * Field charSet: 字符集编码
	 * </p>
	 */
	private String charSet = "UTF-8";

	/**
	 * <p>
	 * Field remoteCharSet: 远端文件编码
	 * </p>
	 */
	private String remoteCharSet = "iso-8859-1";

	/**
	 * <p>
	 * Field byteSize: 字节数组长度
	 * </p>
	 */
	private int byteSize = 1024;

	/**
	 * <p>
	 * Field size100: 100长度
	 * </p>
	 */
	private int sizeL100 = 100;

	/**
	 * <p>
	 * Field size10: 10长度
	 * </p>
	 */
	private int sizeL10 = 10;

	/**
	 * <p>
	 * Field separated: /分隔符
	 * </p>
	 */
	private String separated = "/";

	/**
	 * <p>
	 * Description: 构造函数
	 * </p>
	 * 
	 * @param charSet
	 *            字符编码
	 */
	public FtpUtils(String charSet) {
		// 实例化FTP客户端
		this.ftpClient = new FTPClient();
		// 设置编码
		this.charSet = charSet;
	}

	/**
	 * <p>
	 * Description: 构造函数(代理的方式,当代理参数不存在的时候,实例化的为普通对象)
	 * </p>
	 * 
	 * @param charSet
	 *            字符编码
	 * @param proxyHost
	 *            代理地址
	 * @param porxyPort
	 *            代理端口
	 */
	public FtpUtils(String charSet, String proxyHost, String porxyPort) {

		// 判断是否有代理数据 实例化FTP客户端
		if (!StringUtils.isEmpty(proxyHost) && !StringUtils.isEmpty(porxyPort)) {

			// 实例化FTP客户端
			this.ftpClient = new FTPHTTPClient(proxyHost,
					new Integer(porxyPort));

		} else {

			// 实例化FTP客户端
			this.ftpClient = new FTPClient();

		}

		// 设置编码
		this.charSet = charSet;
	}

	/**
	 * <p>
	 * Description: 连接到FTP服务器
	 * </p>
	 * 
	 * @param hostname
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否连接成功
	 * @throws SystemException
	 */
	public boolean connect(String hostname, int port, String username,
			String password) throws SystemException {
		try {
			this.ftpClient.connect(hostname, port);
			this.ftpClient.setControlEncoding(this.charSet);
			if (FTPReply.isPositiveCompletion(this.ftpClient.getReplyCode())
					&& this.ftpClient.login(username, password)) {
				return true;
			}
			disconnect();
			return false;
		} catch (Exception e) {
			throw new SystemException("ftp连接时发生异常,异常信息:"+e.getMessage(),0);
		}
	}

	/**
	 * <p>
	 * Description: 递归创建远程服务器目录
	 * </p>
	 * 
	 * @param remote
	 *            远程服务器文件绝对路径
	 * @param fc
	 *            FTPClient对象
	 * @return 目录创建是否成功<br />
	 *         1:成功<br />
	 *         0:失败
	 * @throws SystemException
	 */
	private String createDirecroty(String remote, FTPClient fc)
			throws IOException {
		String status = "1";
		String directory = remote.substring(0,
				remote.lastIndexOf(this.separated) + 1);
		if (!this.separated.equalsIgnoreCase(directory)
				&& !fc.changeWorkingDirectory(new String(directory
						.getBytes(this.charSet), this.remoteCharSet))) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			status = this.createDirecroty(directory, fc, remote);
		}
		return status;
	}

	private String createDirecroty(String directory, FTPClient fc, String remote)
			throws IOException {
		int start = 0;
		int end;
		if (directory.startsWith(this.separated)) {
			start = 1;
		}
		end = directory.indexOf(this.separated, start);
		while (true) {
			String subDirectory = new String(remote.substring(start, end)
					.getBytes(this.charSet), this.remoteCharSet);
			if (!fc.changeWorkingDirectory(subDirectory)) {
				if (fc.makeDirectory(subDirectory)) {
					fc.changeWorkingDirectory(subDirectory);
				} else {
					LOG.info("创建目录失败");
					return "0";
				}
			}

			start = end + 1;
			end = directory.indexOf(this.separated, start);

			// 检查所有目录是否创建完毕
			if (end <= start) {
				break;
			}
		}
		return "1";
	}

	/**
	 * <p>
	 * Description: 断开与远程服务器的连接
	 * </p>
	 * 
	 * @throws IOException
	 *             异常
	 */
	public void disconnect() throws IOException {
		if (this.ftpClient.isConnected()) {
			this.ftpClient.disconnect();
		}
	}

	private String download(FTPFile[] files, String local, String remote)
			throws IOException {
		String result;
		// 获得远程文件的大小
		long lRemoteSize = files[0].getSize();

		// 获得本地文件对象(不存在,则创建)
		File f = new File(local);

		// 本地存在文件，进行断点下载
		if (f.exists()) {

			// 获得本地文件的长度
			long localSize = f.length();

			// 判断本地文件大小是否大于远程文件大小
			if (localSize >= lRemoteSize) {
				LOG.info("本地文件大于等于远程文件,无需下载,下载中止");
				result = "1";
			} else {
				result = this.downloaddd(localSize, f, remote, lRemoteSize);
			}
		} else {
			result = this.downloadCreate(f, remote, lRemoteSize);
		}
		return result;
	}

	/**
	 * <p>
	 * Description: 从FTP服务器上下载文件,支持断点续传，下载百分比汇报
	 * </p>
	 * 
	 * @param remote
	 *            远程文件路径
	 * @param local
	 *            本地文件路径
	 * @return 下载状态 <br />
	 *         1:下载成功<br />
	 *         0:下载失败<br />
	 *         -1:远程文件不存在<br />
	 * @throws SystemException
	 */
	public String download(String remote, String local) throws IOException {

		try {

			// 设置被动模式
			this.ftpClient.enterLocalPassiveMode();

			// 设置以二进制方式传输
			this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			// 返回状态
			String result;

			// 检查远程文件是否存在
			FTPFile[] files = this.ftpClient.listFiles(new String(remote
					.getBytes(this.charSet), this.remoteCharSet));
			if (files.length != 1) {
				LOG.info("远程文件不存在");
				result = "-1";
			} else {
				result = this.download(files, local, remote);
			}
			return result;
		} catch (IOException e) {
			throw e;
		}
	}

	private String downloadCreate(File f, String remote, long lRemoteSize)
			throws IOException {
		String result;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			// 获得流
			bos = new BufferedOutputStream(new FileOutputStream(f));
			bis = new BufferedInputStream(
					this.ftpClient.retrieveFileStream(new String(remote
							.getBytes(this.charSet), this.remoteCharSet)));

			// 远程文件长度
			long step = 0;
			step = lRemoteSize / this.sizeL100;

			// 进度
			long process = 0;
			long localSize = 0L;

			// 开始下载
			int c;
			byte[] bytes = null;
			bytes = new byte[this.byteSize];
			while ((c = bis.read(bytes)) != -1) {
				bos.write(bytes, 0, c);
				this.downloadProcess(localSize, process, c, step);
			}

		} finally {
			bis.close();
			bos.flush();
			bos.close();
		}

		// 获得下载状态
		boolean isDo = false;
		if (this.ftpClient.sendNoOp()) {
			isDo = this.ftpClient.completePendingCommand();
		}
		if (isDo) {
			result = "1";
		} else {
			result = "0";
		}
		return result;
	}

	private String downloaddd(long localSize, File f, String remote,
			long lRemoteSize) throws IOException {
		String result;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {

			// 设置开始点
			this.ftpClient.setRestartOffset(localSize);

			// 获得流(进行断点续传，并记录状态)
			bos = new BufferedOutputStream(new FileOutputStream(f, true));
			bis = new BufferedInputStream(
					this.ftpClient.retrieveFileStream(new String(remote
							.getBytes(this.charSet), this.remoteCharSet)));

			// 字节流
			byte[] bytes = null;
			bytes = new byte[this.byteSize];

			// 远程文件长度
			long step = 0;
			step = lRemoteSize / this.sizeL100;

			// 进度
			long process = 0;
			process = localSize / step;

			// 开始下载
			int c;
			while ((c = bis.read(bytes)) != -1) {
				bos.write(bytes, 0, c);
				this.downloadProcess(localSize, process, c, step);
			}

		} finally {
			bis.close();
			bos.flush();
			bos.close();
		}

		// 获得下载状态
		boolean isDo = false;
		if (this.ftpClient.sendNoOp()) {
			isDo = this.ftpClient.completePendingCommand();
		}
		if (isDo) {
			result = "1";
		} else {
			result = "0";
		}
		return result;
	}

	private void downloadProcess(long localSize, long process, int c, long step) {
		long ls = localSize;
		ls += c;
		long nowProcess = ls / step;
		if (nowProcess > process) {
			long pc = nowProcess;
			if (pc % this.sizeL10 == 0) {
				LOG.info("下载进度：" + pc);
			}
		}
	}

	/**
	 * <p>
	 * Description: 返回远程文件列表
	 * </p>
	 * 
	 * @param remotePath
	 *            路径
	 * @return 远程文件列表
	 * @throws SystemException
	 */
	public FTPFile[] getremoteFiles(String remotePath) throws SystemException {
		try {
			this.ftpClient.enterLocalPassiveMode(); // 设置被动模式
			this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 设置以二进制方式传输
			return this.ftpClient.listFiles(remotePath);
		} catch (Exception e) {
			throw new SystemException("从ftp得到文件列表时发生异常,异常信息:"+e.getMessage(),0);
		}
	}

	/**
	 * <p>
	 * Description: 删除ftp上的文件
	 * </p>
	 * 
	 * @param remotePath
	 *            路径
	 * @return 操作结果
	 * @throws IOException
	 *             异常
	 */
	public boolean removeFile(String remotePath) throws IOException {
		boolean flag = false;
		if (this.ftpClient != null) {

			// 设置被动模式
			this.ftpClient.enterLocalPassiveMode();

			// 设置以二进制方式传输
			this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			flag = this.ftpClient.deleteFile(new String(remotePath
					.getBytes(this.charSet), this.remoteCharSet));
		}
		return flag;
	}

	private String reUploadFile(String result, String remoteFileName, File f)
			throws SystemException {
		try {
			String rs = result;
			if ("-1".equals(rs)) {
				if (!this.ftpClient.deleteFile(remoteFileName)) {
					LOG.info("删除远端文件失败");
					return "-3";
				}
				rs = uploadFile(remoteFileName, f, this.ftpClient, 0);
			}
			return rs;
		} catch (Exception e) {
			throw new SystemException("ftp传输时发生异常,异常信息:"+e.getMessage(),0);
		}
	}

	/**
	 * <p>
	 * Description: 上传文件到FTP服务器，支持断点续传
	 * </p>
	 * 
	 * @param local
	 *            本地文件名称，绝对路径
	 * @param remote
	 *            远程文件路径，使用/home/directory1/subdirectory/file.ext或是
	 *            http://www.guihua.org/subdirectory/file.ext
	 *            按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
	 * @return 上传结果<br />
	 *         1:上传文件成功<br />
	 *         2:上传新文件成功<br />
	 *         0:创建服务器远程目录结构失败<br />
	 *         -1:上传文件失败<br />
	 *         -2:上传新文件失败 <br />
	 *         -3:删除远端文件失败<br />
	 * @throws SystemException
	 */
	public String upload(String local, String remote) throws SystemException {
		try {
			// 设置PassiveMode传输
			this.ftpClient.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			this.ftpClient.setControlEncoding(this.charSet);
			// 对远程目录的处理
			String remoteFileName = remote;
			if (remote.contains(this.separated)) {
				remoteFileName = remote.substring(remote
						.lastIndexOf(this.separated) + 1);
				// 创建服务器远程目录结构，创建失败直接返回
				if ("0".equals(this.createDirecroty(remote, this.ftpClient))) {
					LOG.info("创建服务器远程目录结构失败");
					return "0";
				}
			}
			// 返回状态
			String result;
			// 检查远程是否存在文件
			FTPFile[] files = this.ftpClient.listFiles(new String(
					remoteFileName.getBytes(this.charSet), this.remoteCharSet));
			if (files.length == 1) {
				long remoteSize = files[0].getSize();
				File f = new File(local);
				long localSize = f.length();
				if (remoteSize >= localSize) {
					LOG.info("远端文件大于等于本地文件大小,无需上传,终止上传");
					result = "1";
				} else {
					// 尝试移动文件内读取指针,实现断点续传
					result = uploadFile(remoteFileName, f, this.ftpClient,
							remoteSize);

					// 如果断点续传没有成功，则删除服务器上文件，重新上传
					result = this.reUploadFile(result, remoteFileName, f);
				}
			} else {
				result = uploadFile(remoteFileName, new File(local),
						this.ftpClient, 0);
			}
			return result;
		} catch (Exception e) {
			throw new SystemException("ftp传输时发生异常,异常信息:"+e.getMessage(),0);
		}
	}

	/**
	 * <p>
	 * Description: 上传文件到服务器,新上传和断点续传
	 * </p>
	 * 
	 * @param remoteFile
	 *            远程文件名，在上传之前已经将服务器工作目录做了改变
	 * @param localFile
	 *            本地文件File句柄，绝对路径
	 * @param fc
	 *            FTPClient引用
	 * @param remoteSize
	 *            需要显示的处理进度步进值
	 * @return 上传结果<br />
	 *         1:上传文件成功<br />
	 *         -1:上传文件失败<br />
	 *         2:上传新文件成功<br />
	 *         -2:上传新文件失败
	 * @throws SystemException
	 */
	private String uploadFile(String remoteFile, File localFile, FTPClient fc,
			long remoteSize) throws IOException {
		RandomAccessFile raf = null;
		BufferedOutputStream bos = null;
		try {
			// 显示进度的上传
			long step = 0;
			step = localFile.length() / this.sizeL100;
			long process = 0;
			long localreadbytes = 0L;
			raf = new RandomAccessFile(localFile, "r");
			bos = new BufferedOutputStream(fc.appendFileStream(new String(
					remoteFile.getBytes(this.charSet), this.remoteCharSet)),
					1024);// 最后一个参数是缓冲区大小

			// 断点续传
			if (remoteSize > 0) {
				fc.setRestartOffset(remoteSize);
				process = remoteSize / step;
				raf.seek(remoteSize);
				localreadbytes = remoteSize;
			}

			// 开始上传
			byte[] bytes = null;
			bytes = new byte[this.byteSize];
			int c;
			while (-1 != (c = raf.read(bytes))) {
				bos.write(bytes, 0, c);
				FtpUtils.uploadProcess(localreadbytes, c, step, process);
			}

			// 判断上传结果
			boolean result = false;
			String status = null;
			if (this.ftpClient.sendNoOp()) {
				result = this.ftpClient.completePendingCommand();
			}
			if (remoteSize > 0) {
				status = result ? "1" : "-1";
			} else {
				status = result ? "2" : "-2";
			}
			return status;
		} finally {
			if (bos != null) {
				bos.flush();
				bos.close();
			}
			if (raf != null) {
				raf.close();
			}
		}
	}
}
