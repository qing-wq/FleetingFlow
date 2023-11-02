package ink.whi.comment.repo.converter;


import ink.whi.comment.model.dto.BaseCommentDTO;
import ink.whi.comment.model.dto.SubCommentDTO;
import ink.whi.comment.model.dto.TopCommentDTO;
import ink.whi.comment.model.req.CommentSaveReq;
import ink.whi.comment.repo.entity.CommentDO;
import ink.whi.common.model.dto.CommentDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;

/**
 * @author: qing
 * @Date: 2023/11/2
 */
public class CommentConverter {

    public static CommentDO toDo(CommentSaveReq req) {
        if (req == null) {
            return null;
        }
        CommentDO commentDO = new CommentDO();
        commentDO.setId(req.getCommentId());
        commentDO.setVideoId(req.getArticleId());
        commentDO.setUserId(req.getUserId());
        commentDO.setContent(req.getCommentContent());
        commentDO.setParentCommentId(req.getParentCommentId() == null ? 0L : req.getParentCommentId());
        commentDO.setTopCommentId(req.getTopCommentId() == null ? 0L : req.getTopCommentId());
        return commentDO;
    }

    private static <T extends BaseCommentDTO> void parseDto(CommentDO comment, T sub) {
        sub.setCommentId(comment.getId());
        sub.setUserId(comment.getUserId());
        sub.setCommentContent(comment.getContent());
        sub.setCommentTime(comment.getCreateTime().getTime());
        sub.setPraiseCount(0);
    }

    public static TopCommentDTO toTopDto(CommentDO commentDO) {
        TopCommentDTO dto = new TopCommentDTO();
        parseDto(commentDO, dto);
        dto.setChildComments(new ArrayList<>());
        return dto;
    }

    public static SubCommentDTO toSubDto(CommentDO comment) {
        SubCommentDTO sub = new SubCommentDTO();
        parseDto(comment, sub);
        return sub;
    }

    public static CommentDTO toDto(CommentDO comment) {
        CommentDTO dto = new CommentDTO();
        BeanUtils.copyProperties(comment, dto);
        dto.setVideoId(comment.getVideoId());
        return dto;
    }
}
