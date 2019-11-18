package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.CouponDO;
import com.wangdao.mall.bean.CouponDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select("select last_insert_id()")
    int selectLastInsertId();

    @Update("update cskaoyan_mall_coupon set days = date(end_time) - date(start_time) where id = #{id}")
    void updateDays(int id);

    @Update("update cskaoyan_mall_coupon set code = #{param2} where id = #{param1}")
    void updateCode(int id, String s);

    @Update("update cskaoyan_mall_coupon set deleted = 1 where id = #{id}")
    int deleteCouponById(Integer id);
}
