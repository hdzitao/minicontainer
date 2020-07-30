package study.aop.aoprocessor;

import lombok.SneakyThrows;
import study.aop.Before;
import study.aop.CutPoint;
import study.reflect.ClassResolver;

import java.lang.reflect.Method;

/**
 * Aop Before 处理器
 */
public class AopBeforeProcessor extends AopProcessor {
    public AopBeforeProcessor(Before annotation) {
        super(annotation);
    }

    @Override
    @SneakyThrows
    protected Object invokeAop(Object aop, Method aopMethod, CutPoint point) {
        // 执行aop方法
        ClassResolver.send(aopMethod, aop, point);
        // 执行原方法并返回
        return ClassResolver.send(point.getMethod(), point.getTarget(), point.getArgs());
    }
}
