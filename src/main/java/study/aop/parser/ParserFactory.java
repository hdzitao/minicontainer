package study.aop.parser;

/**
 * 分析器工厂
 * <p>
 * Created by taojinhou on 2019/11/25.
 */
public class ParserFactory {
    public static PathParser getPathParser(String path, MethodParser methodParser) {
        if (path.startsWith("@")) {
            return new AnnotationPathParser(path, methodParser);
        } else {
            return new PattenPathParser(path, methodParser);
        }
    }

    public static MethodParser getMethodParser(String method) {
        if (method.startsWith("@")) {
            return new AnnotationMethodParser(method);
        } else {
            return new PattenMethodParser(method);
        }
    }
}
