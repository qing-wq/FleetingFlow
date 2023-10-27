package ink.whi.video.repo.video.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.video.repo.qiniu.entity.QiniuConfig;
import ink.whi.video.repo.video.entity.CategoryDO;
import org.springframework.stereotype.Component;

/**
 * @author qing
 * @date 2023/10/25
 */
@Component
public interface CategoryMapper extends BaseMapper<CategoryDO> {
}
