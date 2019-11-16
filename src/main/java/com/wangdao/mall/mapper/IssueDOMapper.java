package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.IssueDO;
import com.wangdao.mall.bean.IssueDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface IssueDOMapper {
    long countByExample(IssueDOExample example);

    int deleteByExample(IssueDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IssueDO record);

    int insertSelective(IssueDO record);

    List<IssueDO> selectByExample(IssueDOExample example);

    IssueDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IssueDO record, @Param("example") IssueDOExample example);

    int updateByExample(@Param("record") IssueDO record, @Param("example") IssueDOExample example);

    int updateByPrimaryKeySelective(IssueDO record);

    int updateByPrimaryKey(IssueDO record);

    @Select("select DISTINCT LAST_INSERT_ID() FROM cskaoyan_mall_issue")
    int selectLastInsertId();
}