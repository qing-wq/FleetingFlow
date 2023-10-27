package ink.whi.video.repo.qiniu.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.video.repo.qiniu.entity.QiniuConfig;
import ink.whi.video.repo.qiniu.mapper.QiNiuConfigMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author: qing
 * @Date: 2023/10/25
 */
@Repository
public class QiniuConfigDao extends ServiceImpl<QiNiuConfigMapper, QiniuConfig> {

    /**
     * 编辑类型
     *
     * @param type
     */
    void updateType(String type) {

    }

    public QiniuConfig getConfig() {
        return lambdaQuery().one();
    }
}
