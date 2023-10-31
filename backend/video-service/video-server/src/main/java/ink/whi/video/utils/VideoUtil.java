package ink.whi.video.utils;



public class VideoUtil {

    public enum VideoType {
        Vertical, Horizontal, Square
    }

    public static final long r360p = 640 * 360;
    public static final long r480p = 854 * 480;
    public static final long r720p = 1280 * 720;
    public static final long r1080p = 1920 * 1080;
    public static final long r4k = 3840 * 2160;

    private static final long[] resolutions =   {r360p, r480p,  r720p, r1080p, r4k};
    public static final String[] multiVb =      {"200k","450k", "850k", "2250k", "17000k"};
    public static final int[] envBandWidth =    {200000, 450000, 850000, 2250000, 17000000};
    private static final int[][] resolutionsHW = {{640, 360}, {854, 480}, {1280, 720}, {1920, 1080}, {3840, 2160}};

    // Refer: https://help.aliyun.com/zh/live/developer-reference/what-resolutions-and-bitrates-does-apsaravideo-live-support


    /**
     * 获取视频等级
     * @param width 宽度
     * @param height 高度
     * @return int 等级, -1 为未知，0 为 360p，1 为 480p，2 为 720p，3 为 1080p，4 为 4k
     */
    public static int getLevel(int width, int height) {
        long resolution = ((long) width) * ((long) height);
        int level = -1;
        for (int i = 0; i < resolutions.length; i++) {
            if (resolution >= resolutions[i]) {
                level = i;
            }
        }
        return level;
    }

    /**
     * 获取视频等级名称
     * @param level
     * @return String 等级名称
     */
    public static String getLevelName(int level) {
        return switch (level) {
            case 0 -> "360p";
            case 1 -> "480p";
            case 2 -> "720p";
            case 3 -> "1080p";
            case 4 -> "4k";
            default -> "unknown";
        };
    }

    /**
     * 获取视频比例
     * @param width
     * @param height
     * @return float 比例
     */
    public static float getVideoRatio(int width, int height) {
        return ((float) width) / ((float) height);
    }

    public static VideoType getVideoType(int width, int height) {
        float ratio = getVideoRatio(width, height);
        if (ratio > 1) {
            return VideoType.Horizontal;
        } else if (ratio < 1) {
            return VideoType.Vertical;
        } else {
            return VideoType.Square;
        }
    }

    public static int[][] getVideoScales(int width, int height) {
        int level = getLevel(width, height);
        int[][] scales = new int[level + 1][2];
        float ratio = getVideoRatio(width, height);
        VideoType type = getVideoType(width, height);

        if(type == VideoType.Horizontal) {
            for (int i = 0; i <= level; i++) {
                scales[i][0] = resolutionsHW[i][0];
                scales[i][1] = (int) (resolutionsHW[i][0] / ratio);
            }
        } else if(type == VideoType.Vertical) {
            for (int i = 0; i <= level; i++) {
                scales[i][0] = (int) (resolutionsHW[i][1] * ratio);
                scales[i][1] = resolutionsHW[i][1];
            }
        } else {
            for (int i = 0; i <= level; i++) {
                scales[i][0] = resolutionsHW[i][1];
                scales[i][1] = resolutionsHW[i][1];
            }
        }

        scales[level][0] = width;
        scales[level][1] = height;

        return scales;
    }

    public static String getResolutionCommand(int[][] scales) {
        StringBuilder sb = new StringBuilder();
        for (int[] scale : scales) {
            sb.append(scale[0]).append(":").append(scale[1]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String getEnvBandWidthCommand(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= level; i++) {
            sb.append(envBandWidth[i]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();

    }

    public static String getMultiVbCommand(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= level; i++) {
            sb.append(multiVb[i]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String getCommand(int width, int height) {
        int[][] scales = getVideoScales(width, height);
        StringBuilder sb = new StringBuilder();
        sb.append("adapt/m3u8/");

        // MultiResolution Part
        sb.append("multiResolution/");
        sb.append(getResolutionCommand(scales));
        sb.append("/");

        // envBandWidth Part
        sb.append("envBandWidth/");
        sb.append(getEnvBandWidthCommand(scales.length - 1));
        sb.append("/");

        // multiVb Part
        sb.append("multiVb/");
        sb.append(getMultiVbCommand(scales.length - 1));
        sb.append("/");


        return sb.toString();
    }


}
