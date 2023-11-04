package ink.whi.video.search.controller;

import ink.whi.common.base.BaseRestController;
import ink.whi.common.model.ResVo;
import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.video.search.repo.entity.VideoDoc;
import ink.whi.video.search.service.VideoSearchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 视频搜索接口
 * @author qing
 * @date 2023/11/4
 */
@RestController
@RequestMapping("search")
public class VideoSearchController extends BaseRestController {

	@Resource
	private VideoSearchService videoSearchService;


	/**
	 * 视频搜索
	 * @param key 关键词
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping(path = "")
	public ResVo<PageListVo<VideoDoc>> searchVideo(@RequestParam("key") String key,
												   @RequestParam(name = "page") Long page,
												   @RequestParam(name = "size", required = false) Long size) {
		PageParam pageParam = buildPageParam(page, size);
		PageListVo<VideoDoc> result = videoSearchService.searchVideo(key, pageParam);
		return ResVo.ok(result);
	}
}
