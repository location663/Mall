package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.FootprintDO;
import com.wangdao.mall.bean.FootprintDOExample;
import java.util.List;

import com.wangdao.mall.bean.FootprintVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface FootprintDOMapper {
    long countByExample(FootprintDOExample example);

    int deleteByExample(FootprintDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FootprintDO record);

    int insertSelective(FootprintDO record);

    List<FootprintDO> selectByExample(FootprintDOExample example);

    FootprintDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FootprintDO record, @Param("example") FootprintDOExample example);

    int updateByExample(@Param("record") FootprintDO record, @Param("example") FootprintDOExample example);

    int updateByPrimaryKeySelective(FootprintDO record);

    int updateByPrimaryKey(FootprintDO record);

    @Update("update cskaoyan_mall_footprint set deleted = 1 where id = #{id}")
    int deleteFootprintById(Integer id);

    List<FootprintVO> selectFootprintVOs();

}
