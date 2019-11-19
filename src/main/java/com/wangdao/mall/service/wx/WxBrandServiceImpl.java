package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.BrandDO;
import com.wangdao.mall.bean.BrandDOExample;
import com.wangdao.mall.bean.RequestPageDTO;
import com.wangdao.mall.mapper.BrandDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxBrandServiceImpl implements WxBrandService {

    @Autowired
    BrandDOMapper brandDOMapper;

    /**
     * 品牌列表
     * @param pageDTO
     * @return
     */
    @Override
    public Map<String, Object> listBrand(RequestPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getLimit());

        BrandDOExample brandDOExample = new BrandDOExample();
        BrandDOExample.Criteria criteria = brandDOExample.createCriteria().andDeletedEqualTo(false);
        List<BrandDO> brandDOS = brandDOMapper.selectByExample(brandDOExample);
        PageInfo<BrandDO> brandDOPageInfo = new PageInfo<>(brandDOS);
        int total = (int) (brandDOPageInfo.getTotal() / pageDTO.getLimit() + 1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("brandList", brandDOS);
        map.put("totalPages", total);
        return map;
    }

    /**
     * 品牌详情
     * @param id
     * @return
     */
    @Override
    public BrandDO selectById(Integer id) {
        BrandDO brandDO = brandDOMapper.selectByPrimaryKey(id);
        return brandDO;
    }
}
