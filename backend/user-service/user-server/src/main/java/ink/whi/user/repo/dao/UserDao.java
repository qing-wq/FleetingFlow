package ink.whi.user.repo.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import ink.whi.common.enums.YesOrNoEnum;
import ink.whi.common.vo.dto.BaseUserDTO;
import ink.whi.common.vo.page.PageParam;
import ink.whi.user.repo.converter.UserConverter;
import ink.whi.user.repo.entity.UserDO;
import ink.whi.user.repo.entity.UserInfoDO;
import ink.whi.user.repo.mapper.UserInfoMapper;
import ink.whi.user.repo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: qing
 * @Date: 2023/10/24
 */
@Repository
public class UserDao extends ServiceImpl<UserInfoMapper, UserInfoDO> {

    @Autowired
    private UserMapper userMapper;

    public BaseUserDTO getByUserNameAndPwd(String username, String password) {
        LambdaQueryChainWrapper<UserDO> wrapper = ChainWrappers.lambdaQueryChain(userMapper);
        UserDO user = wrapper.eq(UserDO::getUserName, username)
                .eq(UserDO::getPassword, password)
                .eq(UserDO::getDeleted, 0)
                .one();
        return UserConverter.toDTO(user);
    }

    public UserInfoDO getByUserId(Long userId) {
        return lambdaQuery().eq(UserInfoDO::getUserId, userId)
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode())
                .one();
    }

    public UserDO getUser(Long userId) {
        return userMapper.selectById(userId);
    }

    public UserDO getUserByName(String username) {
        LambdaQueryWrapper<UserDO> query = Wrappers.lambdaQuery();
        query.eq(UserDO::getUserName, username)
                .eq(UserDO::getDeleted, YesOrNoEnum.NO.getCode());
        return userMapper.selectOne(query);
    }

    public void saveUser(UserDO user) {
        if (user.getId() == null) {
            userMapper.insert(user);
        } else {
            userMapper.updateById(user);
        }
    }

    public List<Long> scanUserId(Long userId, Integer size) {
        return userMapper.getUserIdsOrderByIdAsc(userId, size == null ? PageParam.DEFAULT_PAGE_SIZE : size);
    }
}
