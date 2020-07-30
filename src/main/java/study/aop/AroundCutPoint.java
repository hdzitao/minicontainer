package study.aop;

import lombok.SneakyThrows;

public class AroundCutPoint extends CutPoint {
    public AroundCutPoint(CutPoint point) {
        super(point.getClazz(), point.getMethod(), point.getArgs(), point.getResult(), point.getTarget());
    }

    @SneakyThrows
    public Object join() {
        return this.method.invoke(this.target, this.args);
    }

    @SneakyThrows
    public Object join(Object... args) {
        return this.method.invoke(this.target, args);
    }
}
