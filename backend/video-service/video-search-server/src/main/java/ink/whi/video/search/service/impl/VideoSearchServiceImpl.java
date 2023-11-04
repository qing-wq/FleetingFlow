package ink.whi.video.search.service.impl;

import ink.whi.common.model.page.PageListVo;
import ink.whi.common.model.page.PageParam;
import ink.whi.common.properties.QiniuConfigProperties;
import ink.whi.video.search.repo.entity.VideoDoc;
import ink.whi.video.search.repo.mapper.VideoSearchRepository;
import ink.whi.video.search.service.VideoSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qing
 * @date 2023/11/4
 */
@Slf4j
@Service
public class VideoSearchServiceImpl implements VideoSearchService {

    @Resource
    private VideoSearchRepository videoSearchRepository;

    @Autowired
    private QiniuConfigProperties config;

    /**
     * 搜索视频
     *
     * @param searchKey 关键字
     * @return
     */
    @Override
    public PageListVo<VideoDoc> searchVideo(String searchKey, PageParam pageParam) {
        log.debug("searchQuery:{}", searchKey);
        SearchPage<VideoDoc> searchPage = videoSearchRepository.findByDescriptiveContent(searchKey, pageParam);
        log.debug("result number:{}", searchPage.getTotalElements());
        // 数据解析
        List<SearchHit<VideoDoc>> searchHitList = searchPage.getContent();
        ArrayList<VideoDoc> videoDocList = new ArrayList<>(searchHitList.size());
        for (SearchHit<VideoDoc> blogHit : searchHitList) {
            // 1.获取视频数据
            VideoDoc VideoDoc = blogHit.getContent();
            // 2.获取高亮数据
            Map<String, List<String>> fields = blogHit.getHighlightFields();
            System.out.println("fields " + fields);
            if (fields.size() > 0) {
                // 将高亮数据替换到原来的数据中
                BeanMap beanMap = BeanMap.create(VideoDoc);
                System.out.println("beanMap: " + beanMap);
                for (String name : fields.keySet()) {
                    beanMap.put(name, fields.get(name).get(0));
                }
            }

            // 封面图链接添加前缀
            if (VideoDoc.getPicture() != null) {
                config.buildUrl(VideoDoc.getPicture());
            }
            videoDocList.add(VideoDoc);
        }
        return PageListVo.newVo(videoDocList, pageParam.getPageSize());
    }

}
