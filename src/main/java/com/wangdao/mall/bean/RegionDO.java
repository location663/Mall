package com.wangdao.mall.bean;

import lombok.Data;

@Data
public class RegionDO {
    private Integer id;

    private Integer pid;

    private String name;

    private Byte type;

    private Integer code;

    public RegionDO() {
    }

    public RegionDO(Integer id, Integer pid, String name, Byte type, Integer code) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.type = type;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}