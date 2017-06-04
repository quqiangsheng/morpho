package com.max256.morpho.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.max256.morpho.common.exception.SystemException;

/**
 * CmdUtils 执行操作系统命令 
 * @author fbf
 */
public class CmdUtils {

	// 日志
	private static final Logger LOG = LoggerFactory.getLogger(CmdUtils.class);
	/**
	 * 执行操作系统命令
	 * 
	 * @param command
	 *            命令
	 * @throws SystemException
	 */
	public void exe(String command) throws SystemException {
		Process child = null;
		BufferedReader reader = null;
		try {
			Runtime rt = Runtime.getRuntime();
			child = rt.exec(command);

			// 以下代码为控制台输出相关的批出理
			String line = null;
			reader = new BufferedReader(new InputStreamReader(
					child.getInputStream(), "UTF-8"));
			while ((line = reader.readLine()) != null) {
				LOG.info(line);
			}

			// 等待刚刚执行的命令的结束
			while (true) {
				if (child.waitFor() == 0) {
					break;
				}
			}
		} catch (Exception e) {
			throw new SystemException("执行cmd命令时发生异常,异常信息:"+e.getMessage(),0);
		} finally {
			child.destroy();
			try {
				reader.close();
			} catch (IOException e) {
				LOG.error("exe:", e);
			}
		}
	}
}
