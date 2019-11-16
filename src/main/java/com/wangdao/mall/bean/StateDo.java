/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/16
 * Time:14:12
 **/
package com.wangdao.mall.bean;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class StateDo {
    @JsonFormat(pattern = "yyyy-MMM-dd")
    Date day;
    Integer users;
    Double amount;
    Integer orders;
    Integer customers;
    Double pcr;
    Integer products;
    public StateDo() {
    }
}
