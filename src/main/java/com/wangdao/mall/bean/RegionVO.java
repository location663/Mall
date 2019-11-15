package com.wangdao.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class RegionVO {
    private Integer id;

    private Integer pid;

    private String name;

    private Byte type;

    private Integer code;

    private List<RegionVO> children;
}
