package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.GoodsAttributeDO;
import com.wangdao.mall.bean.GoodsAttributeDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsAttributeDOMapper {
    long countByExample(GoodsAttributeDOExample example);

    int deleteByExample(GoodsAttributeDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsAttributeDO record);

    int insertSelective(GoodsAttributeDO record);

    List<GoodsAttributeDO> selectByExample(GoodsAttributeDOExample example);

    GoodsAttributeDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsAttributeDO record, @Param("example") GoodsAttributeDOExample example);

    int updateByExample(@Param("record") GoodsAttributeDO record, @Param("example") GoodsAttributeDOExample example);

    int updateByPrimaryKeySelective(GoodsAttributeDO record);

    int updateByPrimaryKey(GoodsAttributeDO record);
}