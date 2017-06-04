package com.max256.morpho.common.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取Class信息的工具类
 * 
 * 1. 取shortClassName 和 packageName(via Common Lang)
 * 
 * 2. 获取全部父类，全部接口(via Common Lang)，以及最全面的获取全部Annotation(自写)
 * 
 * 3. 获取标注了annotation的所有属性和方法(最全面的循环基类和接口)
 * 
 * 3. 取得cglib之前的用户类(from Spring)
 * 
 * 4. 获取类用泛型声明的Class实例.
 * 
 * 
 */
public class ClassUtil {

	/**
	 * 获取某包下（包括该包的所有子包）所有类
	 * 
	 * @param packageName
	 *            包名
	 * @return 类的完整名称
	 */
	public static List<String> getClassName(String packageName) {
		return getClassName(packageName, true);
	}

	/**
	 * 获取某包下所有类
	 * 
	 * @param packageName
	 *            包名
	 * @param childPackage
	 *            是否遍历子包
	 * @return 类的完整名称
	 */
	public static List<String> getClassName(String packageName,
			boolean childPackage) {
		List<String> fileNames = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String packagePath = packageName.replace(".", "/");
		URL url = loader.getResource(packagePath);
		if (url != null) {
			String type = url.getProtocol();
			if (type.equals("file")) {
				fileNames = getClassNameByFile(url.getPath(), null,
						childPackage);
			} else if (type.equals("jar")) {
				fileNames = getClassNameByJar(url.getPath(), childPackage);
			}
		} else {
			fileNames = getClassNameByJars(((URLClassLoader) loader).getURLs(),
					packagePath, childPackage);
		}
		return fileNames;
	}

	/**
	 * 从项目文件获取某包下所有类
	 * 
	 * @param filePath
	 *            文件路径
	 * @param className
	 *            类名集合
	 * @param childPackage
	 *            是否遍历子包
	 * @return 类的完整名称
	 */
	private static List<String> getClassNameByFile(String filePath,
			List<String> className, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				if (childPackage) {
					myClassName.addAll(getClassNameByFile(childFile.getPath(),
							myClassName, childPackage));
				}
			} else {
				String childFilePath = childFile.getPath();
				if (childFilePath.endsWith(".class")) {
					childFilePath = childFilePath.replaceAll("/", "\\\\");
					childFilePath = childFilePath.substring(
							childFilePath.indexOf("\\classes") + 9,
							childFilePath.lastIndexOf("."));
					childFilePath = childFilePath.replace("\\", ".");
					myClassName.add(childFilePath);
				}
			}
		}

		return myClassName;
	}

	/**
	 * 从jar获取某包下所有类
	 * 
	 * @param jarPath
	 *            jar文件路径
	 * @param childPackage
	 *            是否遍历子包
	 * @return 类的完整名称
	 */
	private static List<String> getClassNameByJar(String jarPath,
			boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		String[] jarInfo = jarPath.split("!");
		String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
		String packagePath = jarInfo[1].substring(1);
		try {
			@SuppressWarnings("resource")
			JarFile jarFile = new JarFile(jarFilePath);
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jarEntry = entrys.nextElement();
				String entryName = jarEntry.getName();
				if (entryName.endsWith(".class")) {
					if (childPackage) {
						if (entryName.startsWith(packagePath)) {
							entryName = entryName.replace("/", ".").substring(
									0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					} else {
						int index = entryName.lastIndexOf("/");
						String myPackagePath;
						if (index != -1) {
							myPackagePath = entryName.substring(0, index);
						} else {
							myPackagePath = entryName;
						}
						if (myPackagePath.equals(packagePath)) {
							entryName = entryName.replace("/", ".").substring(
									0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myClassName;
	}

	/**
	 * 从所有jar中搜索该包，并获取该包下所有类
	 * 
	 * @param urls
	 *            URL集合
	 * @param packagePath
	 *            包路径
	 * @param childPackage
	 *            是否遍历子包
	 * @return 类的完整名称
	 */
	private static List<String> getClassNameByJars(URL[] urls,
			String packagePath, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		if (urls != null) {
			for (int i = 0; i < urls.length; i++) {
				URL url = urls[i];
				String urlPath = url.getPath();
				// 不必搜索classes文件夹
				if (urlPath.endsWith("classes/")) {
					continue;
				}
				String jarPath = urlPath + "!/" + packagePath;
				myClassName.addAll(getClassNameByJar(jarPath, childPackage));
			}
		}
		return myClassName;
	}

	public static void main(String[] args) throws Exception {
		String packageName = "com.lanyuan.entity";
		// List<String> classNames = getClassName(packageName);
		List<String> classNames = getClassName(packageName, false);
		if (classNames != null) {
			for (String className : classNames) {
				System.out.println(className);
			}
		}
	}
	private static final String CGLIB_CLASS_SEPARATOR = "$$";

	private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

	/**
	 * 返回Class名, 不包含PackageName.
	 * 
	 * 内部类的话，返回"主类.内部类"
	 */
	public static String getShortClassName(final Class<?> cls) {
		return ClassUtil.getShortClassName(cls);
	}

	/**
	 * 返回Class名，不包含PackageName
	 * 
	 * 内部类的话，返回"主类.内部类"
	 */
	public static String getShortClassName(final String className) {
		return ClassUtil.getShortClassName(className);
	}

	/**
	 * 返回PackageName
	 */
	public static String getPackageName(final Class<?> cls) {
		return ClassUtil.getPackageName(cls);
	}

	/**
	 * 返回PackageName
	 */
	public static String getPackageName(final String className) {
		return ClassUtil.getPackageName(className);
	}

	/**
	 * 递归返回所有的SupperClasses，包含Object.class
	 */
	public static List<Class<?>> getAllSuperclasses(final Class<?> cls) {
		return ClassUtil.getAllSuperclasses(cls);
	}

	/**
	 * 递归返回本类及所有基类继承的接口，及接口继承的接口，比Spring中的相同实现完整
	 */
	public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
		return ClassUtil.getAllInterfaces(cls);
	}

	/**
	 * 递归Class所有的Annotation，一个最彻底的实现.
	 * 
	 * 包括所有基类，所有接口的Annotation，同时支持Spring风格的Annotation继承的父Annotation，
	 */
	public static Set<Annotation> getAllAnnotations(final Class<?> cls) {
		List<Class<?>> allTypes = getAllSuperclasses(cls);
		allTypes.addAll(getAllInterfaces(cls));
		allTypes.add(cls);

		Set<Annotation> anns = new HashSet<Annotation>();
		for (Class<?> type : allTypes) {
			anns.addAll(Arrays.asList(type.getDeclaredAnnotations()));
		}

		Set<Annotation> superAnnotations = new HashSet<Annotation>();
		for (Annotation ann : anns) {
			getSupperAnnotations(ann.annotationType(), superAnnotations);
		}

		anns.addAll(superAnnotations);

		return anns;
	}

	private static <A extends Annotation> void getSupperAnnotations(Class<A> annotationType, Set<Annotation> visited) {
		Annotation[] anns = annotationType.getDeclaredAnnotations();

		for (Annotation ann : anns) {
			if (!ann.annotationType().getName().startsWith("java.lang") && visited.add(ann)) {
				getSupperAnnotations(ann.annotationType(), visited);
			}
		}
	}

	/**
	 * 找出所有标注了该annotation的公共属性，循环遍历父类.
	 * 
	 * 暂未支持Spring风格Annotation继承Annotation
	 * 
	 * from org.unitils.util.AnnotationUtils
	 */
	public static <T extends Annotation> Set<Field> getPublicFieldsAnnotatedWith(Class<? extends Object> clazz,
			Class<T> annotation) {

		if (Object.class.equals(clazz)) {
			return Collections.emptySet();
		}

		Set<Field> annotatedFields = new HashSet<Field>();
		Field[] fields = clazz.getFields();

		for (Field field : fields) {
			if (field.getAnnotation(annotation) != null) {
				annotatedFields.add(field);
			}
		}

		return annotatedFields;
	}

	/**
	 * 找出所有标注了该annotation的属性，循环遍历父类，包含private属性.
	 * 
	 * 暂未支持Spring风格Annotation继承Annotation
	 * 
	 * from org.unitils.util.AnnotationUtils
	 */
	public static <T extends Annotation> Set<Field> getFieldsAnnotatedWith(Class<? extends Object> clazz,
			Class<T> annotation) {
		if (Object.class.equals(clazz)) {
			return Collections.emptySet();
		}
		Set<Field> annotatedFields = new HashSet<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getAnnotation(annotation) != null) {
				annotatedFields.add(field);
			}
		}
		annotatedFields.addAll(getFieldsAnnotatedWith(clazz.getSuperclass(), annotation));
		return annotatedFields;
	}

	/**
	 * 找出所有标注了该annotation的公共方法(含父类的公共函数)，循环其接口.
	 * 
	 * 暂未支持Spring风格Annotation继承Annotation
	 */
	public static <T extends Annotation> Set<Method> getPublicMethodsAnnotatedWith(Class<?> clazz,
			Class<T> annotation) {
		// 已递归到Objebt.class, 停止递归
		if (Object.class.equals(clazz)) {
			return Collections.emptySet();
		}

		List<Class<?>> ifcs = ClassUtil.getAllInterfaces(clazz);
		Set<Method> annotatedMethods = new HashSet<Method>();

		// 遍历当前类的所有公共方法
		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			// 如果当前方法有标注，或定义了该方法的所有接口有标注
			if (method.getAnnotation(annotation) != null || searchOnInterfaces(method, annotation, ifcs)) {
				annotatedMethods.add(method);
			}
		}

		return annotatedMethods;
	}

	private static <T extends Annotation> boolean searchOnInterfaces(Method method, Class<T> annotationType,
			List<Class<?>> ifcs) {
		for (Class<?> iface : ifcs) {
			try {
				Method equivalentMethod = iface.getMethod(method.getName(), method.getParameterTypes());
				if (equivalentMethod.getAnnotation(annotationType) != null) {
					return true;
				}
			} catch (NoSuchMethodException ex) {
				// Skip this interface - it doesn't have the method...
			}
		}
		return false;
	}

	/**
	 * 获取CGLib处理过后的实体的原Class.
	 */
	public static Class<?> unwrapCglib(Object instance) {
		Validate.notNull(instance, "Instance must not be null");
		Class<?> clazz = instance.getClass();
		if ((clazz != null) && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if ((superClass != null) && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;
	}

	////////////////

	/**
	 * 通过反射, 获得Class定义中声明的泛型参数的类型,
	 * 
	 * 注意泛型必须定义在父类处. 这是唯一可以通过反射从泛型获得Class实例的地方.
	 * 
	 * 如无法找到, 返回Object.class.
	 * 
	 * eg. public UserDao extends HibernateDao<User>
	 * 
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Class<T> getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 
	 * 注意泛型必须定义在父类处. 这是唯一可以通过反射从泛型获得Class实例的地方.
	 * 
	 * 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration, start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if ((index >= params.length) || (index < 0)) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}
}