package study.aop.parser;

import lombok.SneakyThrows;

import java.lang.annotation.Annotation;

/**
 * 注解型类路径分析器
 * Created by taojinhou on 2019/11/25.
 */
public class AnnotationPathParser extends PathParser {
    private final Class<? extends Annotation> classAnnotation;

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public AnnotationPathParser(String path, MethodParser methodParser) {
        super(methodParser);
        path = path.replaceFirst("^@", "").replace("/", ".");
        this.classAnnotation = (Class<? extends Annotation>) Class.forName(path);
    }

    @Override
    protected boolean parseClass(Class<?> clazz) {
        return clazz.isAnnotationPresent(this.classAnnotation);
    }
}