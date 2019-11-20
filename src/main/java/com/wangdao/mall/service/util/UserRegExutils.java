package com.wangdao.mall.service.util;

/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/18
 * Time:17:15
 **/

public class UserRegExutils {
    public static int userReg(String string) {
        if (string == null) {
            return 0;
        } else if ("".equals(string.trim())) {
            return 601;
        } else if (!string.matches("^[1-9]\\d*$")) {
            return 602;
        }
        return 0;
    }
}
