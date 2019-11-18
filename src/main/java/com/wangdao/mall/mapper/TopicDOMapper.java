package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.TopicDO;
import com.wangdao.mall.bean.TopicDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TopicDOMapper {
    long countByExample(TopicDOExample example);

    int deleteByExample(TopicDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TopicDO record);

    int insertSelective(TopicDO record);

    List<TopicDO> selectByExampleWithBLOBs(TopicDOExample example);

    List<TopicDO> selectByExample(TopicDOExample example);

    TopicDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TopicDO record, @Param("example") TopicDOExample example);

    int updateByExampleWithBLOBs(@Param("record") TopicDO record, @Param("example") TopicDOExample example);

    int updateByExample(@Param("record") TopicDO record, @Param("example") TopicDOExample example);

    int updateByPrimaryKeySelective(TopicDO record);

    int updateByPrimaryKeyWithBLOBs(TopicDO record);

    int updateByPrimaryKey(TopicDO record);

    @Select("select last_insert_id()")
    int selectLastInsertId();

}
