package ink.whi.video.repo.qiniu.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.video.repo.qiniu.entity.QiniuContent;
import ink.whi.video.repo.qiniu.mapper.QiniuContentMapper;
import org.springframework.stereotype.Repository;

/**
 * @author: qing
 * @Date: 2023/10/25
 */
@Repository
public class QiniuContentDao extends ServiceImpl<QiniuContentMapper, QiniuContent> {

    public QiniuContent queryByKey(String key) {
        return lambdaQuery().eq(QiniuContent::getKeyName, key).one();
    }
}
