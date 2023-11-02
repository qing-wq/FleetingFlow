package ink.whi.video.repo.video.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.common.enums.PushStatusEnum;
import ink.whi.common.enums.YesOrNoEnum;
import ink.whi.common.model.page.PageParam;
import ink.whi.video.model.video.CategoryDTO;
import ink.whi.video.repo.video.converter.VideoConverter;
import ink.whi.video.repo.video.entity.CategoryDO;
import ink.whi.video.repo.video.mapper.CategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/29
 */
@Repository
public class CategoryDao extends ServiceImpl<CategoryMapper, CategoryDO> {
    /**
     * 获取分类ID
     * @param category
     * @return
     */
    public Long getIdByCategoryName(String category) {
        return lambdaQuery().eq(CategoryDO::getCategoryName, category)
                .eq(CategoryDO::getStatus, PushStatusEnum.ONLINE.getCode())
                .eq(CategoryDO::getDeleted, YesOrNoEnum.NO.getCode())
                .one().getId();
    }

    public List<CategoryDO> listAllCategoriesFromDb() {
        return lambdaQuery().eq(CategoryDO::getStatus, PushStatusEnum.ONLINE.getCode())
                .eq(CategoryDO::getDeleted, YesOrNoEnum.NO.getCode())
                .orderByAsc(CategoryDO::getRank)
                .list();
    }

    public List<CategoryDTO> listCategory(PageParam pageParam) {
        List<CategoryDO> list = lambdaQuery().eq(CategoryDO::getDeleted, YesOrNoEnum.NO.getCode())
                .last(PageParam.getLimitSql(pageParam))
                .list();
        return VideoConverter.toCategoryDtoList(list);
    }

    public Integer countCategory() {
        return lambdaQuery().eq(CategoryDO::getDeleted, YesOrNoEnum.NO.getCode())
                .count().intValue();
    }
}
