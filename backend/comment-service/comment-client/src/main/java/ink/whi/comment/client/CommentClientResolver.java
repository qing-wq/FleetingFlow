package ink.whi.comment.client;

import ink.whi.common.model.dto.CommentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: qing
 * @Date: 2023/11/2
 */
@Slf4j
@Component
public class CommentClientResolver implements CommentClient {

	@Override
	public CommentDTO queryComment(Long comment) {
		log.error("Comment 服务异常：queryComment 请求失败");
		return null;
	}

	@Override
	public Integer queryCommentCount(Long videoId) {
		log.error("Comment 服务异常：queryCommentCount 请求失败");
		return null;
	}
}
