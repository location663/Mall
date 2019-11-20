package com.wangdao.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-20 09:12
 **/

@Data
public class FootprintVO {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;

    private Integer id;

    private Integer goodsId;

    private String name;

    private Boolean deleted;

    private String picUrl;

    private String brief;

    private BigDecimal retailPrice;



    public FootprintVO() {
    }

    public FootprintVO(Date addTime, Integer id, Integer goodsId, String name, Boolean deleted, String picUrl, String brief, BigDecimal retailPrice) {
        this.addTime = addTime;
        this.id = id;
        this.goodsId = goodsId;
        this.name = name;
        this.deleted = deleted;
        this.picUrl = picUrl;
        this.brief = brief;
        this.retailPrice = retailPrice;
    }
}
