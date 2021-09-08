package com.msc.fix.lisa.executor.query.system;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.common.BusinessException;
import com.msc.fix.lisa.common.security.JwtTokenUtil;
import com.msc.fix.lisa.dto.system.SysUserCmd;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/27
 * Time: 15:59
 * Description: No Description
 */
@Command
public class SysUserCmdExe implements CommandExecutorI<SingleResponse<SysUserCo>, SysUserCmd> {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public SingleResponse<SysUserCo> execute(SysUserCmd cmd) {
        String username = cmd.getUsername();
        String password = cmd.getPassword();
//        String code = cmd.getCode();
//        String captcha = cmd.getCaptcha();
//        if (StringUtils.isEmpty(code) || !captcha.equals(code)) {
//            throw new BusinessException("验证码错误，请重新输入");
//        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BusinessException("用户名或密码错误！！！");
        }
        if (!userDetails.isEnabled()){
            throw new BusinessException("账号被禁用，请联系管理员！！！");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String token = jwtTokenUtil.generateToken(userDetails);
        SysUserCo tokenMap = new SysUserCo();
        Map<String, String> map = tokenMap.getTokenMap();
        map.put("token",token);
        map.put("tokenHead",tokenHead);
        return SingleResponse.of(tokenMap);
    }
}
