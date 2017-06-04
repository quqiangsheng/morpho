package com.max256.morpho.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * FreeMarker的工具类
 * 
 * 
 */
public class FreeMarkerUtils {

	// 日志
	private static final Logger logger = LoggerFactory.getLogger(FreeMarkerUtils.class);


	/** 是否已初始化 */
	private static boolean isInit = false;

	/** 应用所在路径 */
	private static String appPath = null;

	/** 编码格式 UTF-8 */
	private static final String ENCODING = "UTF-8";

	/** FreeMarker配置 */
	@SuppressWarnings("deprecation")
	private static Configuration config = new Configuration();

	/** 路径分割符 */
	public static final String PATH_SEPARATOR = "/";

	/**
	 * 据数据及模板生成文件
	 * 
	 * @param data
	 *            一个Map的数据结果集
	 * @param templateFileName
	 *            ftl模版路径(已默认为WEB-INF/template,文件名相对此路径)
	 * @param outFileName
	 *            生成文件名称(可带路径)
	 * @param isAbsPath
	 *            是否绝对路径
	 */
	@SuppressWarnings("deprecation")
	public static void crateFile(Map<String, Object> data,
			String templateFileName, String outFileName, boolean isAbsPath) {
		if (!isInit) {
			System.out
					.println("FreeMarker模板引擎未初始化,请确认已经调用initFreeMarker()方法对其进行了初始化");
		}
		Writer out = null;
		try {
			// 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
			Template template = config.getTemplate(templateFileName, ENCODING);
			template.setEncoding(ENCODING);

			// 生成文件路径
			// 如果是绝对路径则直接使用
			if (isAbsPath) {
				outFileName = new StringBuffer(outFileName).toString();
			} else {
				// 相对路径则使用默认的appPath加上输入的文件路径
				outFileName = new StringBuffer(appPath).append(File.separator)
						.append(outFileName).toString();
			}
			String outPath = outFileName.substring(0,
					outFileName.lastIndexOf(PATH_SEPARATOR));
			// 创建文件目录
			FileUtils.forceMkdir(new File(outPath));
			File outFile = new File(outFileName);
			out = new OutputStreamWriter(new FileOutputStream(outFile),
					ENCODING);

			// 处理模版
			template.process(data, out);

			out.flush();
			logger.info("由模板文件" + templateFileName + "生成" + outFileName + "成功.");
		} catch (Exception e) {
			logger.error("由模板文件" + templateFileName + "生成" + outFileName
					+ "出错.", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("关闭Write对象出错", e);
			}
		}
	}

	/**
	 * 初始化FreeMarker配置
	 * 
	 * @param applicationPath
	 *            应用所在路径
	 */
	public static void initFreeMarker(String applicationPath) {
		if (!(isInit)) {
			try {
				appPath = applicationPath;
				// 加载模版
				File file = new File(new StringBuffer(appPath).append(
						File.separator).toString());
				// 设置要解析的模板所在的目录，并加载模板文件
				config.setDirectoryForTemplateLoading(file);
				// 设置文件编码为UTF-8
				config.setEncoding(Locale.getDefault(), ENCODING);
				isInit = true;
			} catch (IOException e) {
				logger.error("初始化FreeMarker配置出错", e);
			}
		}
	}

}
