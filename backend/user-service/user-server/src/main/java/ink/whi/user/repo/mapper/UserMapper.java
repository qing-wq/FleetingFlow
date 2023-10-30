package ink.whi.user.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.user.repo.entity.UserDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: qing
 * @Date: 2023/10/16
 */
@Component
public interface UserMapper extends BaseMapper<UserDO> {

    @Select("select id from user where id > #{offsetUserId} order by id asc limit #{size}")
    List<Long> getUserIdsOrderByIdAsc(@Param("offsetUserId") Long offsetUserId, @Param("size") Long size);
}
