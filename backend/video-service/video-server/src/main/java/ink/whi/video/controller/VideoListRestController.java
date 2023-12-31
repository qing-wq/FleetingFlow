package ink.whi.video.controller;

import ink.whi.common.base.BaseRestController;
import ink.whi.common.model.ResVo;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.video.dto.CategoryDTO;
import ink.whi.video.dto.TagDTO;
import ink.whi.video.service.CategoryService;
import ink.whi.video.service.VideoService;
import ink.whi.video.utils.AIUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频分类、参数接口
 * @author: qing
 * @Date: 2023/10/29
 */
@RestController
@RequestMapping(path = "video")
public class VideoListRestController extends BaseRestController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VideoService videoService;

    /**
     * 查询所有分类
     *
     * @return
     */
    @GetMapping(path = "category")
    public ResVo<List<CategoryDTO>> listCategory() {
        List<CategoryDTO> list = categoryService.loadAllCategories();
        return ResVo.ok(list);
    }

    /**
     * 查询所有标签
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(path = "tags")
    public ResVo<PageListVo<TagDTO>> listTags(@RequestParam(name = "page") Long pageNum,
                                              @RequestParam(name = "pageSize", required = false) Long pageSize,
                                              @PathVariable Long categoryId) {
        PageParam pageParam = buildPageParam(pageNum, pageSize);
        PageListVo<TagDTO> list = videoService.queryTagsList(categoryId, pageParam);
        return ResVo.ok(list);
    }

    /**
     * 标签推荐
     * @param key
     * @return
     * @throws JSONException
     */
    @GetMapping("tags/recommend")
    public ResVo<List<String>> tags(@RequestParam String key) throws JSONException {
        List<String> tagRecommendResults = AIUtil.getTagRecommendResults(key);
        return ResVo.ok(tagRecommendResults);
    }
}
