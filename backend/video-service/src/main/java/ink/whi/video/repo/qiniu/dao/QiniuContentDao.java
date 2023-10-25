package ink.whi.video.repo.qiniu.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.video.repo.qiniu.entity.QiniuContentDO;
import ink.whi.video.repo.qiniu.mapper.QiniuContentMapper;
import org.springframework.stereotype.Service;

/**
 * @author: qing
 * @Date: 2023/10/25
 */
@Service
public class QiniuContentDao extends ServiceImpl<QiniuContentMapper, QiniuContentDO> {

    public QiniuContentDO getByFileName(String fileName) {
        return lambdaQuery().eq(QiniuContentDO::getKey, fileName).one();
    }
}
