package study_test.aop;


import study.aop.After;
import study.aop.Aop;
import study.aop.CutPoint;
import study.factory.auto.MiniComponent;

import java.lang.reflect.Method;
import java.util.Arrays;

@MiniComponent
@Aop
public class ShowRunning {

    @After(path = "study.*/.*")
    void show(CutPoint point) {
        Class<?> klass = point.getClazz();
        Method method = point.getMethod();
        Object[] args = point.getArgs();
        Object result = point.getResult();
        Object target = point.getTarget();

        System.out.println(String.format("ShowRunning class:%s, method:%s, args:%s, result:%s, target:%s",
                klass.getName(), method.getName(), Arrays.toString(args), result, target));
    }
}
