/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/19
 * Time  下午 11:25
 */

package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.KeywordDOMapper;
import com.wangdao.mall.mapper.SearchHistoryDOMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class WxSearchServiceImpl implements WxSearchService{

    @Autowired
    KeywordDOMapper keywordDOMapper;

    @Autowired
    SearchHistoryDOMapper searchHistoryDOMapper;

    /**
     * 搜索关键字  必须是"isHot"=true和"deleted"=false   "historyKeywordList": []如果用户未登录，直接返回空List
     * @return
     */
    @Override
    public HashMap<String, Object> searchIndex() {
        HashMap<String, Object> map = new HashMap<>();

        //获取当前用户登录对象
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();

        //封装 historyKeywordList: []
        ArrayList<Object> historyKeywordList = new ArrayList<>();
        if (userDO!=null){  //用户已经登录
            SearchHistoryDOExample searchHistoryDOExample = new SearchHistoryDOExample();
            searchHistoryDOExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId());
            List<SearchHistoryDO> searchHistoryDOS = searchHistoryDOMapper.selectByExample(searchHistoryDOExample);
            for (SearchHistoryDO searchHistoryDO : searchHistoryDOS) {
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("keyword",searchHistoryDO.getKeyword());
                historyKeywordList.add(map1);
            }
        }
        map.put("historyKeywordList",historyKeywordList);//用户未登录，直接返回空List

        //封装 defaultKeyword
        KeywordDOExample keywordDOExample = new KeywordDOExample();
        //必须是"isHot"=true和"deleted"=false
        keywordDOExample.createCriteria().andDeletedEqualTo(false).andIsHotEqualTo(true);
        List<KeywordDO> hotKeywordList = keywordDOMapper.selectByExample(keywordDOExample);
        map.put("hotKeywordList",hotKeywordList);

        //封装热搜 hotKeywordList 暂时默认为Keyword里第一个
        map.put("defaultKeyword",hotKeywordList.get(0));
        return map;
    }



    /**
     * 搜索帮助
     * @param keyword
     * @return
     */
    @Override
    public ArrayList<String> searchHelper(String keyword) {
        ArrayList<String> keywords = new ArrayList<>();
        KeywordDOExample keywordDOExample = new KeywordDOExample();
        keywordDOExample.createCriteria().andDeletedEqualTo(false).andKeywordLike("%"+keyword+"%");
        List<KeywordDO> keywordDOList = keywordDOMapper.selectByExample(keywordDOExample);
        for (KeywordDO keywordDO : keywordDOList) {
            keywords.add(keywordDO.getKeyword());
        }
        return keywords;
    }
}
