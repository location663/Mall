package com.wangdao.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class RequestPageDTO {
    private Integer page;
    private Integer limit;
    private Integer size;
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
    private Integer goodsId;

    public RequestPageDTO() {
    }

    public RequestPageDTO(Integer page, Integer limit, String sort, String order, Integer id, String name, Integer userId, String orderSn, List<Short> orderStatusArray, String question, String keyword, String url) {
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        this.order = order;
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.orderSn = orderSn;
        this.orderStatusArray = orderStatusArray;
        this.question = question;
        this.keyword = keyword;
        this.url = url;
    }
}
