package study.aop.aoprocessor;

import lombok.SneakyThrows;
import study.aop.Around;
import study.aop.AroundCutPoint;
import study.aop.CutPoint;
import study.reflect.ClassResolver;

import java.lang.reflect.Method;

/**
 * Aop Around 处理器
 */
public class AopAroundProcessor extends AopProcessor {
    public AopAroundProcessor(Around annotation) {
        super(annotation);
    }

    @Override
    @SneakyThrows
    protected Object invokeAop(Object aop, Method aopMethod, CutPoint point) {
        // 用CutPoint生成AroundCutPoint,AroundCutPoint可以自行决定执行原方法
        AroundCutPoint cutPoint = new AroundCutPoint(point);
        // 执行aop方法,返回aop方法的结果
        return ClassResolver.send(aopMethod, aop, cutPoint);
    }
}
