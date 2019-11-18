package com.wangdao.mall.bean;

import lombok.Data;

@Data
public class CategoryVO {
    private Integer value;
    private String label;

    public CategoryVO() {
    }

    public CategoryVO(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
