package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.CommentDO;
import com.wangdao.mall.bean.CommentDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentDOMapper {
    long countByExample(CommentDOExample example);

    int deleteByExample(CommentDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommentDO record);

    int insertSelective(CommentDO record);

    List<CommentDO> selectByExample(CommentDOExample example);

    CommentDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommentDO record, @Param("example") CommentDOExample example);

    int updateByExample(@Param("record") CommentDO record, @Param("example") CommentDOExample example);

    int updateByPrimaryKeySelective(CommentDO record);

    int updateByPrimaryKey(CommentDO record);
}