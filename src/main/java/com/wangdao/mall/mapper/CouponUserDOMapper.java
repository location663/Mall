package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.CouponUserDO;
import com.wangdao.mall.bean.CouponUserDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponUserDOMapper {
    long countByExample(CouponUserDOExample example);

    int deleteByExample(CouponUserDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponUserDO record);

    int insertSelective(CouponUserDO record);

    List<CouponUserDO> selectByExample(CouponUserDOExample example);

    CouponUserDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponUserDO record, @Param("example") CouponUserDOExample example);

    int updateByExample(@Param("record") CouponUserDO record, @Param("example") CouponUserDOExample example);

    int updateByPrimaryKeySelective(CouponUserDO record);

    int updateByPrimaryKey(CouponUserDO record);
}