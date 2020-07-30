package study.factory.auto;


import study.factory.BeanFactory;

import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * Created by taojinhou on 2020/3/16.
 */
public interface InjectInfo {
    Object getBean(BeanFactory factory);

    static Object[] getParameterBeans(Executable executable, BeanFactory factory) {
        Parameter[] parameters = executable.getParameters();
        return Arrays.stream(parameters)
                .map(parameter -> parameter.isAnnotationPresent(Value.class)
                        ? ValueInfo.forParameter(parameter)
                        : AutoInfo.forParameter(parameter))
                .map(injectInfo -> injectInfo.getBean(factory))
                .toArray();
    }
}
