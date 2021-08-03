package com.ccicnet.gd.customer.util;

import com.ccicnet.gd.customer.entity.base.TableColumnEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author lidelu
 */
@Slf4j
public class ClassUtil {
    /**
     * 从包package中获取所有的Class
     */
    public static void getClasses(String packageName, Class<?> baseClass, List<Class<?>> output) throws ClassNotFoundException, IOException {
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.name());
                log.info("load enums from path={}", filePath);
                findClassesInFile(packageName, filePath, baseClass, output);
            } else if ("jar".equals(protocol)) {
                JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                log.info("load enums from jar={}", jar.getName());
                findClassessInJar(packageName, jar, baseClass, output);
            }
        }
    }

    /**
     * 以文件的方式扫描整个包下的文件 并添加到集合中
     */
    private static void findClassessInJar(String packageName, JarFile jar, Class<?> baseClass, List<Class<?>> output) throws ClassNotFoundException {
        final String EXT = ".class";

        String packageDirName = packageName.replace('.', '/');
        Enumeration<JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.charAt(0) == '/') {
                name = name.substring(1);
            }

            //如果前半部分和定义的包名相同
            if (name.startsWith(packageDirName)) {
                int idx = name.lastIndexOf('/');
                //如果以"/"结尾 是一个包
                if (idx != -1) {
                    //获取包名 把"/"替换成"."
                    packageName = name.substring(0, idx).replace('/', '.');
                }

                if (name.endsWith(EXT) && !entry.isDirectory()) {
                    String className = name.substring(packageName.length() + 1, name.length() - EXT.length());
                    Class<?> clazz = Class.forName(packageName + '.' + className);
                    if (baseClass.isAssignableFrom(clazz)) {
                        output.add(clazz);
                    }
                }
            }
        }
    }

    /**
     * 以文件的形式来获取包下的所有Class
     */
    private static void findClassesInFile(String packageName, String packagePath, Class<?> baseClass, List<Class<?>> output) throws ClassNotFoundException {
        final String EXT = ".class";

        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
        File[] dirfiles = dir.listFiles(file -> file.isDirectory() || (file.getName().endsWith(EXT)));

        if (dirfiles == null) {
            return;
        }

        for (File file : dirfiles) {
            if (file.isDirectory()) {//如果是目录 则继续扫描
                findClassesInFile(packageName + "." + file.getName(), file.getAbsolutePath(), baseClass, output);
            } else { //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - EXT.length());
                Class<?> clazz = Class.forName(packageName + '.' + className);
                if (baseClass.isAssignableFrom(clazz)) {
                    output.add(clazz);
                }
            }
        }
    }

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 判断指定对象的指定属性 是否需要复制
     */
    private static boolean copyable(Object object, String propertyName, Object value) {
        if (ObjectUtils.isEmpty(value))
            return false;

        if (value instanceof CharSequence && StringUtils.isBlank((CharSequence) value))
            return false;

        return VALIDATOR.validateProperty(object, propertyName, Default.class).isEmpty();
    }

    /**
     * 从 org.springframework.beans.BeanUtils#copyProperties 改写而来。
     * spring 的 BeanUtils 有 properties 的 cache，所以复用 BeanUtils.getPropertyDescriptor 算法，性能较高。
     * 只复制不为空的并且有效的字段。
     */
    public static void copyProperties(Object source, Object target, @Nullable String... ignoreProperties) {
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target.getClass());
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);


        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);


                            if (!copyable(source, sourcePd.getName(), value)) {
                                log.debug("property: {}, value: {}, is not valid to copy.", sourcePd.getName(), value);
                                continue;
                            }

                            if (!TableColumnUtil.checkType(value,target.getClass(),targetPd)){
                                log.error("{}.{}, value: {}, Oracle type is not valid to copy.",
                                        source.getClass().getSimpleName(), sourcePd.getName(),value );
                                continue;
                            }
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }
}
