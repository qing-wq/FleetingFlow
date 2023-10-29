package ink.whi.video.service;

import ink.whi.video.model.video.CategoryDTO;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/29
 */
public interface CategoryService {
    String queryCategoryName(Long categoryId);

    List<CategoryDTO> loadAllCategories();

    void refreshCache();

    Long queryCategoryId(String category);
}
