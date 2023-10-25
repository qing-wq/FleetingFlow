package ink.whi.video.service;

import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.video.model.vo.QiniuQueryCriteria;
import ink.whi.video.repo.qiniu.entity.QiniuConfigDO;
import ink.whi.video.repo.qiniu.entity.QiniuContentDO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: qing
 * @Date: 2023/10/25
 */
public interface QiNiuService {
    QiniuConfigDO getConfig();

    void config(QiniuConfigDO config);

    PageListVo<QiniuContentDO> queryFiles(QiniuQueryCriteria criteria, PageParam pageParam);

    QiniuContentDO upload(MultipartFile file, QiniuConfigDO config);
}
