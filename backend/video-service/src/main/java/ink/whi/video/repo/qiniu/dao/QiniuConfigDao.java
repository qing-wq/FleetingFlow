package ink.whi.video.repo.qiniu.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.video.repo.qiniu.entity.QiniuConfigDO;
import ink.whi.video.repo.qiniu.mapper.QiNiuConfigMapper;
import org.springframework.stereotype.Service;

/**
 * @author: qing
 * @Date: 2023/10/25
 */
@Service
public class QiniuConfigDao extends ServiceImpl<QiNiuConfigMapper, QiniuConfigDO> {

    /**
     * 编辑类型
     *
     * @param type
     */
    void updateType(String type) {

    }

    public QiniuConfigDO getConfig() {
        return lambdaQuery().one();
    }
}
