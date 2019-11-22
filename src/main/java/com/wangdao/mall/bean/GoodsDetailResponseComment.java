/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/22
 * Time  下午 1:32
 */

package com.wangdao.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GoodsDetailResponseComment {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date addTime;

    private String[] picList;

    private String nickname;

    private Integer id;

    private String avatar;

    private String content;


    public GoodsDetailResponseComment() {
    }

    public GoodsDetailResponseComment(Date addTime, String[] picList, String nickname, Integer id, String avatar, String content) {
        this.addTime = addTime;
        this.picList = picList;
        this.nickname = nickname;
        this.id = id;
        this.avatar = avatar;
        this.content = content;
    }
}
