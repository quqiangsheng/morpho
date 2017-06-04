package com.max256.morpho.common.util.system;

import java.text.DecimalFormat;

public class RuntimeInfo {
	
	private Runtime currentRuntime = Runtime.getRuntime();
	
	/**
	 * 获得运行时对象
	 * @return {@link Runtime}
	 */
	public final Runtime getRuntime(){
		return currentRuntime;
	}
	
	/**
	 * 获得JVM最大可用内存
	 * @return 最大可用内存
	 */
	public final long getMaxMemory(){
		return currentRuntime.maxMemory();
	}
	
	/**
	 * 获得JVM已分配内存
	 * @return 已分配内存
	 */
	public final long getTotalMemory(){
		return currentRuntime.totalMemory();
	}
	
	/**
	 * 获得JVM已分配内存中的剩余空间
	 * @return 已分配内存中的剩余空间
	 */
	public final long getFreeMemory(){
		return currentRuntime.freeMemory();
	}
	
	/**
	 * 获得JVM最大可用内存
	 * @return 最大可用内存
	 */
	public final long getUsableMemory(){
		return currentRuntime.maxMemory() - currentRuntime.totalMemory() + currentRuntime.freeMemory();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		SystemUtils.append(builder, "Max Memory:    ", readableFileSize(getMaxMemory()));
		SystemUtils.append(builder, "Total Memory:     ", readableFileSize(getTotalMemory()));
		SystemUtils.append(builder, "Free Memory:     ", readableFileSize(getFreeMemory()));
		SystemUtils.append(builder, "Usable Memory:     ", readableFileSize(getUsableMemory()));

		return builder.toString();
	}
	public static String readableFileSize(long size) {
		if (size <= 0) return "0";
		final String[] units = new String[] { "B", "kB", "MB", "GB", "TB", "EB" };
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
}
