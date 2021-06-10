package com.xiaoma.email.emaildemo.common.utils.api.impl;

import com.xiaoma.email.emaildemo.common.context.SystemContext;
import com.xiaoma.email.emaildemo.common.utils.api.IFileManagerService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
@Service
public class DiskFileManagerServiceImpl implements IFileManagerService {
    public DiskFileManagerServiceImpl(){
        File file = new File(SystemContext.real);
        if(!file.exists()){
            file.mkdirs();
        }
    }
    @Override
    public void ls() {
        File file = new File(SystemContext.real + SystemContext.now );
        if(file.exists()){
            System.out.println(Arrays.toString(file.list()));
        }else{
        }
    }

    @Override
    public String cd(String arg) {
        File file = new File(SystemContext.real + SystemContext.now + File.separatorChar  + arg);
        if("..".equals(arg)){
            file.getParent().replace(SystemContext.real,"");
        }else if(file.exists() && file.isDirectory()){
            SystemContext.now = SystemContext.now + arg;
        }else{
            System.out.println("cd: "+arg+": No such file or directory");
        }
        return SystemContext.now;
    }

    @Override
    public Boolean cp(String filename, String newFilename) throws IOException {
        File srcFile = new File(SystemContext.real + SystemContext.now + File.separatorChar  + filename);
        File destFile = new File(SystemContext.real + SystemContext.now + File.separatorChar  + newFilename);
//		如果源文件不存在
        if(!srcFile.exists()){
            throw new RuntimeException("找不到源文件");
        }
//		如果目标文件不存在
        if(!destFile.exists()){
            destFile.mkdirs();
        }
//		如果源文件是文件
        if(srcFile.isFile()){
//			如果目标文件是文件
            if(destFile.isFile()){
                Files.copy(srcFile.toPath(),destFile.toPath());
            }

//			如果目标文件是文件夹
            else if(destFile.isDirectory()){
                File newFile = new File(destFile,srcFile.getName());
                Files.copy(srcFile.toPath(),newFile.toPath());
            }
        }
//		如果源文件是文件夹
        else if(srcFile.isDirectory()){
            if(destFile.isFile()){
                throw new RuntimeException("源文件是文件夹，目标文件是文件，无法进行复制");
            }
            else if(destFile.isDirectory()){
                File fs[] = srcFile.listFiles();
                for(File f:fs){
                    File newFile = new File(destFile,f.getName());
//					如果子级文件是文件夹，则递归
                    if(f.isDirectory()){
                        cp(f.getAbsolutePath(),newFile.getAbsolutePath());
                    }
                    if(f.isFile()){
                        Files.copy(f.toPath(),newFile.toPath());
                    }

                }
            }
        }
        return true;
    }

    @Override
    public Boolean mv(String filename, String newFilename) {
        File file = new File(SystemContext.real + SystemContext.now + File.separatorChar  + filename);
        if(file.exists()){
            file.renameTo(new File(SystemContext.real + SystemContext.now + File.separatorChar  + newFilename));
            file.delete();
        }else{
            return false;
        }
        return true;
    }

    @Override
    public Boolean mk(String filename) throws IOException {
        File file = new File(SystemContext.real + SystemContext.now + File.separatorChar  + filename);
        if(!file.exists()){
            file.createNewFile();
        }
        return true;
    }

    @Override
    public Boolean rm(String filename) {
        File file = new File(SystemContext.real + SystemContext.now + File.separatorChar  + filename);
        if(!file.exists()){
            file.delete();
        }else{
            return false;
        }
        return true;
    }

    @Override
    public Boolean mkdir(String filename) {
        File file = new File(filename);
        if(!file.exists()){
            file.mkdirs();
        }
        return true;
    }

    @Override
    public Boolean rmdir(String filename) {
        File file = new File(SystemContext.real + SystemContext.now + File.separatorChar  + filename);
        if(!file.exists()){
            file.delete();
        }else{
            return false;
        }
        return true;
    }

    @Override
    public String cat(String filename) throws IOException {
        File file = new File(SystemContext.real + SystemContext.now + File.separatorChar  + filename);
        if(file.exists()){
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            System.out.println(String.valueOf(chars));
            reader.close();
            return String.valueOf(chars);
        }

        return null;
    }

    @Override
    public String head(String filename, Integer n) {
        File file = new File(SystemContext.real + SystemContext.now + File.separatorChar  + filename);
        if(file.exists()){
            try {
                FileReader fr = new FileReader(file);
                BufferedReader bf = new BufferedReader(fr);
                String str;
                // 按行读取字符串
                for (int i=0;i<n&&(str = bf.readLine()) != null;i++) {
                    System.out.println(str);
                }
                bf.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public String tail(String filename, Integer n) {
        File file = new File(SystemContext.real + SystemContext.now + File.separatorChar  + filename);
        RandomAccessFile rf = null;
        try {
            rf = new RandomAccessFile(file, "r");
            long len = rf.length();
            long start = rf.getFilePointer();
            long nextend = start + len - 1;
            String line;
            rf.seek(nextend);
            int c = -1;
            while (nextend > start) {
                c = rf.read();
                if (c == '\n' || c == '\r') {
                    line = rf.readLine();
                    if (line != null) {
                        System.out.println(new String(line.getBytes("UTF-8"), "UTF-8"));
                    }else {
                        System.out.println(line);
                    }
                    nextend--;
                }
                nextend--;
                rf.seek(nextend);
                if (nextend == 0) {// 当文件指针退至文件开始处，输出第一行
                    System.out.println(rf.readLine());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rf != null) {
                    rf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String help() {
        return null;
    }
}