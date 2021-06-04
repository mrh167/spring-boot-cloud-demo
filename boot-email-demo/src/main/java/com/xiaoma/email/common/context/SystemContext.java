package com.xiaoma.email.common.context;

import java.io.File;

public class SystemContext {
    public static String real = "." + File.separatorChar+ "dir" ;
    public static String now="/";
    public static String user="";
    public static String userDirectory = "/"+user;
    public static String Charset = "UTF-8";
}