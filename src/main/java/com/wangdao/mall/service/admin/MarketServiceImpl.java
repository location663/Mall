package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.*;

import com.wangdao.mall.service.util.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.sql.Date;

@Service
@Transactional
public class MarketServiceImpl implements MarketService{

    @Autowired
    RegionDOMapper regionDOMapper;

    @Autowired
    BrandDOMapper brandDOMapper;

    @Autowired
    CategoryDOMapper categoryDOMapper;

    @Autowired
    StorageUtils storageUtils;

    @Autowired
    OrderDOMapper orderDOMapper;

    @Autowired
    OrderGoodsDOMapper orderGoodsDOMapper;

    @Autowired
    UserDOMapper userDOMapper;

    @Autowired
    IssueDOMapper issueDOMapper;

    @Autowired
    KeywordDOMapper keywordDOMapper;



    /**
     * 商场管理，获得全部行政区域
     * @return
     */
    @Override
    public List<RegionVO> selectRegions() {

        List<RegionVO> regionByPidAndType1 = getRegionByPidAndType(0, (byte) 1);
        for (RegionVO regionVO : regionByPidAndType1) {
            List<RegionVO> regionByPidAndType2 = getRegionByPidAndType(regionVO.getId(), (byte) 2);
            for (RegionVO vo : regionByPidAndType2) {
                List<RegionVO> regionByPidAndType3 = getRegionByPidAndType(vo.getId(), (byte) 3);
                vo.setChildren(regionByPidAndType3);
            }
            regionVO.setChildren(regionByPidAndType2);
        }

        return regionByPidAndType1;
    }

    /**
     * 商品品牌显示页及模糊查询
     * @param pageDTO
     * @return
     */
    @Override
    public Map<String, Object> selectBrand(RequestPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getLimit());

        BrandDOExample brandDOExample = new BrandDOExample();
        BrandDOExample.Criteria criteria = brandDOExample.createCriteria().andDeletedEqualTo(false);
        if (null != pageDTO.getId()) {
            criteria.andIdEqualTo(pageDTO.getId());
        }
        if (null != pageDTO.getName() && !pageDTO.getName().trim().equals("")){
            criteria.andNameLike("%" + pageDTO.getName() + "%");
        }
        brandDOExample.setOrderByClause(pageDTO.getSort() + " " + pageDTO.getOrder());
        List<BrandDO> brandDOS = brandDOMapper.selectByExample(brandDOExample);

        PageInfo<BrandDO> brandDOPageInfo = new PageInfo<>(brandDOS);
        long total = brandDOPageInfo.getTotal();

        HashMap<String, Object> map = new HashMap<>();

        map.put("items", brandDOS);
        map.put("total", total);

        return map;
    }

    /**
     * 更新商品品牌信息
     * @param brandDO
     * @return
     */
    @Override
    public BrandDO updateBrandById(BrandDO brandDO) {

        brandDO.setUpdateTime(new Date(System.currentTimeMillis()));
        int i = brandDOMapper.updateByPrimaryKey(brandDO);
        BrandDO brandDO1 = brandDOMapper.selectByPrimaryKey(brandDO.getId());
        return brandDO1;
    }

    /**
     * 加入新的商品品牌
     * @param brandDO
     * @return
     */
    @Override
    public BrandDO insertBrand(BrandDO brandDO) {
        brandDO.setAddTime(new Date(System.currentTimeMillis()));
        brandDO.setUpdateTime(new Date(System.currentTimeMillis()));
        brandDOMapper.insertSelective(brandDO);
        BrandDO brandDO1 = brandDOMapper.selectByPrimaryKey(brandDOMapper.selectLastInsertId());

        return brandDO1;
    }

    /**
     * 上传商品图片
     * @param file
     * @return
     */
    @Override
    public StorageDO insertStorage(MultipartFile file) {
        StorageDO storageDO1 = storageUtils.insertStorage(file);
        return storageDO1;
    }

    /**
     * 删除商场商品
     * @param brandDO
     * @return
     */
    @Override
    public int deleteBrand(BrandDO brandDO) {
        brandDO.setDeleted(true);
        brandDO.setUpdateTime(new Date(System.currentTimeMillis()));
        int i = brandDOMapper.updateByPrimaryKey(brandDO);
        return i;
    }

    /**
     * 行政区域中间方法
     * @param id
     * @param type
     * @return
     */
    private List<RegionVO> getRegionByPidAndType(Integer id, Byte type){
        RegionDOExample regionDOExample = new RegionDOExample();
        regionDOExample.createCriteria().andPidEqualTo(id).andTypeEqualTo(type);
        List<RegionDO> regionDOS = regionDOMapper.selectByExample(regionDOExample);
        ArrayList<RegionVO> regionVOS = new ArrayList<>();
        for (RegionDO regionDO : regionDOS) {
            RegionVO regionVO = new RegionVO();
            regionVO.setId(regionDO.getId());
            regionVO.setName(regionDO.getName());
            regionVO.setType(regionDO.getType());
            regionVO.setCode(regionDO.getCode());
            regionVO.setPid(regionDO.getPid());
            regionVOS.add(regionVO);
        }

        return regionVOS;
    }

    /**
     * 商品类目
     * @return
     */
    @Override
    public List<CategoryDO> listCategory() {
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.createCriteria().andLevelEqualTo("L1").andDeletedEqualTo(false);
        List<CategoryDO> categoryDOList = categoryDOMapper.selectByExample(categoryDOExample);
        return categoryDOList;
    }

    /**
     * 一级商品类目
     * @return
     */
    @Override
    public List<CategoryVO> listCategory1() {
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.createCriteria().andLevelEqualTo("L1").andDeletedEqualTo(false);
        List<CategoryDO> categoryDOList = categoryDOMapper.selectByExample(categoryDOExample);
        ArrayList<CategoryVO> categoryVOS = new ArrayList<>();
        for (CategoryDO categoryDO : categoryDOList) {
            CategoryVO categoryVO = new CategoryVO(categoryDO.getId(), categoryDO.getName());
            categoryVOS.add(categoryVO);
        }
        return categoryVOS;
    }

    /**
     * 插入商品
     * @param categoryDO
     * @return
     */
    @Override
    public CategoryDO insertCategory(CategoryDO categoryDO) {
        categoryDO.setAddTime(new Date(System.currentTimeMillis()));
        categoryDO.setUpdateTime(new Date(System.currentTimeMillis()));
        int i = categoryDOMapper.insertSelective(categoryDO);
        return categoryDO;
    }

    /**
     * 更新商品
     * @param categoryDO
     * @return
     */
    @Override
    public int updateCategory(CategoryDO categoryDO) {
        categoryDO.setUpdateTime(new Date(System.currentTimeMillis()));
        int i1 = categoryDOMapper.updateByPrimaryKey(categoryDO);
        return i1;
    }

    /**
     * 删除商品
     * @param categoryDO
     * @return
     */
    @Override
    public int deleteCategory(CategoryDO categoryDO) {
        categoryDO.setUpdateTime(new Date(System.currentTimeMillis()));
        categoryDO.setDeleted(true);
        int i = categoryDOMapper.updateByPrimaryKey(categoryDO);
        return i;
    }

    /**
     * 订单列表
     * @param pageDTO
     * @return
     */
    @Override
    public Map listOrder(RequestPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getLimit());

        OrderDOExample orderDOExample = new OrderDOExample();
        OrderDOExample.Criteria criteria = orderDOExample.createCriteria().andDeletedEqualTo(false);
        if (null != pageDTO.getUserId() ){
            criteria.andUserIdEqualTo(pageDTO.getUserId());
        }
        if (null != pageDTO.getOrderSn() && !pageDTO.getOrderSn().trim().equals("")){
            criteria.andOrderSnEqualTo(pageDTO.getOrderSn().trim());
        }
        if (null != pageDTO.getOrderStatusArray() ){
//            criteria.andOrderSnEqualTo(pageDTO.getOrderStatusArray());
            criteria.andOrderStatusIn(pageDTO.getOrderStatusArray());
        }
        orderDOExample.setOrderByClause(pageDTO.getSort() + " " + pageDTO.getOrder());
        List<OrderDO> orderDOList = orderDOMapper.selectByExample(orderDOExample);
        PageInfo<OrderDO> orderDOPageInfo = new PageInfo<>(orderDOList);
        long total = orderDOPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", orderDOList);
        map.put("total", total);
        return map;
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @Override
    public Map<String, Object> detailOrder(Integer orderId) {
        HashMap<String, Object> map = new HashMap<>();
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);
        OrderGoodsDOExample orderGoodsDOExample = new OrderGoodsDOExample();
        orderGoodsDOExample.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderGoodsDO> orderGoodsDOS = orderGoodsDOMapper.selectByExample(orderGoodsDOExample);
        UserDO userDO = userDOMapper.selectByPrimaryKey(orderDO.getUserId());
        map.put("order", orderDO);
        map.put("orderGoods", orderGoodsDOS.get(0));
        map.put("user", userDO);
        return map;
    }

    /**
     * 通用问题列表
     * @param pageDTO
     * @return
     */
    @Override
    public Map listIssue(RequestPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getLimit());

        IssueDOExample issueDOExample = new IssueDOExample();
        IssueDOExample.Criteria criteria = issueDOExample.createCriteria().andDeletedEqualTo(false);
        if (null != pageDTO.getQuestion() && !pageDTO.getQuestion().trim().equals("")){
            criteria.andQuestionLike("%" + pageDTO.getQuestion().trim() + "%");
        }
        List<IssueDO> issueDOS = issueDOMapper.selectByExample(issueDOExample);

        PageInfo<IssueDO> issueDOPageInfo = new PageInfo<>();
        long total = issueDOPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", issueDOS);
        map.put("total", total);
        return map;
    }

    /**
     * 插入通用问题
     * @param issueDO
     * @return
     */
    @Override
    public IssueDO insertIssue(IssueDO issueDO) {
        issueDO.setAddTime(new Date(System.currentTimeMillis()));
        issueDO.setUpdateTime(new Date(System.currentTimeMillis()));
        int i = issueDOMapper.insertSelective(issueDO);
        int id = issueDOMapper.selectLastInsertId();
        IssueDO issueDO1 = issueDOMapper.selectByPrimaryKey(id);
        return issueDO1;
    }

    /**
     * 更新通用问题
     * @param issueDO
     * @return
     */
    @Override
    public IssueDO updateIssue(IssueDO issueDO) {
        issueDO.setUpdateTime(new Date(System.currentTimeMillis()));
        issueDOMapper.updateByPrimaryKey(issueDO);
        IssueDO issueDO1 = issueDOMapper.selectByPrimaryKey(issueDO.getId());
        return issueDO1;
    }

    /**
     * 删除通用问题
     * @param issueDO
     * @return
     */
    @Override
    public int deleteIssue(IssueDO issueDO) {
        issueDO.setDeleted(true);
        issueDO.setUpdateTime(new Date(System.currentTimeMillis()));
        int res = issueDOMapper.updateByPrimaryKey(issueDO);
        return res;
    }

    /**
     * 关键词列表
     * @param pageDTO
     * @return
     */
    @Override
    public Map listKeyword(RequestPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getLimit());

        KeywordDOExample keywordDOExample = new KeywordDOExample();
        KeywordDOExample.Criteria criteria = keywordDOExample.createCriteria().andDeletedEqualTo(false);
        if (null != pageDTO.getKeyword() && !pageDTO.getKeyword().trim().equals("")){
            criteria.andKeywordLike("%" + pageDTO.getKeyword().trim() + "%");
        }
        if (null != pageDTO.getUrl() && !pageDTO.getUrl().trim().equals("")){
            criteria.andUrlLike("%" + pageDTO.getUrl().trim() + "%");
        }
        List<KeywordDO> keyDOS = keywordDOMapper.selectByExample(keywordDOExample);
        PageInfo<IssueDO> issueDOPageInfo = new PageInfo<>();
        long total = issueDOPageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", keyDOS);
        map.put("total", total);
        return map;
    }

    /**
     * 插入关键词
     * @param keywordDO
     * @return
     */
    @Override
    public KeywordDO insertKeyword(KeywordDO keywordDO) {
        keywordDO.setAddTime(new Date(System.currentTimeMillis()));
        keywordDO.setUpdateTime(new Date(System.currentTimeMillis()));
        int i = keywordDOMapper.insertSelective(keywordDO);
        int id = keywordDOMapper.selectLastInsertId();
        KeywordDO keywordDO1 = keywordDOMapper.selectByPrimaryKey(id);
        return keywordDO1;
    }

    /**
     * 删除关键词
     * @param keywordDO
     * @return
     */
    @Override
    public int deleteKeyword(KeywordDO keywordDO) {
        keywordDO.setDeleted(true);
        keywordDO.setUpdateTime(new Date(System.currentTimeMillis()));
        int i = keywordDOMapper.updateByPrimaryKey(keywordDO);
        return i;
    }

    /**
     * 修改关键词
     * @param keywordDO
     * @return
     */
    @Override
    public KeywordDO updateKeyword(KeywordDO keywordDO) {
        keywordDO.setUpdateTime(new Date(System.currentTimeMillis()));
        keywordDOMapper.updateByPrimaryKey(keywordDO);
        KeywordDO keywordDO1 = keywordDOMapper.selectByPrimaryKey(keywordDO.getId());
        return keywordDO1;
    }
}
