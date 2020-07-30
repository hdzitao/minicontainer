package study.factory.auto.value.json;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import study.factory.auto.MiniComponent;
import study.factory.auto.value.ValueConvertor;

import java.util.List;

/**
 * todo 随便写写不知道好不好用
 * Created by taojinhou on 2020/3/20.
 */
@MiniComponent
public class JSONValueConverter implements ValueConvertor {
    @Override
    public Object convert(Class<?> type, String value) {
        Object convert = JSON.parse(value);
        if (Object[].class.isAssignableFrom(type)) {
            convert = ((JSONArray) convert).toArray();
        } else if (List.class.isAssignableFrom(type)) {
            convert = ((JSONArray) convert).toJavaList(Object.class);
        } else if (convert instanceof JSONObject) {
            convert = ((JSONObject) convert).toJavaObject(type);
        }

        return convert;
    }
}
