package com.oss.lisa.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    private OssService ossServiceImpl;

    @GetMapping("/policy")
    public R policy(){
        OssPolicyResult ossPolicyResult = ossServiceImpl.policy();
        return R.ok().put("ossPolicyResult",ossPolicyResult);
    }

    @PostMapping("/callback")
    public R callback(HttpServletRequest request){
        OssCallbackResult ossCallbackResult = ossServiceImpl.callback(request);
        return R.ok().put("ossCallbackResult",ossCallbackResult);
    }
}