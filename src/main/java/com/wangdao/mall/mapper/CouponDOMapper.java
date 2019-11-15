package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.CouponDO;
import com.wangdao.mall.bean.CouponDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponDOMapper {
    long countByExample(CouponDOExample example);

    int deleteByExample(CouponDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponDO record);

    int insertSelective(CouponDO record);

    List<CouponDO> selectByExample(CouponDOExample example);

    CouponDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponDO record, @Param("example") CouponDOExample example);

    int updateByExample(@Param("record") CouponDO record, @Param("example") CouponDOExample example);

    int updateByPrimaryKeySelective(CouponDO record);

    int updateByPrimaryKey(CouponDO record);
}