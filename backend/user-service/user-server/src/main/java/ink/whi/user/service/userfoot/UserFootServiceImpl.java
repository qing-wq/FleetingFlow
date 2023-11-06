package ink.whi.user.service.userfoot;

import ink.whi.common.enums.OperateTypeEnum;
import ink.whi.common.enums.VideoTypeEnum;
import ink.whi.common.model.dto.CommentDTO;
import ink.whi.common.model.dto.UserFootDTO;
import ink.whi.common.model.page.PageParam;
import ink.whi.user.repo.converter.UserConverter;
import ink.whi.user.repo.dao.UserFootDao;
import ink.whi.user.repo.entity.UserFootDO;
import ink.whi.user.service.UserFootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Service
public class UserFootServiceImpl implements UserFootService {
    @Autowired
    private UserFootDao userFootDao;

    /**
     * 保存/更新用户足迹
     * @param type
     * @param videoId
     * @param author
     * @param userId
     * @param operate
     * @return
     */
    @Override
    public UserFootDO saveOrUpdateUserFoot(VideoTypeEnum type, Long videoId, Long author, Long userId, OperateTypeEnum operate) {
        UserFootDO record = userFootDao.getRecordByVideoAndUserId(type, videoId,  userId);
        if (record == null) {
            record = new UserFootDO();
            record.setUserId(userId);
            record.setVideoId(videoId);
            record.setType(type.getCode());
            record.setVideoUserId(author);
            setUserFootStat(record, operate);
            userFootDao.save(record);
        } else if (setUserFootStat(record, operate)) {
            record.setUpdateTime(new Date());
            userFootDao.updateById(record);
        }
        return record;
    }

    /**
     * 获取用户和评论关系
     *
     * @param commentId
     * @param documentType
     * @param userId
     * @return
     */
    @Override
    public UserFootDTO queryUserFoot(Long commentId, Integer documentType, Long userId) {
        UserFootDO foot = userFootDao.getByDocumentAndUserId(commentId, documentType, userId);
        return UserConverter.toDTO(foot);
    }

    /**
     * 查询用户浏览记录
     * @param userId
     * @param pageParam
     * @return
     */
    @Override
    public List<Long> queryUserReadVideoList(Long userId, PageParam pageParam) {
        return userFootDao.listReadVideoByUserId(userId, pageParam);
    }

    /**
     * 查询用户收藏记录
     * @param userId
     * @param pageParam
     * @return
     */
    @Override
    public List<Long> queryUserCollectionVideoList(Long userId, PageParam pageParam) {
        return userFootDao.listCollectedVideosByUserId(userId, pageParam);
    }

    @Override
    public void saveCommentFoot(CommentDTO comment, Long authorId, Long parentCommentUser) {
        // 视频已评 + 父评论已评
        saveOrUpdateUserFoot(VideoTypeEnum.VIDEO, comment.getVideoId(), authorId, comment.getUserId(), OperateTypeEnum.COMMENT);
        if (parentCommentUser != null && comment.getParentCommentId() != 0) {
            saveOrUpdateUserFoot(VideoTypeEnum.COMMENT, comment.getParentCommentId(), parentCommentUser, comment.getUserId(), OperateTypeEnum.COMMENT);
        }
    }

    private boolean setUserFootStat(UserFootDO userFootDO, OperateTypeEnum operate) {
        switch (operate) {
            case READ:
                userFootDO.setReadStat(1);
                // 需要更新时间，用于浏览记录
                userFootDO.setUpdateTime(new Date());
                return true;
            case PRAISE:
            case CANCEL_PRAISE:
                return compareAndUpdate(userFootDO::getPraiseStat, userFootDO::setPraiseStat, operate.getDbStatCode());
            case COLLECTION:
            case CANCEL_COLLECTION:
                return compareAndUpdate(userFootDO::getCollectionStat, userFootDO::setCollectionStat, operate.getDbStatCode());
            case COMMENT:
            case DELETE_COMMENT:
                return compareAndUpdate(userFootDO::getCommentStat, userFootDO::setCommentStat, operate.getDbStatCode());
            default:
                return false;
        }
    }

    private <T> boolean compareAndUpdate(Supplier<T> supplier, Consumer<T> consumer, T input) {
        if (Objects.equals(supplier.get(), input)) {
            return false;
        }
        consumer.accept(input);
        return true;
    }
}
