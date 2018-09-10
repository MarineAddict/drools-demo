package com.oyo.droolsdemo.entity.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/4
 */
@Data
public class DroolsData implements Serializable {

    private String packageTitle;

    private String importTile;

    private String rule;

    private String when;

    private String then;

    private String comments;

    private String end;

    public String getPackage(){
        return packageTitle;
    }

    public String getPackageTitle() {
        return "package "+packageTitle;
    }

    public void setPackageTitle(String packageTitle) {
        this.packageTitle = packageTitle;
    }

    public DroolsData() {
        this.end = "end";
    }

    public String getImportTile() {
        return "import "+importTile;
    }

    public void setImportTile(String importTile) {
        this.importTile = importTile;
    }

    public String getRule() {
        return "rule \""+rule+"\"";
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getThen() {
        return then;
    }

    public void setThen(String then) {
        this.then = then;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDroolFileName(){
        return this.rule;
    }

    public String getThenString(){
        if(then.contains(System.getProperty("line.separator"))){
           String[] strs= then.split(System.getProperty("line.separator"));
           StringBuilder sb=new StringBuilder("then"+System.getProperty("line.separator"));
           for(String str:strs){
               sb.append("\t").append(str).append(System.getProperty("line.separator"));
           }
            return sb.substring(0,sb.lastIndexOf(System.getProperty("line.separator")));
        }
        return "then "+System.getProperty("line.separator")+"\t"+then;
    }

    public String getWhenString(){
        if(when.contains(System.getProperty("line.separator"))){
            String[] strs= when.split(System.getProperty("line.separator"));
            StringBuilder sb=new StringBuilder("when"+System.getProperty("line.separator"));
            for(String str:strs){
                sb.append("\t").append(str).append(System.getProperty("line.separator"));
            }
            return sb.substring(0,sb.lastIndexOf(System.getProperty("line.separator")));


        }
        return "when "+System.getProperty("line.separator")+"\t"+when;
    }
}
