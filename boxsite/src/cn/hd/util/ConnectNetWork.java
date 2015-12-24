package cn.hd.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
 
public class ConnectNetWork {
 
  /**
   * @param args
   * @throws Exception 
   */
  public static void main(String[] args) throws Exception {
    // TODO Auto-generated method stub
    connAdsl("VPN_Test","test", "test"); 
    Thread.sleep(1000); 
    cutAdsl("VPN_Test"); 
    Thread.sleep(1000); 
  }
 
  /** 
   * 执行CMD命令,并返回String字符串 
   */
  public static String executeCmd(String strCmd) throws Exception {
    System.out.println("cmd /c " + strCmd);
    Process p = Runtime.getRuntime().exec("cmd /c " + strCmd); 
    StringBuilder sbCmd = new StringBuilder();
    //这里很重要，设置GB2312解决乱码！！！
    //如果程序默认编码就是GB2312，可以不写
    //我NetBeans默认用UTF8
    BufferedReader br = new BufferedReader(new InputStreamReader(p 
        .getInputStream(), "GB2312")); 
    String line; 
    while ((line = br.readLine()) != null) { 
      sbCmd.append(line + "\n"); 
    }
    return sbCmd.toString(); 
     
    /*
    //如果整个过程换成这样，就更清楚了。getInputStream是获取最原始的字节流，
    //cmd返回的是以GB2312双字节编码的字节流
    InputStream in = p.getInputStream();
    byte[] b = new byte[2000];
    in.read(b);
    String msg = new String(b, "GB2312");
    //用GB2312解释这堆字节，就可以组装成一个正常的String了
    //如果上边不写GB2312，等于这里用UTF8组装，结果一样
    return msg;
    */
  } 
  
  /** 
   * 连接ADSL 
   * 语法： rasdial 连接名称 username password 
   * 实例： rasdial 我的宽带 hzhz1234567890 dfdfdfdfdf 
   */ 
  public static boolean connAdsl(String adslTitle, String adslName, String adslPass) throws Exception { 
    System.out.println("正在建立连接."); 
    String adslCmd = "rasdial " + adslTitle + " " + adslName + " " + adslPass; 
    String tempCmd = executeCmd(adslCmd); 
    //String tempCmd = executeCmd("ping www.youku.com"); 
     
    // 判断是否连接成功 
    if (tempCmd.indexOf("已连接") > 0) { 
      System.out.println("已成功建立连接."); 
      return true; 
    } else { 
      System.err.println(tempCmd); 
      System.err.println("建立连接失败"); 
      return false; 
    } 
  } 
  
  /** 
   * 断开ADSL 
   */
  public static boolean cutAdsl(String adslTitle) throws Exception { 
    String cutAdsl = "rasdial " + adslTitle + " /disconnect"; 
    String result = executeCmd(cutAdsl); 
      
    if (result.indexOf("没有连接")!=-1){ 
      System.err.println(adslTitle + "连接不存在!"); 
      return false; 
    } else { 
      System.out.println("连接已断开"); 
      return true; 
    } 
  } 
}