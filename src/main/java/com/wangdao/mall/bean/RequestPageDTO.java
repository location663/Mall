package com.wangdao.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class RequestPageDTO {
    private Integer page;
    private Integer limit;
    private String sort;
    private String order;
    private Integer id;
    private String name;
    private Integer userId;
    private String orderSn;
    private List<Short> orderStatusArray;
    private String question;
    private String keyword;
    private String url;
}
