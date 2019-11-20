package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.GrouponDO;
import com.wangdao.mall.bean.GrouponDOExample;

import java.util.List;

import com.wangdao.mall.bean.SubGrouponDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface GrouponDOMapper {
    long countByExample(GrouponDOExample example);

    int deleteByExample(GrouponDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GrouponDO record);

    int insertSelective(GrouponDO record);

    List<GrouponDO> selectByExample(GrouponDOExample example);

    GrouponDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GrouponDO record, @Param("example") GrouponDOExample example);

    int updateByExample(@Param("record") GrouponDO record, @Param("example") GrouponDOExample example);

    int updateByPrimaryKeySelective(GrouponDO record);

    int updateByPrimaryKey(GrouponDO record);

//    List<GrouponDO> listByGoodsId(@Param("goodsId") Integer goodsId, @Param("sort") String sort, @Param("order") String order);

//    @Select("select user_id as 'userId', order_id as 'orderId' from cskaoyan_mall_groupon where id = #{id} ")
//    List<SubGrouponDTO> listSubGroupon(@Param("id") Integer id);

    List<GrouponDO> selectByGoodsId(@Param("goodsId") Integer goodsId);

    @Select("select user_id as 'userId', order_id as 'orderId' from cskaoyan_mall_groupon where groupon_id = #{id} ")
    List<SubGrouponDTO> listSubGroupon(@Param("id") Integer id);

    @Select("select count(0) from cskaoyan_mall_groupon where groupon_id = #{groupon_id} group by groupon_id")
    int countJoinersByGrouponID(@Param("groupon_id") Integer grouponId);
}

