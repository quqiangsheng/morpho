package com.max256.morpho.common.util;


import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对象序列化
 * 使用jdk的对象流的方式序列化
 * 需要注意序列化id和序列化接口的设置
 *
 * 20170818更新 改为使用fst序列化 更高效的序列化效率 兼容jdk序列化 可以强行序列化没有实现序列化接口的对象
 * 20170825更新 保留jdk序列化
 * @author fbf
 * 
 */
public class ObjectStreamSerializeUtils {
	static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
	/**
	 * <p>
	 * Field log: slf4j日志
	 * </p>
	 */

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory
			.getLogger(ObjectStreamSerializeUtils.class);

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return 返回解析出来的对象 ，如果bytes为空返回null
	 */
	public static Object deserialize(byte[] bytes) {
		return fstDeserialize(bytes);
		/*if (isEmpty(bytes)) {
			return null;
		}
		ByteArrayInputStream byteStream = null;
		ObjectInputStream objectInputStream=null;
		try {
			 byteStream = new ByteArrayInputStream(bytes);
			 objectInputStream = new ObjectInputStream(
					byteStream);
			return objectInputStream.readObject();
		} catch (Exception e) {
			LOG.error("不能解析出对象Failed to deserialize", e);
		}finally {
			close(byteStream);
			close(objectInputStream);
		}
		return null;*/
	}

	/**
	 * fst反序列化
	 * 
	 * @param bytes
	 * @return 返回解析出来的对象 ，如果bytes为空返回null
	 */
	public static Object fstDeserialize(byte[] bytes) {
		if (isEmpty(bytes)) {
			return null;
		}
		// read
		Object object = conf.asObject(bytes);
		return object;
	}

	/**
	 * 判断反序列化时字节数组是否为空 因为序列化时可能会序列化为new byte[0] 所以反序列化时需要判断 如果为byte[0]代表业务意义上的为空
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isEmpty(byte[] data) {
		return ArrayUtils.isEmpty(data);
	}

	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		return fstSerialize(object);

		/*if (object == null) {
			return new byte[0];
		}
		ByteArrayOutputStream byteStream=null;
		ObjectOutputStream objectOutputStream=null;
		try {
			 byteStream = new ByteArrayOutputStream(128);
			if (!(object instanceof Serializable)) {
				throw new IllegalArgumentException(
						ObjectStreamSerializeUtils.class.getSimpleName()
								+ " requires a Serializable payload "
								+ "but received an object of type ["
								+ object.getClass().getName() + "]");
			}
			 objectOutputStream = new ObjectOutputStream(
					byteStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			return byteStream.toByteArray();
		} catch (Exception ex) {
			LOG.error("Failed to serialize", ex);
		}
		finally {
			close(byteStream);
			close(objectOutputStream);
		}
		// 声明一个长度为0的byte数组，开发中为了防止nullPointerException才这样做的
		return new byte[0];*/
	}

	/**
	 * fst序列化
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] fstSerialize(Object object) {
		if (object == null) {
			return new byte[0];
		}
		conf.setForceSerializable(true);
		return conf.asByteArray(object);

	
	}

	private ObjectStreamSerializeUtils() {
	}
	

	/**
	 * 序列化关闭流
	 * @param closeable
	 */
	@SuppressWarnings("unused")
	private static void close(Closeable closeable) {
		if (closeable != null)
			try {
				closeable.close();
			} catch (IOException e) {
				throw new RuntimeException("close object serialize stream error");
			}
	}
}
