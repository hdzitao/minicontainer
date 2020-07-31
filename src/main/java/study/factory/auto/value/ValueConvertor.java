package study.factory.auto.value;


import java.lang.reflect.Type;

/**
 * 把值转换成java对象
 * Created by taojinhou on 2020/3/19.
 */
public interface ValueConvertor {
    Object convert(Type type, String value);
}
