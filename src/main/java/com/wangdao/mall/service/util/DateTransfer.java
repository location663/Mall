/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/16
 * Time:22:43
 **/
package com.wangdao.mall.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTransfer {
    //将时间转化为
    public static Date dateToConfirmDate(Date fromDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse(fromDate.toString());
        return parse;
    }

    public static void main(String[] args) {

    }
}
