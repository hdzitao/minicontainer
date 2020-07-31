package study.factory.auto.value.json;


import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import study.factory.auto.DefaultImplementation;
import study.factory.auto.MiniComponent;
import study.factory.auto.value.ValueConvertor;

import java.lang.reflect.Type;

/**
 * Created by taojinhou on 2020/3/20.
 */
@MiniComponent
@DefaultImplementation
public class JSONValueConverter implements ValueConvertor {
    @Override
    @SneakyThrows
    public Object convert(Type type, String value) {
        Object convert = JSON.parse(value);
        if (convert instanceof JSON) {
            convert = ((JSON) convert).toJavaObject(type);
        }

        return convert;
    }
}
