package ink.whi.comment.client;

import ink.whi.common.model.dto.CommentDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: qing
 * @Date: 2023/11/2
 */
public interface CommentClient {

    @GetMapping(path = "client/{commentId}")
    CommentDTO queryComment(@PathVariable Long commentId);
}
