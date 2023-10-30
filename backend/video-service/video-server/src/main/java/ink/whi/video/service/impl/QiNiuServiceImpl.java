package ink.whi.video.service.impl;

import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.common.utils.JsonUtil;
import ink.whi.video.model.dto.QiniuQueryCriteria;
import ink.whi.video.model.dto.VideoInfoDTO;
import ink.whi.video.repo.qiniu.dao.QiniuConfigDao;
import ink.whi.video.repo.qiniu.dao.QiniuContentDao;
import ink.whi.video.repo.qiniu.entity.QiniuConfig;
import ink.whi.video.repo.qiniu.entity.QiniuContent;
import ink.whi.video.service.QiNiuService;
import ink.whi.video.utils.FileUtil;
import ink.whi.video.utils.QiNiuUtil;
import ink.whi.video.utils.VideoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author: qing
 * @Date: 2023/10/25
 */
@Service
public class QiNiuServiceImpl implements QiNiuService {

    @Autowired
    private QiniuConfigDao qiniuConfigDao;

    @Autowired
    private QiniuContentDao qiniuContentDao;

    @Override
    public QiniuConfig getConfig() {
        QiniuConfig config = qiniuConfigDao.getConfig();
        if (config == null) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_OPERATE, "请先添加相应配置");
        }
        return config;
    }

    @Override
    public void setConfig(QiniuConfig config) {
        String http = "http://", https = "https://";
        if (!(config.getHost().toLowerCase().startsWith(http) || config.getHost().toLowerCase().startsWith(https))) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "外链域名必须以http://或者https://开头");
        }
        qiniuConfigDao.save(config);
    }

    @Override
    public PageListVo<QiniuContent> queryFiles(QiniuQueryCriteria criteria, PageParam pageParam) {
        return null;
    }

    @Override
    public DefaultPutRet upload(MultipartFile file) throws IOException {

        // 检查文件大小
//        FileUtil.checkSize(maxSize, file.getSize());
        //todo: 配置可以加到caffeine缓存
        QiniuConfig config = getConfig();
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "文件名非法");
        }
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());

        // 获取视频信息，用来进行视频转码规则判定
        MultimediaInfo info = FileUtil.getVideoInfo(file);
        int H = info.getVideo().getSize().getHeight();
        int W = info.getVideo().getSize().getWidth();

        System.out.println("H: " + H + " W: " + W);

        System.out.println(Arrays.deepToString(VideoUtil.getVideoScales(1080, 1080)));

        String upToken = auth.uploadToken(config.getBucket());

        // 考虑数据安全，对文件名进行hash
        String key = FileUtil.calculateHash(filename);
        // 如果存在同名文件，加上日期避免冲突
        if (qiniuContentDao.queryByKey(key) != null) {
            key = QiNiuUtil.genTmpFileName(key);
        }

        // 上传kodo
        Response response = uploadManager.put(file.getBytes(), key, upToken);
        return JsonUtil.toObj(response.bodyString(), DefaultPutRet.class);
    }

    @Override
    public QiniuContent queryContentById(Long videoId) {
        QiniuContent content = qiniuContentDao.getById(videoId);
        if (content == null) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "id 不存在");
        }
        return content;
    }

    @Override
    public String download(VideoInfoDTO videoInfoDTO, QiniuConfig config) {
        String finalUrl;
        String type = "公开";
        if (type.equals(videoInfoDTO.getType())) {
            finalUrl = videoInfoDTO.getUrl();
        } else {
            Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
            long expireInSeconds = 3600;    // 1小时
            finalUrl = auth.privateDownloadUrl(videoInfoDTO.getUrl(), expireInSeconds);
        }
        return finalUrl;
    }
}
