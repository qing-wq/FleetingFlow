package ink.whi.user.client;

import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.model.dto.CommentDTO;
import ink.whi.common.model.dto.SimpleUserInfoDTO;
import ink.whi.common.model.dto.UserFootDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: qing
 * @Date: 2023/10/27
 */
@Slf4j
@Component
public class UserClientResolver implements UserClient {

	@Override
	public SimpleUserInfoDTO queryBasicUserInfo(Long userId) {
		log.error("User 服务异常：queryBasicUserInfo 请求失败");
		return null;
	}

	@Override
	public SimpleUserInfoDTO querySimpleUserInfo(Long userId) {
		log.error("User 服务异常：querySimpleUserInfo 请求失败");
		return null;
	}

	@Override
	public UserFootDTO saveUserFoot(VideoTypeEnum videoType, Long videoId, Long author, Long userId, OperateTypeEnum operate) {
		log.error("User 服务异常：saveUserFoot 请求失败");
		return null;
	}

	@Override
	public void saveCommentFoot(CommentDTO comment, Long userId, Long parentCommentUser) {
		log.error("User 服务异常：saveCommentFoot 请求失败");
	}

	@Override
	public Integer queryCommentPraiseCount(Long commentId) {
		return null;
	}

	@Override
	public UserFootDTO queryUserFoot(Long commentId, Integer type, Long loginUserId) {
		return null;
	}
}
