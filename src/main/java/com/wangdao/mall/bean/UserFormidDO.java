package com.wangdao.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class UserFormidDO {
    private Integer id;

    private String formid;

    private Boolean isprepay;

    private Integer useamount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date expireTime;

    private String openid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date addTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    private Boolean deleted;

    public UserFormidDO() {
    }

    public UserFormidDO(Integer id, String formid, Boolean isprepay, Integer useamount, Date expireTime, String openid, Date addTime, Date updateTime, Boolean deleted) {
        this.id = id;
        this.formid = formid;
        this.isprepay = isprepay;
        this.useamount = useamount;
        this.expireTime = expireTime;
        this.openid = openid;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid == null ? null : formid.trim();
    }

    public Boolean getIsprepay() {
        return isprepay;
    }

    public void setIsprepay(Boolean isprepay) {
        this.isprepay = isprepay;
    }

    public Integer getUseamount() {
        return useamount;
    }

    public void setUseamount(Integer useamount) {
        this.useamount = useamount;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}