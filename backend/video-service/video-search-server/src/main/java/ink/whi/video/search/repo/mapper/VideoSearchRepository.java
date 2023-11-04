package ink.whi.video.search.repo.mapper;

import ink.whi.common.model.page.PageParam;
import ink.whi.video.search.repo.entity.VideoDoc;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author qing
 * @date 2023/11/4
 */
public interface VideoSearchRepository extends ElasticsearchRepository<VideoDoc, Long> {

	/**
	 * 通过描述内容来搜索博客
	 * <br><br>
	 * 这里通过 @Highlight 完成了对高亮的需求，
	 * 其中 requireFieldMatch 参数是取消了只有字段匹配才有高亮的规则
	 * <br>
	 * 并通过 Pageable 和 SearchPage 完成了对分页的需求
	 * <br>
	 * 得到结果后仅需将分页的内容替换掉实体类的内容即可，已经是封装好的了
	 *
	 * @param descriptiveContent 描述语句
	 * @param page           分页
	 * @return 博客列表
	 */
	@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
	@Highlight(fields = {
			@HighlightField(name = "title", parameters = @HighlightParameters(requireFieldMatch = false)),
			@HighlightField(name = "description", parameters = @HighlightParameters(requireFieldMatch = false)),
	})
	SearchPage<VideoDoc> findByDescriptiveContent(String descriptiveContent, PageParam page);
}
