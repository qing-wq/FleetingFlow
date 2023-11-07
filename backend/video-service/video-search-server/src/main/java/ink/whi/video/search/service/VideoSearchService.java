package ink.whi.video.search.service;

import ink.whi.common.model.page.PageParam;
import ink.whi.video.search.repo.entity.VideoDoc;

import java.util.List;

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
	List<VideoDoc> searchVideo(String searchKey, PageParam pageParam);
}
