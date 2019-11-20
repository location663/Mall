/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/20
 * Time  下午 3:14
 */

package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.CollectDO;
import com.wangdao.mall.bean.CollectDOExample;
import com.wangdao.mall.bean.GoodsDO;
import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.mapper.CollectDOMapper;
import com.wangdao.mall.mapper.GoodsDOMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WxCollectServiceImpl implements WxCollectService {

    @Autowired
    CollectDOMapper collectDOMapper;

    @Autowired
    GoodsDOMapper goodsDOMapper;



    /**
     * 获取用户商品收藏列表   (可能有专题收藏)  totalPages需要改进
     * @param type
     * @param page
     * @param size
     * @return
     */
    @Override
    public HashMap<String, Object> queryCollectList(Integer type, Integer page, Integer size) {
        //获取当前用户登录对象
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(page,size);

        //封装 collectList
        if (type==0 && userDO!=null){  //0说明是商品类型
            ArrayList<Map> collectList = new ArrayList<>();

            CollectDOExample collectDOExample = new CollectDOExample();
            collectDOExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId()).andTypeEqualTo(Byte.valueOf(String.valueOf(type)));
            List<CollectDO> collectList1 = collectDOMapper.selectByExample(collectDOExample);
            if (collectList1.size()>0){
                for (CollectDO collectDO : collectList1) {
                    HashMap<String, Object> map1 = new HashMap<>();
                    GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(collectDO.getValueId());
                    map1.put("brief",goodsDO.getBrief());
                    map1.put("picUrl",goodsDO.getPicUrl());
                    map1.put("valueId",goodsDO.getId());
                    map1.put("name",goodsDO.getName());
                    map1.put("id",collectDO.getId());
                    map1.put("type",collectDO.getType());
                    map1.put("retailPrice",goodsDO.getRetailPrice());
                    collectList.add(map1);
                }
                map.put("collectList",collectList);

                PageInfo<CollectDO> userPageInfo = new PageInfo<>(collectList1);
                Integer totalPages = userPageInfo.getPageNum();
                map.put("totalPages",totalPages);  //分页后页的总数
            }
        }
        return map;
    }



    /*
    添加或取消收藏 (只做了商品，可能有添加专题收藏)
     */
    @Override
    public HashMap<String, Object> collectAddordelete(CollectDO collectDO) {
        //获取当前用户登录对象
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();

        HashMap<String, Object> map = new HashMap<>();
        //CollectDOExample collectDOExample = new CollectDOExample();
        if (collectDO.getType()==0 && userDO!=null) {  //0说明是商品类型
            //先查询数据表里是否存有绑定了此用户的这个商品id(绑定次userId)，如果有，直接修改delete属性和update属性,没有就插入新建收藏对象
            CollectDOExample collectDOExample1 = new CollectDOExample();
            collectDOExample1.createCriteria().andUserIdEqualTo(userDO.getId()).andValueIdEqualTo(collectDO.getValueId());
            List<CollectDO> collectOldList = collectDOMapper.selectByExample(collectDOExample1);

            if (collectOldList.size()>0){ //如果有，直接修改delete属性和update属性,达成添加或者删除收藏的功能
                for (CollectDO collectDO1 : collectOldList) {
                    if (collectDO1.getDeleted()){  //delete:true 说明该收藏是删除状态，需要重新收藏
                        collectDO1.setDeleted(false);
                        collectDO1.setUpdateTime(new Date());  //然后更新收藏时间
                        int addCollectNum = collectDOMapper.updateByPrimaryKey(collectDO1);
                        map.put("type","add");
                    }else {
                        //delete:false 说明该收藏是已经收藏状态，需要删除这个收藏
                        collectDO1.setDeleted(true);
                        collectDO1.setUpdateTime(new Date());  //然后更新收藏时间
                        int deleteCollectNum = collectDOMapper.updateByPrimaryKey(collectDO1);
                        map.put("type","delete");
                    }
                }
            }else {
                //没有就表示是需要新建这个收藏，insert新建收藏对象
                CollectDO collectDO2 = new CollectDO();
                collectDO2.setUserId(userDO.getId());
                collectDO2.setValueId(collectDO.getValueId());
                collectDO2.setType(Byte.valueOf(String.valueOf(collectDO.getType())));
                collectDO2.setAddTime(new Date());
                collectDO2.setUpdateTime(new Date());
                collectDO2.setDeleted(false);
                int addCollectNum1 = collectDOMapper.insertSelective(collectDO2);
                map.put("type","add");
            }
        }

        return map;
    }
}
