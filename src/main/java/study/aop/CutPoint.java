package study.aop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

@AllArgsConstructor
public class CutPoint {
    @Getter
    protected final Class<?> clazz;

    @Getter
    protected final Method method;

    @Getter
    protected final Object[] args;

    @Getter
    @Setter
    protected Object result;

    @Getter
    protected final Object target;
}
