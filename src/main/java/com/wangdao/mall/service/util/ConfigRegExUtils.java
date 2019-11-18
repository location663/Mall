package com.wangdao.mall.service.util;


import java.util.Map;
import java.util.Set;

/**
 * 后台配置模块正则判断
 */
public class ConfigRegExUtils {

    /**
     * 判断是否符合正则表达式
     * @param map
     * @return
     */
    public static int isConfigRegEx(Map<String, Object> map){
        Set<String> strings = map.keySet();
        for (String string : strings) {
            switch (string){
                case "litemall_wx_share":
                    break;
                case "litemall_mall_address":
                    String s1 = (String) map.get(string);
                    if ("".equals(s1.trim())){
                        return 601;
                    }
                    break;
                case "litemall_mall_name":
                    String s2 = (String) map.get(string);
                    if ("".equals(s2.trim())){
                        return 601;
                    }
                    break;
                case "litemall_mall_phone":
                    String s3 = (String) map.get(string);
                    boolean matches1 = s3.matches("^1[345789]\\d{9}$");
                    if (!matches1){
                        return 601;
                    }
                    break;
                case "litemall_mall_qq":
                    String s4 = (String) map.get(string);
                    boolean matches2 = s4.matches("[1-9][0-9]{4,14}");
                    if (!matches2){
                        return 601;
                    }
                    break;
                case "litemall_express_freight_min":
                    String s5 = (String) map.get(string);
                    boolean matches3 = s5.matches("^[0-9]+(.[0-9]{2})?$");
                    if (!matches3){
                        return 601;
                    }
                    break;
                case "litemall_express_freight_value":
                    String s6 = (String) map.get(string);
                    boolean matches4 = s6.matches("^[0-9]+(.[0-9]{2})?$");
                    if (!matches4){
                        return 601;
                    }
                    break;
                default:
                    String s7 = (String) map.get(string);
                    boolean matches5 = s7.matches("^[+]{0,1}(\\d+)$");
                    if (!matches5){
                        return 601;
                    }
                    break;
            }
        }
        return 0;
    }
}
