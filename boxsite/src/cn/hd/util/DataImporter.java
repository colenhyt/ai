package cn.hd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import box.db.Wxpublic;
import box.db.WxpublicService;
import box.db.WxtitleService;
import net.sf.json.JSONArray;

public class DataImporter {
	private WxpublicService wpService = new WxpublicService();
	private WxtitleService wtService = new WxtitleService();
	private String cfg_file;
	
	public DataImporter(String _cfg_file)
	{
		cfg_file = _cfg_file;
	}
	
	public void test()
	{

	}
	private JSONArray getArraydata(String strSheetName)
	{
		JSONArray jsondata = new JSONArray();
		XSSFSheet st = getSheet(strSheetName);
        int rows = 600;//总行数  
        
        int cols;//总列数  
        //schema
        for(int i=0;i<rows;i++){  
            XSSFRow row=st.getRow(i);//读取某一行数据  
            if(row!=null){  
                //获取行中所有列数据  
                cols=row.getLastCellNum();  
                String record = "";
            for(int j=0;j<cols;j++){  
                XSSFCell cell=row.getCell(j);  
                if(cell==null){  
                    System.out.print("   ");    
                }else{  
                //判断单元格的数据类型  
                switch (cell.getCellType()) {    
                    case XSSFCell.CELL_TYPE_STRING: // 字符串    
                        record += cell.getStringCellValue();
                        break;    
                    case XSSFCell.CELL_TYPE_NUMERIC: // 数字,转为float
                    	float value = (float)cell.getNumericCellValue();
                        record += value+",";
                        break;    
                    case XSSFCell.CELL_TYPE_BOOLEAN: // bool 
                        record += cell.getBooleanCellValue()+",";
                        break;    
                    default:    
                        record += cell.getNumericCellValue()+",";
                        break;    
                    }    
            }  
            }  
                jsondata.add(record);
            }  
        }  
        		
		return jsondata;
	}
	
	public void importPublics(String sheetName){
        File fileDes = new File(cfg_file);  
        InputStream str;
		try {
			str = new FileInputStream(fileDes);
	        XSSFWorkbook xwb = new XSSFWorkbook(str);  //利用poi读取excel文件流  
	        List<Wxpublic> wxs = new ArrayList<Wxpublic>();
        	
	        Iterator<XSSFSheet> iterator = xwb.iterator();
	        while (iterator.hasNext())
	        {
	        	
	        	XSSFSheet iii = (XSSFSheet) iterator.next();
	        	JSONArray data2 = getArraydata(iii.getSheetName());
		        for (int i=0;i<data2.size();i+=2)
		        {
		        	System.out.println("type:"+iii.getSheetName()+";name:"+data2.get(i)+";hao:"+data2.get(i+1));
		        	if (data2.get(i).toString().length()>0&&data2.get(i+1).toString().length()>0)
		        	{
			        	Wxpublic wp = new Wxpublic();
			        	wp.setWxname(data2.get(i).toString());
			        	wp.setWxhao(data2.get(i+1).toString());
			        	wp.setType(Integer.valueOf(iii.getSheetName()));
			        	wp.setStatus(0);
			        	wp.setCrdate(new Date());
			        	wxs.add(wp);	        		
		        	}
	        	
		        }
	        	wpService.addWxpublic(wxs);
	        	wxs.clear();
	        }	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  		
	}
	
	
	private XSSFSheet getSheet(String strSheetName)
	{
        File fileDes = new File(cfg_file);  
        InputStream str;
		try {
			str = new FileInputStream(fileDes);
	        XSSFWorkbook xwb = new XSSFWorkbook(str);  //利用poi读取excel文件流  
	        return xwb.getSheet(strSheetName);  //读取sheet的第一个工作表  		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataImporter imp = new DataImporter("热微信号.xlsx");
		imp.test();
		//imp.importPublics("文化");
	}

}
