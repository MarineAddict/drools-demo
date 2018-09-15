package com.oyo.droolsdemo.common.droolutil;

import com.oyo.droolsdemo.entity.drool.DataBaseDroolDrl;
import com.oyo.droolsdemo.entity.request.DroolsData;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: create by xuqie
 * @description: drl文件生成类，包含drl文件的生成,drl string的生成
 * @date:2018/9/10
 * drl文件操作类
 */
public class DrlFileGeneratorUtil {
    public final static String PACKAGESTARTER="drool.";
    public final static String SEPARATOR = System.getProperty("line.separator");
    public final static String BLANK ="\b";



    /**
     * 生成.drl 文件，生成文件的路径为src/main/resources/xx，因此package的位置决定该文件的所在位置;
     * 使用该方法后，需要调用DroolUtil的addIntoKieFileSystem方法才能将填写后的文件加载进入内存;
     * 或者采用reloadAll方法将全部的drl文件加载，虽然该方法已经废弃不建议使用
     * @param
     */
    public static String generateDrlFile(DroolsData droolsData)  {
        // 文件的rule 名称判断
        if(droolsData.getRule()==null||"".equals(droolsData.getRule())){
            return "EMPTY NAME IS INVALID";
        }
        //包名必须drool开头，否则无法生成到对应的位置
        if(!droolsData.getPackage().startsWith(PACKAGESTARTER)){
            return "PACKAGENAME SHOULD START WITH \"drool\"";
        }
        String filePath=getFilePath(droolsData);;

        File file= null;
        try {
            file = ResourceUtils.getFile(filePath);
        if(!file.exists()){
                //创建文件操作
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
        String baseRoot=DrlFileGeneratorUtil.class.getClassLoader().getResource("").getPath();
      return   baseRoot+"/"+droolsData.getPackage()
              .replaceAll("\\.","\\/")+"/"+droolsData.getDroolFileName()+".drl";
    }

    /*write a new drl file under the specific filepath*/
    private static void writeDrlFile(File filePath,DroolsData droolsData) throws IOException {
        FileWriter fw=null;
        try {
        fw=new FileWriter(filePath);
        fw.write("// "+ droolsData.getComments().trim());
        fw.write(SEPARATOR);
        fw.write(droolsData.getPackageTitle().trim());
        fw.write(SEPARATOR);
        fw.write(droolsData.getImportTileInFile().trim());
        fw.write(SEPARATOR);
        fw.write(droolsData.getRuleTitle().trim());
        fw.write(SEPARATOR);
        fw.write(droolsData.getWhenString().trim());
        fw.write(SEPARATOR);
        fw.write(droolsData.getThenString().trim());
        fw.write(SEPARATOR);
        fw.write(droolsData.getEnd().trim());
        fw.write(SEPARATOR);
        fw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
           fw.close();
        }
    }

    /**
     * 将DataBaseDrl对象写成一个String字符串
     * @param droolsData
     * @return
     */
    public static String generateDrlFileString(DataBaseDroolDrl droolsData){
        StringBuilder sb=new StringBuilder();
        sb.append("// ")
                .append(droolsData.getComments().trim())
                .append(SEPARATOR)
                .append("package "+droolsData.getPackageDesc().trim())
                .append(SEPARATOR)
                .append("import "+droolsData.getImportDesc().trim())
                .append(SEPARATOR)
                .append("rule \""+droolsData.getRule().trim()+"\"")
                .append(SEPARATOR)
                .append("when")
                .append(SEPARATOR)
                .append(droolsData.getWhenExp().trim())
                .append(SEPARATOR)
                .append("then")
                .append(droolsData.getThenExp().trim())
                .append(SEPARATOR)
                .append("end")
                .append(SEPARATOR);
        return sb.toString();
    }


}
