package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.FeedbackDO;
import com.wangdao.mall.bean.FeedbackDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FeedbackDOMapper {
    long countByExample(FeedbackDOExample example);

    int deleteByExample(FeedbackDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FeedbackDO record);

    int insertSelective(FeedbackDO record);

    List<FeedbackDO> selectByExample(FeedbackDOExample example);

    FeedbackDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FeedbackDO record, @Param("example") FeedbackDOExample example);

    int updateByExample(@Param("record") FeedbackDO record, @Param("example") FeedbackDOExample example);

    int updateByPrimaryKeySelective(FeedbackDO record);

    int updateByPrimaryKey(FeedbackDO record);

}
