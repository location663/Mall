package com.wangdao.mall.bean;

import lombok.Data;

@Data
public class CategoryVO {
    private Integer value;
    private String lable;

    public CategoryVO() {
    }

    public CategoryVO(Integer value, String lable) {
        this.value = value;
        this.lable = lable;
    }
}
