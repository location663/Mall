package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.OrderDO;
import com.wangdao.mall.bean.OrderDOExample;
import java.util.List;
import com.wangdao.mall.bean.StateDo;
import com.wangdao.mall.bean.OrderStatisticsDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OrderDOMapper {
    long countByExample(OrderDOExample example);

    List<StateDo> selectGoods();

    int deleteByExample(OrderDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    List<OrderDO> selectByExample(OrderDOExample example);

    OrderDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderDO record, @Param("example") OrderDOExample example);

    int updateByExample(@Param("record") OrderDO record, @Param("example") OrderDOExample example);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);

    List<OrderStatisticsDTO> selectForStatistics();

//    @Select("select DISTINCT LAST_INSERT_ID() from cskaoyan_mall_order")
//    int selectLastInsertId();
}
