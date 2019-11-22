package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.AdDO;
import com.wangdao.mall.bean.AdDOExample;
import com.wangdao.mall.mapper.AdDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-15 17:51
 **/
@Service
@Transactional
public class AdServiceImpl implements AdService {
    @Autowired
    AdDOMapper adDOMapper;

    @Override
    public Map<String, Object> queryAdDOs(Integer page, Integer limit, String name, String content) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(page, limit);
        AdDOExample adDOExample = new AdDOExample();
        //怎么添加sort add_time和order desc这两个条件
        adDOExample.setOrderByClause("add_time desc");
        AdDOExample.Criteria criteria = adDOExample.createCriteria();
        if(name != null) {
            String new_name = "%" + name + "%";
            criteria.andNameLike(new_name);
        }
        if(content != null) {
            String new_content = "%" + content + "%";
            criteria.andContentLike(new_content);
        }
        criteria.andDeletedEqualTo(false);
        //获取所有ad的List

        List<AdDO> adDOList = adDOMapper.selectByExample(adDOExample);
        map.put("items", adDOList);
        //获取总条目数
        PageInfo pageInfo = new PageInfo();
        long total = pageInfo.getTotal();
        map.put("total", total);
        return map;
    }

    @Override
    public int updateAdDO(AdDO adDO) {
        int i = adDOMapper.updateByPrimaryKey(adDO);
        return i;
    }

    @Override
    public int createAdDO(AdDO adDO) {
        Date date = new Date();
        adDO.setAddTime(date);
        adDO.setUpdateTime(date);
        int id = 0;
        //修改mapper里该函数，增加selectKey
        int insert = adDOMapper.insertSelective(adDO);
        if(insert == 1){
            id = adDOMapper.selectLastInsertId();
        }
        return id;
    }


    /**逻辑删除
     * @param id
     * @return
     */
    @Override
    public int deleteAdDOById(Integer id) {
        int delete = adDOMapper.deleteCouponById(id);
        return delete;
    }


    @Override
    public AdDO queryAdDO(Integer id) {
        AdDO adDO = adDOMapper.selectByPrimaryKey(id);
        return adDO;
    }

}
