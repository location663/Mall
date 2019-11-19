package com.wangdao.mall.bean;

import lombok.Data;

@Data
public class SubGrouponDTO {
    private Integer orderId;
    private Integer userId;

    public SubGrouponDTO() {
    }

    public SubGrouponDTO(Integer orderId, Integer userId) {
        this.orderId = orderId;
        this.userId = userId;
    }
}
