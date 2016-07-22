package cn.hd.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class MessageConverter {
	static String path = "C:\\d\\Sites\\ai\\so\\resource\\assets\\zhandoushibai\\";
	static String outpath = "C:\\d\\Sites\\ai\\so\\resource\\assets\\zhandoushibai\\";
	
	public static void outputanimation(RandomAccessFile dataFile, Element e0,
			String childName) throws JDOMException, IOException {
		List<Element> list = e0.getChildren(childName);
		int i0=0;
		dataFile.write(("    \"" + childName + "\": [ \n").getBytes());
		for (Element anim : list) {
			dataFile.write(("    { \n").getBytes());
			dataFile.write(("      ").getBytes());
			List<Attribute> att1 = anim.getAttributes();
			for (Attribute e2 : att1) {
				if (e2.getValue().length() <= 0)
					continue;

				if (e2.getName().endsWith("name")
						|| e2.getName().endsWith("tweenEasing"))
					dataFile.write(("\"" + e2.getName() + "\":\""
							+ e2.getValue() + "\",").getBytes());
				else
					dataFile.write(("\"" + e2.getName() + "\":" + e2.getValue() + ",")
							.getBytes());
			}

			List<Element> slots = anim.getChildren("timeline");
			dataFile.write(("\"timeline\":[\n").getBytes());
			int i=0;
			for (Element e : slots) {
				dataFile.write(("        { \n").getBytes());
				List<Attribute> att2 = e.getAttributes();
				dataFile.write(("         ").getBytes());
				for (Attribute e2 : att2) {
					if (e2.getName().endsWith("name"))
						dataFile.write(("\"" + e2.getName() + "\":\""
								+ e2.getValue() + "\",").getBytes());
					else
						dataFile.write(("\"" + e2.getName() + "\":"
								+ e2.getValue() + ",").getBytes());
				}
				
				List<Element> childs = e.getChildren("frame");
				dataFile.write(("\"frame\": [ \n").getBytes());
				int ii=0;
				for (Element display : childs) {
					dataFile.write(("         { \n").getBytes());
					List<Attribute> att3 = display.getAttributes();
					int rr=0;
					dataFile.write(("            ").getBytes());
					for (Attribute a3 : att3) {
						if (a3.getName().equals("event"))
							dataFile.write(("\"" + a3.getName() + "\":\""+ a3.getValue()+"\"").getBytes());
						else
						dataFile.write(("\"" + a3.getName() + "\":"+ a3.getValue()).getBytes());
						rr++;
						if (rr<att3.size())
							dataFile.write((",").getBytes());
					}
					List<Element> ts = display.getChildren("transform");
					if (ts.size() > 0) {
						Element tt = ts.get(0);
						List<Attribute> att4 = tt.getAttributes();
						dataFile.write((",\"transform\": { \n").getBytes());
						int jj=0;
						for (Attribute a3 : att4) {
							dataFile.write(("              \"" + a3.getName()
									+ "\":" + a3.getValue()).getBytes());
							
							jj++;
							if (jj<att4.size())
							dataFile.write((",").getBytes());
							dataFile.write(("\n").getBytes());
						}
						dataFile.write(("             }\n").getBytes());
					}
					
					dataFile.write(("         }").getBytes());
					ii++;
					if (ii<childs.size())
					dataFile.write((",").getBytes());
					dataFile.write(("\n").getBytes());
				}
				dataFile.write(("       ] \n").getBytes());
				
				dataFile.write(("       }").getBytes());
				i++;
				if (i<slots.size())
				dataFile.write((",").getBytes());
				dataFile.write(("\n").getBytes());				
			}
			dataFile.write(("      ] \n").getBytes());
			
			dataFile.write(("    }").getBytes());
			
			i0++;
			if (i0<list.size())
			dataFile.write((",").getBytes());
			dataFile.write(("\n").getBytes());					
		}
		
		dataFile.write(("    ] \n").getBytes());
	}

	public static void readSkeleton() throws JDOMException, IOException {
		String name = "texture";
		InputStream file = new FileInputStream(name + ".json");

		byte[] buf = new byte[1024];
        StringBuffer sb=new StringBuffer();
        while((file.read(buf))!=-1) {
            sb.append(new String(buf));    
            buf=new byte[1024];//重新生成，避免和上次读取的数据重复
        }
        String str = (sb.toString());
        JSON json = JSONObject.fromObject(str);
        System.out.println(json.toString());
	}

	public void xml2json() {
		File fileDes = new File("texture.xml");
		InputStream str;
		String xmlStr = "<frame z='13' tweenEasing='0' duration='2'></frame>";
		try {
			str = new FileInputStream(fileDes);
			XMLSerializer a = new XMLSerializer();
			JSON jj = a.readFromStream(str);
			System.out.println(jj.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void outputSkeleton() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		String name = "skeleton";
		InputStream file = new FileInputStream(path+name + ".xml");
	
		RandomAccessFile dataFile = new RandomAccessFile(outpath+name + ".json", "rw");
	
		dataFile.setLength(0);
		dataFile.write("{ ".getBytes());
		Document document = builder.build(file);// 获得文档对象
		Element root = document.getRootElement();// 获得根节点
		List<Element> list = root.getChildren();
		System.out.println(root.getName());
		List<Attribute> att = root.getAttributes();
		for (Attribute e : att) {
			System.out.println(e.getName() + "=" + e.getValue());
			if (e.getName().equals("name"))
			dataFile.write(("\"" + e.getName() + "\":\"" + e.getValue() + "\",")
					.getBytes());
			else
				dataFile.write(("\"" + e.getName() + "\":" + e.getValue() + ",").getBytes());
			
		}
		dataFile.write(" \"armature\": [ \n".getBytes());
		dataFile.write("    { \n".getBytes());
		Attribute nameA = root.getChild("armature").getAttribute("name");
		dataFile.write(("    \"name\":\""+nameA.getValue()+"\",\n").getBytes());
		for (Element e : list) {
			
			System.out.println(e.getName());
	
			outputBone(dataFile, e, "bone");
			outputSkin(dataFile, e, "skin");
			outputanimation(dataFile, e, "animation");
		}
		dataFile.write("} ] }".getBytes());
		
		dataFile.close();
	}

	public static void outputSkin(RandomAccessFile dataFile, Element e0,
			String childName) throws JDOMException, IOException {
		List<Element> list = e0.getChildren(childName);
		Element skin = list.get(0);
		dataFile.write(("    \"" + childName + "\": [ \n").getBytes());
		dataFile.write(("     { \n").getBytes());

		List<Attribute> att1 = skin.getAttributes();
		for (Attribute e2 : att1) {
			if (e2.getValue().length() <= 0)
				continue;

			if (e2.getName().endsWith("name"))
				dataFile.write(("\"" + e2.getName() + "\":\"" + e2.getValue() + "\",")
						.getBytes());
			else
				dataFile.write(("\"" + e2.getName() + "\":" + e2.getValue() + ",")
						.getBytes());
		}

		dataFile.write(("      \"slot\": [ \n").getBytes());
		List<Element> slots = skin.getChildren("slot");
		int i=0;
		for (Element e : slots) {
			dataFile.write(("        { \n").getBytes());
			List<Attribute> att2 = e.getAttributes();
			dataFile.write(("         ").getBytes());
			for (Attribute e2 : att2) {
				if (e2.getName().endsWith("z"))
					dataFile.write(("\"" + e2.getName() + "\":" + e2.getValue() + ",")
							.getBytes());
				else
					dataFile.write(("\"" + e2.getName() + "\":\""
							+ e2.getValue() + "\",").getBytes());
			}
			List<Element> childs = e.getChildren("display");
			if (childs.size() > 0) {
				Element display = childs.get(0);
				dataFile.write(("\n         \"" + display.getName() + "\": [ \n")
						.getBytes());
				dataFile.write(("            { \n").getBytes());
				List<Attribute> att3 = display.getAttributes();
				dataFile.write(("            ").getBytes());
				for (Attribute a3 : att3) {
					dataFile.write(("\"" + a3.getName() + "\":\""
							+ a3.getValue() + "\",").getBytes());
				}
				
				List<Element> ts = display.getChildren("transform");
				Element tt = ts.get(0);
				List<Attribute> att4 = tt.getAttributes();
				dataFile.write(("    \"transform\": { \n").getBytes());
				int j=0;
				for (Attribute a3 : att4) {
					dataFile.write(("              \"" + a3.getName() + "\":"
							+ a3.getValue()).getBytes());
					j++;
					if (j<att4.size())
						dataFile.write((",").getBytes());
					dataFile.write(("\n").getBytes());
				}
				dataFile.write(("          } \n").getBytes());
				
				dataFile.write(("          } \n").getBytes());
				dataFile.write(("         ] \n").getBytes());
			}
			
			dataFile.write(("       }").getBytes());
			
			i++;
			if (i<slots.size())
				dataFile.write((",").getBytes());	
			
			dataFile.write(("\n").getBytes());
		}
		dataFile.write(("       ] \n").getBytes());
		
		dataFile.write(("      } \n").getBytes());
		dataFile.write(("    ], \n").getBytes());
	}

	public static void outputBone(RandomAccessFile dataFile, Element e0,
			String childName) throws JDOMException, IOException {
		List<Element> list = e0.getChildren(childName);
		dataFile.write(("    \"" + childName + "\": [ \n").getBytes());
		
		int i=0;
		for (Element e : list) {
			dataFile.write(("        { \n").getBytes());
			List<Attribute> att2 = e.getAttributes();
			for (Attribute e2 : att2) {
				dataFile.write(("         \"" + e2.getName() + "\":\""
						+ e2.getValue() + "\",").getBytes());
			}
			List<Element> childs = e.getChildren();
			for (Element e3 : childs) {
				dataFile.write(("\"" + e3.getName() + "\": { \n").getBytes());
				List<Attribute> att3 = e3.getAttributes();
				int j=0;
				for (Attribute e4 : att3) {
					dataFile.write(("          \"" + e4.getName() + "\":"
							+ e4.getValue()).getBytes());
					j++;
					if (j<att3.size())
						dataFile.write(",".getBytes());					
					dataFile.write("\n".getBytes());					
				}
				dataFile.write(("          }\n").getBytes());
			}
			dataFile.write(("        }").getBytes());
			i++;
			if (i<list.size())
				dataFile.write(",".getBytes());
			dataFile.write("\n".getBytes());								
			
		}
		dataFile.write(("    ], \n").getBytes());
	}

	public static void outputTexture() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		InputStream file = new FileInputStream(path+"texture.xml");

		RandomAccessFile dataFile = new RandomAccessFile(outpath+"texture.json", "rw");

		dataFile.setLength(0);
		dataFile.write("{ ".getBytes());
		Document document = builder.build(file);// 获得文档对象
		Element root = document.getRootElement();// 获得根节点
		List<Element> list = root.getChildren();
		System.out.println(root.getName());
		List<Attribute> att = root.getAttributes();
		for (Attribute e : att) {
			System.out.println(e.getName() + "=" + e.getValue());
			dataFile.write(("\"" + e.getName() + "\":\"" + e.getValue() + "\", ")
					.getBytes());
		}
		dataFile.write(" \"SubTexture\": [ \n".getBytes());
		int i=0;
		for (Element e : list) {
			System.out.println(e.getName());
			dataFile.write("     { \n".getBytes());
			List<Attribute> att2 = e.getAttributes();
			int j=0;
			for (Attribute e2 : att2) {
				System.out.println(e2.getName() + "=" + e2.getValue());
				if (e2.getName().equals("name"))
					dataFile.write(("          \"" + e2.getName()
							+ "\":\"" + e2.getValue() + "\"")
							.getBytes());
				else
					dataFile.write(("          \"" + e2.getName() + "\":"
							+ e2.getValue() + "").getBytes());
				j++;
				if (j<att2.size())
					dataFile.write(",".getBytes());

				dataFile.write("\n".getBytes());
			}
			
			dataFile.write("     }".getBytes());
			i++;
			if (i<list.size())
				dataFile.write(",".getBytes());
			
			dataFile.write("\n".getBytes());
		}
		dataFile.write("] }".getBytes());
		dataFile.close();
	}

	public static void main(String[] args) {
		MessageConverter tt = new MessageConverter();
		// tt.xml2json();
		try {
			tt.outputTexture();
			tt.outputSkeleton();
			//tt.readSkeleton();
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

}
