package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.TopicDO;
import com.wangdao.mall.bean.TopicDOExample;
import com.wangdao.mall.mapper.TopicDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-18 17:19
 **/
@Service
public class TopicDOServiceImpl implements TopicDOService {
    @Autowired
    TopicDOMapper topicDOMapper;

    /**根据标题、子标题模糊查询专题
     * @param page
     * @param limit
     * @param title
     * @param subtitle
     * @return
     */
    @Override
    public Map<String, Object> queryTopocList(Integer page, Integer limit, String title, String subtitle) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(page, limit);

        TopicDOExample topicDOExample = new TopicDOExample();
        topicDOExample.setOrderByClause("add_time desc");
        TopicDOExample.Criteria criteria = topicDOExample.createCriteria();
        if(title != null){
            criteria.andTitleLike("%" + title + "%");
        }
        if(subtitle != null){
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);
        List<TopicDO> topicDOList = topicDOMapper.selectByExample(topicDOExample);
        stringObjectHashMap.put("items", topicDOList);
        PageInfo<TopicDO> topicDOPageInfo = new PageInfo<>(topicDOList);
        long total = topicDOPageInfo.getTotal();
        stringObjectHashMap.put("total", total);
        return stringObjectHashMap;
    }

    @Override
    public TopicDO updateTopic(TopicDO topicDO) {
        topicDO.setUpdateTime(new Date());
        int update = topicDOMapper.updateByPrimaryKey(topicDO);
        TopicDO topicDO1 = null;
        if(update == 1){
            topicDO1 = topicDOMapper.selectByPrimaryKey(topicDO.getId());
        }
        return topicDO1;
    }

    @Override
    public int deleteTopic(TopicDO topicDO) {
        int delete = topicDOMapper.deleteTopicById(topicDO.getId());
        return delete;
    }

    @Override
    public TopicDO creatTopic(TopicDO topicDO) {
        Date date = new Date();
        topicDO.setAddTime(date);
        topicDO.setUpdateTime(date);
        int i = topicDOMapper.insertSelective(topicDO);
        int id = 0;
        if(i == 1){
            id = topicDOMapper.selectLastInsertId();
        }
        TopicDO topicDO1 = topicDOMapper.selectByPrimaryKey(id);
        return topicDO1;
    }
}
