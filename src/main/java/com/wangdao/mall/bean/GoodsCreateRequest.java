/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/16
 * Time  下午 9:07
 */

package com.wangdao.mall.bean;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 这个类用来创建商品goodsCreate或者编辑更新商品goodsUpdate时，封装request的四个请求json
 */
@Data
public class GoodsCreateRequest {

    GoodsDO goods;
    List<GoodsSpecificationDO> specifications;
    List<GoodsProductDO> products;
    List<GoodsAttributeDO> attributes;
}
