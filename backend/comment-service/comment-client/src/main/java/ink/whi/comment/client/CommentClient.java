package ink.whi.comment.client;

import ink.whi.comment.dto.TopCommentDTO;
import ink.whi.common.model.dto.CommentDTO;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: qing
 * @Date: 2023/11/2
 */
@FeignClient(value = "comment-service", fallback = CommentClientResolver.class)
public interface CommentClient {

    @GetMapping(path = "client/comment/{commentId}")
    CommentDTO queryComment(@PathVariable Long commentId);

    @GetMapping(path = "client/video/{videoId}")
    Integer queryCommentCount(@PathVariable Long videoId);

    @GetMapping(path = "comment/api/page")
    PageListVo<TopCommentDTO> listVideoComment(@RequestParam Long videoId, @RequestParam Long pageNum,
                                               @RequestParam(required = false) Long pageSize);
}
