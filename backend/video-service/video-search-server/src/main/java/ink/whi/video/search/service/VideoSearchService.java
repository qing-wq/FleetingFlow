package ink.whi.video.search.service;

import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.video.search.repo.entity.VideoDoc;

/**
 * @author qing
 * @date 2023/11/4
 */
public interface VideoSearchService {

	/**
	 * 搜索博客
	 *
	 * @param searchKey 关键字
	 * @return
	 */
	PageListVo<VideoDoc> searchVideo(String searchKey, PageParam pageParam);
}
