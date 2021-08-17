package com.msc.fix.lisa.controller;

import com.msc.fix.lisa.api.OssService;
import com.msc.fix.lisa.base.OssCallbackResult;
import com.msc.fix.lisa.base.OssPolicyResult;
import com.msc.fix.lisa.common.R;
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