package study.aop.parser;

import java.lang.reflect.Method;

/**
 * 正则表达式方法路径分析器
 * Created by taojinhou on 2019/11/25.
 */
public class PattenMethodParser extends MethodParser {
    private final String method;

    public PattenMethodParser(String method) {
        this.method = method;
    }

    @Override
    protected boolean parseMethod(Method zmethod) {
        return zmethod.getName().matches(this.method);
    }
}
