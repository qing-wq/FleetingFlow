package ink.whi.video.utils;


import ink.whi.video.model.req.InteractionReq;
import lombok.Getter;

import static ink.whi.video.utils.UserInteractionUtil.InteractionType.WATCH;

public class UserInteractionUtil {

    /**
     * 类型：      "type": 1
     * 说明：      用户是否观看视频，data返回0或1
     * 触发：      用户播放视频立即触发
     * 返回：      "data": 0
     * <p>
     * 类型：      "type": 2
     * 说明：      用户播放时长百分比，如 74%
     * 触发：      用户切换视频，且播放时长 > 10% 触发
     * 返回：      "data": 74
     * <p>
     * 类型：      "type": 3
     * 说明：      用户是否查看评论，返回1或0
     * 触发：      用户点击评论按钮触发
     * 返回：      "data": 1
     * <p>
     * 类型：      "type": 4
     * 说明：      用户是否查看点赞，返回1或0
     * 触发：      用户点击点赞按钮触发
     * 返回：      "data": 1
     * <p>
     * 类型：      "type": 5
     * 说明：      用户是否查看转发，返回1或0
     * 触发：      用户点击转发按钮触发
     * 返回：      "data": 1
     * <p>
     * 类型：      "type": 6
     * 说明：      用户是否收藏，返回1或0
     * 触发：      用户收藏视频触发
     * 返回：      "data": 1
     */
    @Getter
    public enum InteractionType {
        WATCH(1), WATCH_PERCENT(2), COMMENT(3), LIKE(4), FORWARD(5), COLLECT(6);

        private final int type;

        InteractionType(int type) {
            this.type = type;
        }

    }

    public static float getScoreChange(InteractionReq req, float nowScore) {
        float result;
        int data = req.getData();
        int type = req.getType();
        // to Enum
        InteractionType interactionType = InteractionType.values()[type - 1];
        switch (interactionType) {
            case WATCH -> result = nowScore == 0 ? 0.1f : -0.5f;
            case WATCH_PERCENT -> result = (float) (Math.log1p(data) / 2.0);
            case COMMENT -> result = 1f;
            case LIKE -> result = 2f;
            case FORWARD, COLLECT -> result = 2.5f;
            default -> result = 0f;
        }
        return result;
    }
}
