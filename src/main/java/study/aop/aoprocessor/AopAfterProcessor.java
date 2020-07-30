package study.aop.aoprocessor;

import lombok.SneakyThrows;
import study.aop.After;
import study.aop.CutPoint;
import study.reflect.ClassResolver;

import java.lang.reflect.Method;

/**
 * Aop After 处理器
 */
public class AopAfterProcessor extends AopProcessor {
    public AopAfterProcessor(After annotation) {
        super(annotation);
    }

    @Override
    @SneakyThrows
    protected Object invokeAop(Object aop, Method aopMethod, CutPoint point) {
        // 执行原方法并设置结果
        point.setResult(ClassResolver.send(point.getMethod(), point.getTarget(), point.getArgs()));
        // 执行aop方法
        ClassResolver.send(aopMethod, aop, point);
        // 返回结果,必须用 point.getResult() ,这样aop方法可以修改结果
        return point.getResult();
    }
}
