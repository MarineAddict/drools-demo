package com.oyo.droolsdemo.common.droolutil;

import com.oyo.droolsdemo.entity.request.DroolsData;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/10
 */
public class DrlFileUtil {
    public final static String PACKAGESTARTER="drool";
    public final static String SEPARATOR = System.getProperty("line.separator");
    public final static String BLANK ="\b";

    private static String baseRoot;

    static {
        baseRoot=  DrlFileUtil.class.getClassLoader().getResource("").getPath();
    }


    /**
     * 生成drl文件（如果文件存在，直接在文件下 继续书写）
     * @param
     */
    public static String generateDrlFile(DroolsData droolsData)  {

        if(droolsData.getRule()==null||"".equals(droolsData.getRule())){
            return "EMPTY NAME IS INVALID";
        }
        if(!droolsData.getPackage().startsWith(PACKAGESTARTER)){
            return "PACKAGENAME SHOULD START WITH \"drool\"";
        }
        String filePath=getFilePath(droolsData);;

        File file= null;
        try {
            file = ResourceUtils.getFile(filePath);
        if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            writeDrlFile(file,droolsData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFilePath(DroolsData droolsData) {
      return   baseRoot+"/"+droolsData.getPackage()
              .replaceAll("\\.","\\/")+"/"+droolsData.getDroolFileName()+".drl";
    }

    /*write a new drl file under the specific filepath*/
    private static void writeDrlFile(File filePath,DroolsData droolsData) throws IOException {
        FileWriter fw=null;
        try {
        fw=new FileWriter(filePath);
        fw.write("// "+droolsData.getComments());
        fw.write(SEPARATOR);
        fw.write(droolsData.getPackageTitle());
        fw.write(SEPARATOR);
        fw.write(droolsData.getImportTile());
        fw.write(SEPARATOR);
        fw.write(droolsData.getRule());
        fw.write(SEPARATOR);
        fw.write(droolsData.getWhenString());
        fw.write(SEPARATOR);
        fw.write(droolsData.getThenString());
        fw.write(SEPARATOR);
        fw.write(droolsData.getEnd());
        fw.write(SEPARATOR);
        fw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
           fw.close();
        }
    }
}
