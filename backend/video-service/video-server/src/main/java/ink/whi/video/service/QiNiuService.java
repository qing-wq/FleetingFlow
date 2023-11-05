package ink.whi.video.service;

import ink.whi.video.dto.VideoInfoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: qing
 * @Date: 2023/10/25
 */
public interface QiNiuService {

    String upload(MultipartFile file) throws IOException;

    String download(VideoInfoDTO videoInfoDTO);

    String uploadImage(MultipartFile file) throws IOException;
}
