package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.GrouponRulesDO;
import com.wangdao.mall.bean.GrouponRulesDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GrouponRulesDOMapper {
    long countByExample(GrouponRulesDOExample example);

    int deleteByExample(GrouponRulesDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GrouponRulesDO record);

    int insertSelective(GrouponRulesDO record);

    List<GrouponRulesDO> selectByExample(GrouponRulesDOExample example);

    GrouponRulesDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GrouponRulesDO record, @Param("example") GrouponRulesDOExample example);

    int updateByExample(@Param("record") GrouponRulesDO record, @Param("example") GrouponRulesDOExample example);

    int updateByPrimaryKeySelective(GrouponRulesDO record);

    int updateByPrimaryKey(GrouponRulesDO record);
}