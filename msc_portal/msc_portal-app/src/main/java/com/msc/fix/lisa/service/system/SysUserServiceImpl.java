package com.msc.fix.lisa.service.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msc.fix.lisa.api.system.SysRoleService;
import com.msc.fix.lisa.api.system.SysUserRoleService;
import com.msc.fix.lisa.api.system.SysUserService;
import com.msc.fix.lisa.common.RRException;
import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.domain.common.utils.BeanUtils;
import com.msc.fix.lisa.domain.common.utils.Constant;
import com.msc.fix.lisa.domain.common.utils.Query;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import com.msc.fix.lisa.repository.db.SysUser;
import com.msc.fix.lisa.repository.db.mapper.SysUserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/16
 * Time: 16:39
 * Description: No Description
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Long createUserId = (Long)params.get("createUserId");

        IPage<SysUser> page = this.page(
                new Query<SysUser>().getPage(params),
                new QueryWrapper<SysUser>()
                        .like(StringUtils.isNotBlank(username),"username", username)
                        .eq(createUserId != null,"create_user_id", createUserId)
        );

        return new PageUtils(page);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return baseMapper.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public SysUserCo queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    @Transactional
    public void saveUser(SysUserCo user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        SysUser sysUser = BeanUtils.convert(user, SysUser.class);
        baseMapper.insert(sysUser);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(SysUserCo user) {
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        SysUser sysUser = BeanUtils.convert(user, SysUser.class);
        baseMapper.updateById(sysUser);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }



    @Override
    public void deleteBatch(Long[] userId) {
        this.removeByIds(Arrays.asList(userId));
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUser userEntity = new SysUser();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<SysUser>().eq("user_id", userId).eq("password", password));
    }

    @Override
    public SysUserCo getById(Long userId) {
        SysUser sysUser = baseMapper.selectById(userId);
        SysUserCo user = BeanUtils.convert(sysUser, SysUserCo.class);
        return user;
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserCo user){
        if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if(user.getCreateUserId() == Constant.SUPER_ADMIN){
            return ;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if(!roleIdList.containsAll(user.getRoleIdList())){
            throw new RRException("新增用户所选角色，不是本人创建");
        }
    }

}
