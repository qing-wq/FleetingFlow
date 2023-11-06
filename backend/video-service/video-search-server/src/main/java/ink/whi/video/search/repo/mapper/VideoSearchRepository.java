package ink.whi.video.search.repo.mapper;

import ink.whi.common.model.page.PageParam;
import ink.whi.video.search.repo.entity.VideoDoc;
import org.springframework.data.domain.Pageable;
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
     *
     * @param descriptiveContent 描述语句
     * @param pageable               分页
     * @return 博客列表
     */
    @Highlight(fields = {
            @HighlightField(name = "title", parameters = @HighlightParameters(requireFieldMatch = false)),
            @HighlightField(name = "description", parameters = @HighlightParameters(requireFieldMatch = false)),
    })
    SearchPage<VideoDoc> findByDescriptiveContent(String descriptiveContent, Pageable pageable);
}
