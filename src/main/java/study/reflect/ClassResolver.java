package study.reflect;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by taojinhou on 2020/7/28.
 */
public final class ClassResolver {
    private ClassResolver() {
    }

    /**
     * 全部字段,可以加过滤条件
     *
     * @param clazz
     * @return
     */
    public static Field[] getFields(Class<?> clazz) {
        Class<?> current = clazz;
        List<Field> fields = new LinkedList<>();
        do {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
        } while ((current = current.getSuperclass()) != null);

        return fields.toArray(new Field[0]);
    }

    /**
     * 全部方法,可以加过滤条件
     *
     * @param clazz
     * @return
     */
    public static Method[] getMethods(Class<?> clazz) {
        // 添加所有public方法
        Set<Method> methods = new LinkedHashSet<>();
        Class<?> current = clazz;
        do {
            methods.addAll(Arrays.asList(current.getDeclaredMethods()));
        } while ((current = current.getSuperclass()) != null);
        methods.addAll(Arrays.asList(clazz.getMethods())); // interface default method
        return methods.toArray(new Method[0]);
    }

    @SneakyThrows
    public static void set(Field field, Object bean, Object value) {
        field.setAccessible(true);
        field.set(bean, value);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T get(Field field, Object bean) {
        field.setAccessible(true);
        return (T) field.get(bean);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T send(Method method, Object bean, Object... args) {
        method.setAccessible(true);
        return (T) method.invoke(bean, args);
    }
}
