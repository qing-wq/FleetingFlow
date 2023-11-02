package ink.whi.video.controller;

import ink.whi.common.base.BaseRestController;
import ink.whi.common.vo.ResVo;
import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.video.model.req.TagReq;
import ink.whi.video.model.video.CategoryDTO;
import ink.whi.video.model.video.TagDTO;
import ink.whi.video.repo.video.dao.VideoDao;
import ink.whi.video.service.CategoryService;
import ink.whi.video.service.VideoService;
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
//    @GetMapping(path = "category")
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
    @GetMapping(path = "tags/{categoryId}")
    public ResVo<PageListVo<TagDTO>> listTags(@RequestParam(name = "page") Long pageNum,
                                              @RequestParam(name = "pageSize", required = false) Long pageSize,
                                              @PathVariable Long categoryId) {
        PageParam pageParam = buildPageParam(pageNum, pageSize);
        PageListVo<TagDTO> list = videoService.queryTagsList(categoryId, pageParam);
        return ResVo.ok(list);
    }

    /**
     * 添加标签
     * @param req
     * @return
     */
//    @Permission(role = UserRole.ADMIN)
    @PostMapping(path = "tag/save")
    public ResVo<Long> save(@RequestBody TagReq req) {
        Long tagId = videoService.saveTag(req);
        return ResVo.ok(tagId);
    }
}
