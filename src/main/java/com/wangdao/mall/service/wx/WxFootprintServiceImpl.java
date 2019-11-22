package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.FootprintDO;
import com.wangdao.mall.bean.FootprintDOExample;
import com.wangdao.mall.bean.FootprintVO;
import com.wangdao.mall.exception.WxException;
import com.wangdao.mall.mapper.FootprintDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-19 22:45
 **/
@Service
@Transactional
public class WxFootprintServiceImpl implements WxFootprintService{
    @Autowired
    FootprintDOMapper footprintDOMapper;

    /**逻辑删除足迹
     * @param id
     * @return
     */
    @Override
    public int deleteFootprint(Integer id) {
        int delete = footprintDOMapper.deleteFootprintById(id);
        return delete;
    }


    /**足迹列表,需要查询goods表和footprint表
     * @param page
     * @param size
     * @return
     */
    @Override
    public Map<String, Object> listFootprint(Integer page, Integer size) throws WxException {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(page, size);
        List<FootprintVO> footprintVOList = footprintDOMapper.selectFootprintVOs();
        if(footprintVOList == null){
            throw new WxException("你还没浏览过商品呢");
        }
        PageInfo<FootprintVO> footprintDOPageInfo = new PageInfo<>(footprintVOList);
        long total = footprintDOPageInfo.getTotal();
        int totalPages = (int) Math.ceil(total * 1.0 / size);
        Map<String, Object> map = new HashMap<>();
        map.put("footprintList", footprintVOList);
        map.put("totalPages", totalPages);
        return map;
    }
}
