package com.wangdao.mall.bean;

import lombok.Data;

@Data
public class OrderHandleOption {

    private Boolean cancel;

    private Boolean comment;

    private Boolean confirm;

    private Boolean delete;

    private Boolean pay;

    private Boolean rebuy;

    private Boolean refund;


    public OrderHandleOption() {
    }

    public OrderHandleOption(Boolean cancel, Boolean comment, Boolean confirm, Boolean delete, Boolean pay, Boolean rebuy, Boolean refund) {
        this.cancel = cancel;
        this.comment = comment;
        this.confirm = confirm;
        this.delete = delete;
        this.pay = pay;
        this.rebuy = rebuy;
        this.refund = refund;
    }
}
