package com.xiaoma.email.common.utils.api;

import com.xiaoma.email.common.context.SystemContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
@Component
public interface IFileManagerService {
    void ls();
    String cd(String arg);
    Boolean cp(String filename,String newFilename) throws IOException;
    Boolean mv(String filename,String newFilename);
    Boolean mk(String filename) throws IOException;
    Boolean rm(String filename);
    Boolean mkdir(String filename);
    Boolean rmdir(String filename);
    String cat(String filename) throws IOException;
    String head(String filename,Integer n);
    String tail(String filename,Integer n);
    String help();
    default String getNowPath(){
        String[] strings = SystemContext.now.split(File.pathSeparator);
        if(SystemContext.now.startsWith(File.separatorChar+ SystemContext.user)){
            return SystemContext.now.replace(File.separatorChar+ SystemContext.user,"~");
        }
        return SystemContext.now;
    }
    default String getRealPath(){
        return SystemContext.real + SystemContext.now;
    }

    default void createUserDirectory(){
        File file = new File(SystemContext.real + File.separatorChar + SystemContext.user);
        if(!file.exists()){
            file.mkdirs();
        }
    };

    default boolean checkUserDirectory(){
        File file = new File(SystemContext.real + File.separatorChar + SystemContext.user);
        if(!file.exists()){
            createUserDirectory();
            return true;
        }
        return true;
    }

    default void init(){
        if(checkUserDirectory()){
            SystemContext.now = File.separatorChar+ SystemContext.user;
        }
    }
}