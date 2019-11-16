package com.wangdao.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderStatisticsDTO {
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date day;

    private Double amount;

    private Integer customers;

    private Integer orders;

    private Double pcr;
}
