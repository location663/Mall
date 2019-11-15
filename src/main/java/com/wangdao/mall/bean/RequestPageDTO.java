package com.wangdao.mall.bean;

import lombok.Data;

@Data
public class RequestPageDTO {
    private Integer page;
    private Integer limit;
    private String sort;
    private String order;
    private Integer id;
    private String name;
}
