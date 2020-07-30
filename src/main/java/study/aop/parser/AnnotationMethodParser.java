package study.aop.parser;

import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 注解型方法路径分析器
 * Created by taojinhou on 2019/11/25.
 */
public class AnnotationMethodParser extends MethodParser {
    private final Class<? extends Annotation> methodAnnotation;

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public AnnotationMethodParser(String method) {
        method = method.replaceFirst("^@", "").replace("/", ".");
        this.methodAnnotation = (Class<? extends Annotation>) Class.forName(method);
    }

    @Override
    protected boolean parseMethod(Method zmethod) {
        return zmethod.isAnnotationPresent(this.methodAnnotation);
    }
}
