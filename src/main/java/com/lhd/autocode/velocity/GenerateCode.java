package com.lhd.autocode.velocity;

import com.lhd.autocode.bean.Config;
import com.lhd.autocode.bean.Table;
import com.lhd.autocode.constant.Constant;
import com.lhd.autocode.enums.EmConstant;
import com.lhd.autocode.service.TableService;
import com.lhd.autocode.multithreads.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by lvhaodong on 2016/7/19.
 */
@Service
public class GenerateCode {

    private Logger logger = Logger.getLogger(GenerateCode.class);

    @Autowired
    private Config config;

    @Autowired
    private TableService tableService;

    @Autowired
    private VelocityBuilder velocityBuilder;

    public void generateProject(){
        long stratTime = System.currentTimeMillis();
        File templateDir = new File(config.getTemplateDirectory());
        File targetDir = new File(config.getDestinationDirectory());
        if (!targetDir.exists()){
            targetDir.mkdirs();
        }
        List<Table> tableList = tableService.getTableList();
        List<Future<Message>> futureList = new ArrayList<Future<Message>>();
        generateDirectory(templateDir, targetDir, tableList, futureList);
        for (Future<Message> future : futureList){
            try {
                Message message = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        ThreadPoolUtils.shutdown();
        long endTime = System.currentTimeMillis();
        logger.info("生成代码全部完成！\n耗时：" + (endTime - stratTime) + "ms.");
    }

    public void generateDirectory(File templateDir, File targetDir, List<Table> tableList
            , List<Future<Message>> futureList){
        File[] subFiles = templateDir.listFiles();
        for (File file : subFiles){
            if (file.isDirectory()){
                String dupTemDirName = file.getName();
                dupTemDirName = renameDir(dupTemDirName);
                File dupTemplateDir = new File(targetDir, dupTemDirName);
                if (!dupTemplateDir.exists()){
                    dupTemplateDir.mkdirs();
                }
                // DFS
                generateDirectory(file, dupTemplateDir, tableList, futureList);
            } else if (file.isFile() && !file.isHidden()){
                int fileType = getFileType(file);
                if (fileType == 1){
                    for (Table table : tableList){
                        // 多线程：TableNameXxx.xxx.vm
                        String dupFileName = rename(file.getName(), table.getTableNameUpperCase());
                        File dupFile = new File(targetDir, dupFileName);
                        Future<Message> future = ThreadPoolUtils.submit(new CallableVmTask(this, file, dupFile, table));
                        futureList.add(future);
                    }
                } else if (fileType == 2){
                    //多线程：Xxx.xxx.vm
                    String dupFileName = rename(file.getName(), "");
                    File dupFile = new File(targetDir, dupFileName);
                    Future<Message> future = ThreadPoolUtils.submit(new CallableVmTask(this, file, dupFile));
                    futureList.add(future);
                } else {
                    // 多线程：XXX.xxx
                    String dupFileName = rename(file.getName(), "");
                    File dupFile = new File(targetDir, dupFileName);
                    Future<Message> future = ThreadPoolUtils.submit(new CallableCopyTask(file, dupFile));
                    futureList.add(future);
                    //logger.warn("unrecognized file template: " + file.getName() + ", will not be generated!");
                }
            }
        }
    }

    public boolean generateFile(File templateFile, File targetFile, Table table){
        String userTypedCode = "";
        if (targetFile.exists()){
            if (!isOverWrite(targetFile.getName()))
                return true;
            userTypedCode = getUserTypedCode(targetFile);
            targetFile.delete();
        }
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Template template = velocityBuilder.getTemplate(templateFile.getAbsolutePath());
        VelocityContext velocityContext = velocityBuilder.buildVelocityContext(table);
        velocityContext.put(EmConstant.userTypedCode.name(), userTypedCode);
        template.merge(velocityContext, printWriter);
        printWriter.flush();
        printWriter.close();
        return true;
    }

    private String getUserTypedCode(File file){
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            boolean isUserTypedCode = false;
            boolean isFirst = true;
            while ((line = br.readLine())!=null){
                if (line.contains(Constant.CUSTOMIZED_CODE_AREA_START)){
                    isUserTypedCode = true;
                    continue;
                } else if (line.contains(Constant.CUSTOMIZED_CODE_AREA_END)){
                    isUserTypedCode = false;
                    continue;
                }
                if (isUserTypedCode){
                    if (isFirst)
                        isFirst = false;
                    else
                        buffer.append(Constant.LINE_BREAKER);
                    buffer.append(line);
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("读取自定义代码区域失败，文件未找到！文件名：" + file.getName());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("读取自定义代码区域失败！文件名：" + file.getName());
            e.printStackTrace();
        }
        return buffer.toString();
    }

    private boolean isOverWrite(String fileName){
        return true;
    }

    private int getFileType(File file){
        if (file.getName().endsWith(Constant._VM)){
            if (file.getName().toLowerCase().contains(EmConstant.tableName.name().toLowerCase()))
                return 1;
            return 2;
        }
        return 0;
    }

    private String renameConfig(String originName){
        String newName = StringUtils.replaceEach(originName,
                new String[]{
                    EmConstant.projectName.name(),
                    EmConstant.groupId.name(),
                    EmConstant.artifactId.name()},
                new String[]{
                    config.getProjectName(),
                    config.getGroupId(),
                    config.getArtifactId(),
                });
        return newName;
    }

    private String renameDir(String originName){
        String newName = renameConfig(originName);
        return StringUtils.replace(newName, ".", "/");
    }

    private String rename(String originName, String replace){
        String newName = renameConfig(originName);
        if (newName.endsWith(Constant._VM))
            newName = newName.substring(0, newName.length() - Constant._VM.length());
        if (newName.startsWith(EmConstant.TableName.name()))
            newName = StringUtils.replace(newName, EmConstant.TableName.name(), replace);
        return newName;
    }
}