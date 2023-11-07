package ink.whi.video.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ink.whi.common.enums.PushStatusEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 发布视频入参
 *
 * @author qing
 * @date 2023/10/26
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoPostReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 3360181337899172705L;
    
    /**
     * 视频ID(非必填)
     */
    private Long videoId;

    /**
     * 视频文件
     */
    @JsonIgnore
    private MultipartFile file;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 分类(不用填)
     */
    private Long categoryId;

    /**
     * 标签
     */
    private Set<Long> tagIds;

    /**
     * 视频介绍
     */
    private String thumbnail;

    /**
     * 视频头图
     */
    private String picture;

    /**
     * 视频地址
     */
    private String url;

    /**
     * POST 发表, SAVE 暂存 DELETE 删除
     */
    private String actionType;

    public PushStatusEnum pushStatus() {
        if ("post".equalsIgnoreCase(actionType)) {
            return PushStatusEnum.ONLINE;
        } else {
            return PushStatusEnum.OFFLINE;
        }
    }
}