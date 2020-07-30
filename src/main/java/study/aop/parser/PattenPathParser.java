package study.aop.parser;

/**
 * 正则表达式类分析器
 * Created by taojinhou on 2019/11/25.
 */
public class PattenPathParser extends PathParser {
    private final String path;

    public PattenPathParser(String path, MethodParser methodParser) {
        super(methodParser);
        this.path = path.replace("/", "\\."); // 用 / 代替路径中的 .(.是正则表达式,要转义很麻烦)
    }

    @Override
    protected boolean parseClass(Class<?> clazz) {
        return clazz.getName().matches(this.path);
    }
}
