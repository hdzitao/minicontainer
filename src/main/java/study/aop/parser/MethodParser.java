package study.aop.parser;

import java.lang.reflect.Method;

/**
 * 方法分析器
 * Created by taojinhou on 2019/11/25.
 */
public abstract class MethodParser {
    public boolean parse(Method method) {
        if (method.getDeclaringClass().equals(Object.class)) { // 不aop Object的方法
            return false;
        }

        return parseMethod(method);
    }

    protected abstract boolean parseMethod(Method method);
}
