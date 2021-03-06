package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.OrderGoodsDO;
import com.wangdao.mall.bean.OrderGoodsDOExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OrderGoodsDOMapper {
    long countByExample(OrderGoodsDOExample example);

    int deleteByExample(OrderGoodsDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderGoodsDO record);

    int insertSelective(OrderGoodsDO record);

    List<OrderGoodsDO> selectByExample(OrderGoodsDOExample example);

    OrderGoodsDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderGoodsDO record, @Param("example") OrderGoodsDOExample example);

    int updateByExample(@Param("record") OrderGoodsDO record, @Param("example") OrderGoodsDOExample example);

    int updateByPrimaryKeySelective(OrderGoodsDO record);

    int updateByPrimaryKey(OrderGoodsDO record);

    @Select("select order_id as orderId, product_id as productId, `number` from cskaoyan_mall_order_goods where order_id = #{orderId}")
    List<OrderGoodsDO> selectByOrderId(@Param("orderId") Integer orderId);
}
