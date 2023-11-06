package ink.whi.notify.controller;

import ink.whi.common.base.BaseRestController;
import ink.whi.common.context.ReqInfoContext;
import ink.whi.common.enums.NotifyTypeEnum;
import ink.whi.common.model.ResVo;
import ink.whi.common.model.dto.NotifyMsgDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.notify.vo.NoticeResVo;
import ink.whi.notify.service.NotifyMsgService;
import ink.whi.web.auth.Permission;
import ink.whi.web.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 消息接口
 * @author: qing
 * @Date: 2023/11/6
 */
@RestController
@Permission(role = UserRole.LOGIN)
@RequestMapping(path = "notice")
public class NoticeRestController extends BaseRestController {

    @Autowired
    private NotifyMsgService notifyService;

    /**
     * 消息列表
     * @param type 消息类型，如：comment、reply、praise、collect、follow、system
     * @return
     */
    @GetMapping(path = {"/", "/{type}"})
    public ResVo<NoticeResVo> list(@PathVariable(name = "type", required = false) String type) {
        Long loginUserId = ReqInfoContext.getReqInfo().getUserId();
        Map<String, Integer> map = notifyService.queryUnreadCounts(loginUserId);

        NotifyTypeEnum typeEnum = type == null ? NotifyTypeEnum.COMMENT : NotifyTypeEnum.typeOf(type);

        NoticeResVo vo = new NoticeResVo();
        vo.setList(notifyService.queryUserNotices(loginUserId, typeEnum, new PageParam(1, 10, 0, 50)));
        vo.setSelectType(typeEnum.name().toLowerCase());
        vo.setUnreadCountMap(map);
        return ResVo.ok(vo);
    }

    /**
     * 消息分页接口
     * @param type 必需参数
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(path = "page/{type}")
    public ResVo<PageListVo<NotifyMsgDTO>> page(@PathVariable(name = "type") String type,
                                                @RequestParam(name = "page") Long pageNum,
                                                @RequestParam(name = "pageSize", required = false) Long pageSize) {
        PageParam pageParam = buildPageParam(pageNum, pageSize);
        NotifyTypeEnum typeEnum = NotifyTypeEnum.typeOf(type);

        Long loginUserId = ReqInfoContext.getReqInfo().getUserId();
        PageListVo<NotifyMsgDTO> list = notifyService.queryUserNotices(loginUserId, typeEnum, pageParam);
        return ResVo.ok(list);
    }
}
