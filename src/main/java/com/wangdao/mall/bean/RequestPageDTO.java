/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 20:52
 **/
package com.wangdao.mall.bean;

import lombok.Data;

@Data
public class RequestPageDTO {
    private Integer page;
    private Integer limit;
    private String sort;
    private String order;
    private Integer id;
    private String name;

    public RequestPageDTO() {
    }

    public RequestPageDTO(Integer page, Integer limit, String sort, String order, Integer id, String name) {
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        this.order = order;
        this.id = id;
        this.name = name;
    }
}
