package com.xiaoma.okhttp.mvc.controller;

import com.xiaoma.okhttp.utils.OkHttpCli;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerController {
    @Autowired
    private OkHttpCli okHttpCli;


    @RequestMapping(value = "show", method = RequestMethod.GET)
    public String show() {
        String url = "https://www.baidu.com/";
        String message = okHttpCli.doGet(url);
        return message;
    }

}
