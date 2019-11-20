/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/19
 * Time  下午 11:25
 */

package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.KeywordDO;
import com.wangdao.mall.bean.KeywordDOExample;
import com.wangdao.mall.mapper.KeywordDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class WxSearchServiceImpl implements WxSearchService{

    @Autowired
    KeywordDOMapper keywordDOMapper;

    /**
     * 搜索关键字 "historyKeywordList": []需要待改进
     * @return
     */
    @Override
    public HashMap<String, Object> searchIndex() {
        HashMap<String, Object> map = new HashMap<>();

        //封装 historyKeywordList: [] 需要待改进 暂时返回空
        ArrayList<Object> historyKeywordList = new ArrayList<>();
        map.put("historyKeywordList",historyKeywordList);

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
    public HashMap<String, Object> searchHelper(String keyword) {
        HashMap<String, Object> map = new HashMap<>();
            
        return map;
    }
}
