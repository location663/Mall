package com.wangdao.mall.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-20 14:39
 **/

public class StringArray2StringConverter implements Converter<String[], String> {
    @Override
    public String convert(String[] strings) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");

        for (String string : strings) {
            stringBuffer.append("\"").append(string).append("\",");
        }
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
