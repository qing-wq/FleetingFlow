package ink.whi.comment.controller;

import ink.whi.comment.service.CommentReadService;
import ink.whi.common.model.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * feign远程调用接口
 * @author: qing
 * @Date: 2023/11/2
 */
@RestController
@RequestMapping(path = "client")
public class CommentClientController {

    @Autowired
    private CommentReadService commentReadService;

    @GetMapping(path = "client/{commentId}")
    CommentDTO queryComment(@PathVariable Long commentId) {

    }
}
