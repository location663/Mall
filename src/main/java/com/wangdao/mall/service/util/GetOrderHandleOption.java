package com.wangdao.mall.service.util;

import com.wangdao.mall.bean.OrderHandleOption;

public class GetOrderHandleOption {

    public static OrderHandleOption getOrderHandleOption(Integer orderStatus){
        OrderHandleOption handleOption = null;
        switch (orderStatus){
            case 101:
                handleOption = new OrderHandleOption(true,false,false,false,true,false,false);
                break;
            case 102:
                handleOption = new OrderHandleOption(false,false,false,true,false,false,false);
                break;
            case 103:
                handleOption = new OrderHandleOption(false,false,false,true,false,false,false);
                break;
            case 201:
                handleOption = new OrderHandleOption(false,false,false,false,false,true,true);
                break;
            case 202:
                handleOption = new OrderHandleOption(false,false,false,false,false,true,false);
                break;
            case 203:
                handleOption = new OrderHandleOption(false,false,false,false,false,true,false);
                break;
            case 301:
                handleOption = new OrderHandleOption(false,false,true,false,false,false,true);
                break;
            case 401:
                handleOption = new OrderHandleOption(false,true,false,true,false,true,false);
                break;
            case 402:
                handleOption = new OrderHandleOption(false,true,false,true,false,true,false);
                break;
        }
        return handleOption;
    }
}
