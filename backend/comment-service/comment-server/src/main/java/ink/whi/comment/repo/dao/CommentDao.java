package ink.whi.comment.repo.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.comment.repo.mapper.CommentMapper;
import ink.whi.comment.repo.entity.CommentDO;
import ink.whi.common.enums.YesOrNoEnum;
import ink.whi.common.model.page.PageParam;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author: qing
 * @Date: 2023/11/2
 */
@Repository
public class CommentDao extends ServiceImpl<CommentMapper, CommentDO> {
    /**
     * 查询文章评论数量
     * @param videoId
     * @return
     */
    public Integer countCommentByVideoId(Long videoId) {
        return lambdaQuery().eq(CommentDO::getVideoId, videoId)
                .eq(CommentDO::getDeleted, YesOrNoEnum.NO.getCode())
                .count().intValue();
    }

    /**
     * 顶级评论
     * @param videoId
     * @param page
     * @return
     */
    public List<CommentDO> listTopCommentList(Long videoId, PageParam page) {
        return lambdaQuery()
                .eq(CommentDO::getTopCommentId, 0)
                .eq(CommentDO::getVideoId, videoId)
                .eq(CommentDO::getDeleted, YesOrNoEnum.NO.getCode())
                .last(PageParam.getLimitSql(page))
                .orderByDesc(CommentDO::getId).list();
    }

    /**
     * 非顶级评论
     * @param videoId
     * @param topCommentIds
     * @return
     */
    public List<CommentDO> listSubCommentIdMappers(Long videoId, Set<Long> topCommentIds) {
        return lambdaQuery()
                .in(CommentDO::getTopCommentId, topCommentIds)
                .eq(CommentDO::getVideoId, videoId)
                .eq(CommentDO::getDeleted, YesOrNoEnum.NO.getCode()).list();
    }
}
