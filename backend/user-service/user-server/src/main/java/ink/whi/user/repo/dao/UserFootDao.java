package ink.whi.user.repo.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.common.enums.CollectionStatEnum;
import ink.whi.common.enums.PraiseStatEnum;
import ink.whi.common.enums.ReadStatEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.model.base.BaseDO;
import ink.whi.common.model.page.PageParam;
import ink.whi.user.model.dto.VideoFootCountDTO;
import ink.whi.user.repo.entity.UserFootDO;
import ink.whi.user.repo.mapper.UserFootMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Repository
public class UserFootDao extends ServiceImpl<UserFootMapper, UserFootDO> {

    /**
     * 获取用户的视频点赞数，收藏数，浏览数
     * 
     * @param userId
     * @return
     */
    public VideoFootCountDTO countVideoStatisticByUserId(Long userId) {
        return baseMapper.countVideoByUserId(userId);
    }

    /**
     * 获取视频点赞数，收藏数，浏览数
     *
     * @param videoId
     * @return
     */
    public VideoFootCountDTO countVideoStatisticByVideoId(Long videoId) {
        return baseMapper.countVideoInfoByVideoId(videoId);
    }

    public UserFootDO getRecordByVideoAndUserId(VideoTypeEnum type, Long videoId, Long userId) {
        return lambdaQuery().eq(UserFootDO::getVideoId, videoId)
                .eq(UserFootDO::getUserId, userId)
                .eq(UserFootDO::getType, type.getCode())
                .one();
    }

    public UserFootDO getByDocumentAndUserId(Long commentId, Integer documentType, Long userId) {
        return lambdaQuery().eq(UserFootDO::getVideoId, commentId)
                .eq(UserFootDO::getType, documentType)
                .eq(UserFootDO::getUserId, userId)
                .one();
    }

    public Integer countCommentPraise(Long commentId) {
        return lambdaQuery().eq(UserFootDO::getVideoId, commentId)
                .eq(UserFootDO::getType, VideoTypeEnum.COMMENT.getCode())
                .eq(UserFootDO::getPraiseStat, PraiseStatEnum.PRAISE.getCode())
                .count().intValue();
    }

    /**
     * 用户浏览记录列表
     * @param userId
     * @param pageParam
     * @return
     */
    public List<Long> listReadVideoByUserId(Long userId, PageParam pageParam) {
        List<UserFootDO> list = lambdaQuery().eq(UserFootDO::getUserId, userId)
                .eq(UserFootDO::getReadStat, ReadStatEnum.READ.getCode())
                .eq(UserFootDO::getType, VideoTypeEnum.VIDEO.getCode())
                .last(PageParam.getLimitSql(pageParam))
                .orderByDesc(BaseDO::getUpdateTime)
                .list();
        return list.stream().map(UserFootDO::getVideoId).toList();
    }

    /**
     * 用户收藏列表
     * @param userId
     * @param pageParam
     * @return
     */
    public List<Long> listCollectedVideosByUserId(Long userId, PageParam pageParam) {
        List<UserFootDO> list = lambdaQuery().eq(UserFootDO::getUserId, userId)
                .eq(UserFootDO::getCollectionStat, CollectionStatEnum.COLLECTION.getCode())
                .eq(UserFootDO::getType, VideoTypeEnum.VIDEO.getCode())
                .orderByDesc(BaseDO::getUpdateTime)
                .last(PageParam.getLimitSql(pageParam))
                .list();
        return list.stream().map(UserFootDO::getVideoId).toList();
    }
}
