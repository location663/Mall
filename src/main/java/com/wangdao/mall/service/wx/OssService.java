package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.StorageDO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-20 19:36
 **/
public interface OssService {
    StorageDO uploadStorage(MultipartFile file);
}
