package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.GoodsDO;
import com.wangdao.mall.bean.GoodsDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsDOMapper {
    long countByExample(GoodsDOExample example);

    int deleteByExample(GoodsDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsDO record);

    int insertSelective(GoodsDO record);

    List<GoodsDO> selectByExampleWithBLOBs(GoodsDOExample example);

    List<GoodsDO> selectByExample(GoodsDOExample example);

    GoodsDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsDO record, @Param("example") GoodsDOExample example);

    int updateByExampleWithBLOBs(@Param("record") GoodsDO record, @Param("example") GoodsDOExample example);

    int updateByExample(@Param("record") GoodsDO record, @Param("example") GoodsDOExample example);

    int updateByPrimaryKeySelective(GoodsDO record);

    int updateByPrimaryKeyWithBLOBs(GoodsDO record);

    int updateByPrimaryKey(GoodsDO record);
}