package study.aop;

import lombok.SneakyThrows;
import study.reflect.ClassResolver;

public class AroundCutPoint extends CutPoint {
    public AroundCutPoint(CutPoint point) {
        super(point.getClazz(), point.getMethod(), point.getArgs(), point.getResult(), point.getTarget());
    }

    @SneakyThrows
    public Object join() {
        return ClassResolver.send(this.method, this.target, this.args);
    }

    @SneakyThrows
    public Object join(Object... args) {
        return ClassResolver.send(this.method, this.target, args);
    }
}
