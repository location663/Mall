package com.wangdao.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class CategoryGoodsVO {

    private String name;
    private Integer id;
    private List<GoodsDO> goodsDOList;
}
