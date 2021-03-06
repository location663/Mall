package com.wangdao.mall.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SystemPermissionDO {

    private Integer sId;

    private String level;

    private Integer pId;

    private String id;

    private String label;

    private String api;

    private List<SystemPermissionDO> children = new ArrayList<>();

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api == null ? null : api.trim();
    }

    public List<SystemPermissionDO> getChildren() {
        return children;
    }

    public void setChildren(List<SystemPermissionDO> children) {
        this.children = children;
    }
}