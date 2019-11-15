package com.wangdao.mall.bean;

import lombok.Data;

import java.util.Date;
@Data
public class KeywordDO {
    private Integer id;

    private String keyword;

    private String url;

    private Boolean isHot;

    private Boolean isDefault;

    private Integer sortOrder;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    public KeywordDO() {
    }

    public KeywordDO(Integer id, String keyword, String url, Boolean isHot, Boolean isDefault, Integer sortOrder, Date addTime, Date updateTime, Boolean deleted) {
        this.id = id;
        this.keyword = keyword;
        this.url = url;
        this.isHot = isHot;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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