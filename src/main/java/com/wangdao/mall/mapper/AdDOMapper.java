package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.AdDO;
import com.wangdao.mall.bean.AdDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdDOMapper {
    long countByExample(AdDOExample example);

    int deleteByExample(AdDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdDO record);

    int insertSelective(AdDO record);

    List<AdDO> selectByExample(AdDOExample example);

    AdDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdDO record, @Param("example") AdDOExample example);

    int updateByExample(@Param("record") AdDO record, @Param("example") AdDOExample example);

    int updateByPrimaryKeySelective(AdDO record);

    int updateByPrimaryKey(AdDO record);

    //新增一个方法
    @Select("select last_insert_id() from cskaoyan_mall_ad")
    int selectLastInsertId();

    @Update("update cskaoyan_mall_ad set deleted = 1 where id = #{id}")
    int deleteCouponById(Integer id);
}
