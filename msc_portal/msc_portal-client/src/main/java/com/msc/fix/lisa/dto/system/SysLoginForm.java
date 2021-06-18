package com.msc.fix.lisa.dto.system;

import lombok.Data;

@Data
public class SysLoginForm {
    private String username;
    private String password;
    private String captcha;
    private String uuid;


}