package com.max256.morpho.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtils {

	public static void compress2ZipFile(String zipFilePath,
			ArrayList<String> filesPathList, String filesBasePath,
			String encoding, String comment) {
		// 设置解压编码
		if (encoding == null || "".equals(encoding)) {
			encoding = "GBK";
		}
		File zipFile = new File(zipFilePath);
		if (zipFile.exists()) {
			zipFile.delete();
			zipFile = new File(zipFilePath);
		}
		ZipOutputStream zos = null;
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(zipFile));
			// 使用指定校验和创建输出流
			CheckedOutputStream cos = new CheckedOutputStream(bos, new CRC32());
			// 创建压缩流
			zos = new ZipOutputStream(cos);
			// 设置编码，支持中文
			zos.setEncoding(encoding);
			// 设置压缩包注释
			zos.setComment(comment);
			// 启用压缩
			zos.setMethod(ZipOutputStream.DEFLATED);
			// 设置压缩级别
			zos.setLevel(Deflater.BEST_SPEED);
			for (String filePath : filesPathList) {
				File file = new File(filePath);
				// 开始写入新的ZIP文件条目并将流定位到条目数据的开始处
				ZipEntry zipEntry = new ZipEntry(
						filePath.substring(filesBasePath.length()));
				zipEntry.setSize(file.length());
				zipEntry.setTime(file.lastModified());
				// 向压缩流中写入一个新的条目
				zos.putNextEntry(zipEntry);
				BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(file));
				byte[] buffer = new byte[1024 * 64];
				int readCount;
				while ((readCount = bis.read(buffer)) != -1) {
					zos.write(buffer, 0, readCount);
				}
				// 注，在使用缓冲流写压缩文件时，一个条件完后一定要刷新，不然可能有的内容就会存入到后面条目中去了
				zos.flush();
				zos.closeEntry();
				bis.close();
			}
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (IOException ex) {
			}
		}

	}

	/**
	 * 
	 * @param baseDir
	 */
	public static void createZip(String baseDir) {
		File folderObject = new File(baseDir);
		if (folderObject.isDirectory() && folderObject.exists()) {
			String folderName = folderObject.getName();
			File parentFolder = folderObject.getParentFile();
			if (parentFolder.exists()) {
				File Zipfile = new File(parentFolder, folderName + ".zip");
				createZip(folderObject.getAbsolutePath(),
						Zipfile.getAbsolutePath(), folderName);
			}
		}

	}

	/**
	 * zip压缩功能测试. 将d:\\temp\\zipout目录下的所有文件连同子目录压缩到d:\\temp\\out.zip.
	 * 
	 * @param baseDir
	 *            所要压缩的目录名（包含绝对路径）
	 * @param zipFilePath
	 *            压缩后的文件名
	 * @throws Exception
	 */
	public static void createZip(String baseDir, String zipFilePath,
			String comment) {
		File folderObject = new File(baseDir);

		if (folderObject.exists()) {
			List<File> fileList = getSubFiles(new File(baseDir));

			File zipFile = new File(zipFilePath);
			if (zipFile.exists()) {
				zipFile.delete();
				zipFile = new File(zipFilePath);
			}

			ZipOutputStream zos = null;
			try {
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(zipFile));
				// 使用指定校验和创建输出流
				CheckedOutputStream cos = new CheckedOutputStream(bos,
						new CRC32());
				// 创建压缩流
				zos = new ZipOutputStream(cos);
				// 设置编码，支持中文
				zos.setEncoding("GBK");
				// 设置压缩包注释
				zos.setComment(comment);
				// 启用压缩
				zos.setMethod(ZipOutputStream.DEFLATED);
				// 设置压缩级别
				zos.setLevel(Deflater.BEST_SPEED);
				for (File file : fileList) {
					// 开始写入新的ZIP文件条目并将流定位到条目数据的开始处
					ZipEntry zipEntry = new ZipEntry(
							file.getAbsolutePath()
									.substring(
											folderObject.getAbsolutePath()
													.length() + 1));
					zipEntry.setSize(file.length());
					zipEntry.setTime(file.lastModified());
					// 向压缩流中写入一个新的条目
					zos.putNextEntry(zipEntry);
					BufferedInputStream bis = new BufferedInputStream(
							new FileInputStream(file));
					byte[] buffer = new byte[1024 * 64];
					int readCount;
					while ((readCount = bis.read(buffer)) != -1) {
						zos.write(buffer, 0, readCount);
					}
					// 注，在使用缓冲流写压缩文件时，一个条件完后一定要刷新，不然可能有的内容就会存入到后面条目中去了
					zos.flush();
					zos.closeEntry();
					bis.close();
				}
			} catch (FileNotFoundException ex) {
			} catch (IOException ex) {
			} finally {
				try {
					if (zos != null) {
						zos.close();
					}
				} catch (IOException ex) {
				}
			}
		}
	}

	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 * 
	 * @param baseDir
	 *            File 指定的目录
	 * @return 包含java.io.File的List
	 */
	private static List<File> getSubFiles(File baseDir) {
		List<File> ret = new ArrayList<File>();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile()) {
				ret.add(tmp[i]);
			}
			if (tmp[i].isDirectory()) {
				ret.addAll(getSubFiles(tmp[i]));
			}
		}
		return ret;
	}

	public static void main(String[] args) {

		// ZipUtils.unzipZipFile("F:/A201509273992-ARC.zip", null,
		// "D:/A201509273992-ARC", "GBK");
		ZipUtils.createZip("F:\\output_dir\\SBK_2_41070001_711_2015104_3");

	}

	public static void unzipZipFile(String zipFilePath,
			ArrayList<String> filesPathList, String filesBasePath,
			String encoding) {
		// 设置解压编码
		if (encoding == null || "".equals(encoding)) {
			encoding = "GBK";
		}
		if (!new File(zipFilePath).exists()) {
			return;
		}
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(zipFilePath);
			System.out.println(zipFile.getEncoding());
			Enumeration<ZipEntry> entries = zipFile.getEntries();
			while (entries.hasMoreElements()) {
				ZipEntry zipEntry = entries.nextElement();
				String zipEntryName = zipEntry.getName();
				System.out.println(zipEntryName);
				long zipEntryCrc32 = zipEntry.getCrc();
				InputStream is = zipFile.getInputStream(zipEntry);
				BufferedInputStream bis = new BufferedInputStream(is);
				CRC32 crc32 = new CRC32();
				CheckedOutputStream cos = new CheckedOutputStream(
						new FileOutputStream(new File(filesBasePath + "/"
								+ zipEntryName)), new CRC32());
				BufferedOutputStream Buff = new BufferedOutputStream(cos);

				byte[] buffer = new byte[1024 * 100];
				int length = 0;
				while ((length = bis.read(buffer)) > 0) {
					Buff.write(buffer, 0, length);
					crc32.update(buffer, 0, length);
				}

				System.out.println(zipEntryCrc32 + ">>>>" + crc32.getValue()
						+ ">>>>" + cos.getChecksum().getValue());

				Buff.flush();
				Buff.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 * @throws java.util.zip.ZipException
	 * @Description: 解压文件
	 * @param zipPath
	 *            被压缩文件，请使用绝对路径
	 * @param targetPath
	 *            解压路径，解压后的文件将会放入此目录中，请使用绝对路径 默认为压缩文件的路径的父目录为解压路径
	 * @param encoding
	 *            解压编码
	 */
	public static void ZipFileDecompress(String zipPath, String targetPath,
			String encoding) throws FileNotFoundException, IOException {
		// 获取解缩文件
		File file = new File(zipPath);
		if (!file.isFile()) {
			throw new FileNotFoundException("要解压的文件不存在");
		}
		// 设置解压路径
		if (targetPath == null || "".equals(targetPath)) {
			targetPath = file.getParent();
		}
		// 设置解压编码
		if (encoding == null || "".equals(encoding)) {
			encoding = "GBK";
		}
		// 实例化ZipFile对象
		@SuppressWarnings("resource")
		ZipFile zipFile = new ZipFile(file, encoding);
		// 获取ZipFile中的条目
		Enumeration<ZipEntry> files = zipFile.getEntries();
		while (files.hasMoreElements()) {
			// 迭代中的每一个条目
			ZipEntry entry = files.nextElement();
			// 解压后的文件
			File outFile = new File(targetPath + File.separator
					+ entry.getName());
			// 创建目录
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
			// 如果条目为目录，则跳向下一个
			if (entry.getName().endsWith(File.separator)) {
				outFile.mkdirs();
				continue;
			}
			// 创建新文件
			outFile.createNewFile();
			// 如果不可写，则跳向下一个条目
			if (!outFile.canWrite()) {
				continue;
			}
			BufferedOutputStream bos = null;
			try (BufferedInputStream bis = new BufferedInputStream(
					zipFile.getInputStream(entry))) {
				bos = new BufferedOutputStream(new FileOutputStream(outFile));
				byte[] buffer = new byte[1024 * 64];
				int readCount;
				while ((readCount = bis.read(buffer)) != -1) {
					bos.write(buffer, 0, readCount);
				}
			} finally {
				if (bos != null) {
					bos.flush();
					bos.close();
				}

			}
		}
	}

	public ZipUtils() {

	}
}
