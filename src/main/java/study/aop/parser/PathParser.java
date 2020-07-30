package study.aop.parser;


import study.aop.Aop;
import study.factory.BeanConfigure;

import java.lang.reflect.Method;

/**
 * 类分析器
 * Created by taojinhou on 2019/11/25.
 */
public abstract class PathParser {
    protected final MethodParser methodParser;

    public PathParser(MethodParser methodParser) {
        this.methodParser = methodParser;
    }

    public boolean parse(BeanConfigure configure) {
        Class<?> beanClass = configure.getBeanClass();

        if (beanClass.isInterface() // 跳过接口
                || beanClass.isAnnotationPresent(Aop.class) // 跳过aop自己
                || beanClass.getName().startsWith("study.") // 跳过微容器包
        ) {
            return false;
        }

        if (parseClass(beanClass)) { // class是否命中
            for (Method method : beanClass.getMethods()) { // 检查是否有命中aop的方法
                if (this.methodParser.parse(method)) {
                    return true;
                }
            }
        }

        return false;
    }

    protected abstract boolean parseClass(Class<?> beanClass);
}
