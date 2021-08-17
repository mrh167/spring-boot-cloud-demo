package com.msc.fix.lisa.repository.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msc.fix.lisa.common.R;
import com.msc.fix.lisa.common.TokenGenerator;
import com.msc.fix.lisa.common.security.JwtTokenUtil;
import com.msc.fix.lisa.domain.entity.system.SysUserToken;
import com.msc.fix.lisa.domain.gateway.system.SysUserTokenGateway;
import com.msc.fix.lisa.repository.db.mapper.SysUserTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/18
 * Time: 15:46
 * Description: No Description
 */
@Service
public class SysUserTokenRepository extends ServiceImpl<SysUserTokenMapper, SysUserToken> implements SysUserTokenGateway {

    @Autowired
    private JwtTokenUtil jwt;

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Override
    public R createToken(Long userId) {
        //生成一个token
//        String token = TokenGenerator.generateValue();
        String token = jwt.generateToken(userId);

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        //判断是否生成过token
        SysUserToken tokenEntity = this.getById(userId);
        if(tokenEntity == null){
            tokenEntity = new SysUserToken();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expiration);

            //保存token
            this.save(tokenEntity);
        }else{
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expiration);

            //更新token
            this.updateById(tokenEntity);
        }

        R r = R.ok().put("token", token).put("expire", expiration);

        return r;
    }

    @Override
    public void logouts(Long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        SysUserToken tokenEntity = new SysUserToken();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }

}
