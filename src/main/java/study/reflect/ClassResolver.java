package study.reflect;

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
}
