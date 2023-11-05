package ink.whi.video.service.impl;

import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import ink.whi.common.enums.FileTypeEnum;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.properties.QiniuConfigProperties;
import ink.whi.common.utils.JsonUtil;
import ink.whi.video.dto.VideoInfoDTO;
import ink.whi.video.service.QiNiuService;
import ink.whi.video.utils.FileUtil;
import ink.whi.video.utils.QiNiuUtil;
import ink.whi.video.utils.VideoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.info.MultimediaInfo;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author: qing
 * @Date: 2023/10/25
 */
@Service
public class QiNiuServiceImpl implements QiNiuService {

    @Autowired
    private QiniuConfigProperties config;

    @Override
    public String upload(MultipartFile file) throws IOException {
        //todo: 配置可以加到caffeine缓存
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "文件名非法");
        }
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));

        // 分片上传
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        cfg.resumableUploadMaxConcurrentTaskCount = 3; // 3 并发上传

        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());

        // 使用uuid重命名文件
        String key = UUID.randomUUID().toString().replace("-", "") + "." + FileUtil.getExtensionName(filename);

        // 获取视频信息，用来进行视频转码规则判定
        MultimediaInfo info = FileUtil.getVideoInfo(file);
        int H = info.getVideo().getSize().getHeight();
        int W = info.getVideo().getSize().getWidth();

        // 视频转码为m3u8
        String command =  String.format(VideoUtil.getCommand(W, H) + "|saveas/%s", UrlSafeBase64.encodeToString(config.getBucket() + ":" + key + ".m3u8"));
        StringMap policy = new StringMap();
        policy.put("persistentOps", command);
        String upToken = auth.uploadToken(config.getBucket(), key, 3600, policy);

        // 上传kodo
        Response response = uploadManager.put(file.getBytes(), key, upToken);
        Map resultMap = JsonUtil.toObj(response.bodyString(), Map.class);
        System.out.println("resultMap" + resultMap);
        return key + ".m3u8";
    }

    @Override
    public String download(VideoInfoDTO videoInfoDTO) {
        String finalUrl;
        if (videoInfoDTO.getType() == FileTypeEnum.PUBLIC.getCode()) {
            finalUrl = videoInfoDTO.getUrl();
        } else {
            Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
            long expireInSeconds = 3600;    // 设置过期时间为1小时
            finalUrl = auth.privateDownloadUrl(videoInfoDTO.getUrl(), expireInSeconds);
        }
        return finalUrl;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());

        String key = UUID.randomUUID().toString().replace("-", "") + "." + FileUtil.getExtensionName(filename);
        String upToken = auth.uploadToken(config.getBucket(), key);

        // 使用uuid重命名文件
        Response response = uploadManager.put(file.getBytes(), key, upToken);
        DefaultPutRet putRet = JsonUtil.toObj(response.bodyString(), DefaultPutRet.class);
        return config.buildUrl(putRet.key);
    }
}
