package ink.whi.user.controller;

import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.vo.dto.UserFootDTO;
import ink.whi.user.repo.converter.UserConverter;
import ink.whi.user.repo.entity.UserFootDO;
import ink.whi.user.service.UserFootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * feign 远程调用接口
 *
 * @author: qing
 * @Date: 2023/10/27
 */
@RestController
@RequestMapping(path = "client")
public class UserClientRestController {

    @Autowired
    private UserFootService userFootService;

    /**
     * 保存/更新用户足迹
     *
     * @param type VideoTypeEnum
     * @param videoId
     * @param author
     * @param userId
     * @param operateTypeEnum
     * @return
     */
    @PostMapping(path = "client/foot/update")
    public UserFootDTO saveUserFoot(VideoTypeEnum type, Long videoId, Long author, Long userId, OperateTypeEnum operateTypeEnum) {
        UserFootDO foot = userFootService.saveOrUpdateUserFoot(type, videoId, author, userId, operateTypeEnum);
        return UserConverter.toDTO(foot);
    }
}
