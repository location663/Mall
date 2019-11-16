package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.BrandDO;
import com.wangdao.mall.bean.BrandDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface BrandDOMapper {
    long countByExample(BrandDOExample example);

    int deleteByExample(BrandDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BrandDO record);

    int insertSelective(BrandDO record);

    List<BrandDO> selectByExample(BrandDOExample example);

    BrandDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BrandDO record, @Param("example") BrandDOExample example);

    int updateByExample(@Param("record") BrandDO record, @Param("example") BrandDOExample example);

    int updateByPrimaryKeySelective(BrandDO record);

    int updateByPrimaryKey(BrandDO record);

    @Select("select DISTINCT LAST_INSERT_ID() from cskaoyan_mall_brand")
    int selectLastInsertId();
}