/*
 * 创建日期 2006-9-2
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package es.util;

/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-9-2
 */
public class UnicodeConverter {
	
	 public static final String SBCchange(String QJstr)
	 {
	     String outStr="";
	     String Tstr="";
	     byte[] b=null;

	     for(int i=0;i<QJstr.length();i++)
	     {     
	      try
	      {
	       Tstr=QJstr.substring(i,i+1);
	       b=Tstr.getBytes("unicode");
	      }
	      catch(java.io.UnsupportedEncodingException e)
	      {
	       e.printStackTrace();
	      }     
	   
	      if (b[3]==-1)
	      {
	       b[2]=(byte)(b[2]+32);
	       b[3]=0;      
	        
	       try
	       {       
	        outStr=outStr+new String(b,"unicode");
	       }
	       catch(java.io.UnsupportedEncodingException e)
	       {
	        e.printStackTrace();
	       }      
	      }else outStr=outStr+Tstr;
	     }
	    
	     return outStr; 
	  }

	    public static void main(String[] args) {
	        
	         String QJstr="hello!！ 全角转换，ＤＡＯ";
	         
	         String result=SBCchange(QJstr);
	        
	         System.out.println(QJstr+"\n"+result);
	      
	     }
}
