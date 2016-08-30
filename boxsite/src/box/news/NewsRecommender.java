package box.news;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NewsRecommender {

	public List<Integer> recNews(long userid, Set<Integer> curritemids) {
		List<Integer> itemids = new ArrayList<Integer>();
		return itemids;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileWriter fs = null;
		BufferedWriter fw = null;
		InputStreamReader fr;
		try {
			File ff = new File("C:\\boxlib\\news_tensite_xml.dat");
//			fr = new FileReader(ff);
			fr = new InputStreamReader(new FileInputStream("C:\\boxlib\\news_sohusite_xml.dat"),"GBK");
			BufferedReader br = new BufferedReader(fr); // 读取获取整行数据
			int i = 1;
			int CountFile = 30;
			LinkedList WriterLists = new LinkedList(); // 初始化文件流对象集合
			LinkedList fwLists = new LinkedList();
			Map<Integer,BufferedWriter> fws = new HashMap<Integer,BufferedWriter>();
			for (int j = 1; j <= CountFile; j++) {

				// 声明对象
				fs = new FileWriter("C:\\boxlib\\news_sohusite\\news" + j + ".txt", false);
				fw = new BufferedWriter(fs);

				// 将对象装入集合
				WriterLists.add(fs);
				fwLists.add(fw);
				fws.put(j, fw);
			}
			BufferedWriter ffw = fws.get(i);
			int kk = 0;
			// 判断是文件流中是否还有数据返回
			while (br.ready()) {

				ffw.write( br.readLine() + "\r\n");
				kk++;
				if (kk>=300000){
					i++;
					ffw = fws.get(i);
					kk = 0;
				}
			}
			br.close();
			fr.close();
			for (Iterator iterator = fwLists.iterator(); iterator.hasNext();) {
				BufferedWriter object = (BufferedWriter) iterator.next();
				object.close();
			}
			// 遍历关闭所有子文件流
			for (Iterator iterator = WriterLists.iterator(); iterator.hasNext();) {
				FileWriter object = (FileWriter) iterator.next();
				object.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
