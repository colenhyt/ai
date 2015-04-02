package cl.xjt.nlp.word;

/**
 * <p>Title: Java���ķִ����</p>
 * <p>Description: ��������п�ԺICTCLASϵͳΪ�������֮�ϸı࣬
 * �������ѧϰ���о���;���κ���ҵ��;�����ге����ɺ���������д���޹ء�</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ����ʦ����ѧ</p>
 * @author ����
 * @version 1.0
 */

public class ICTCLAS {
  private static ICTCLAS instance=null;
  public ICTCLAS() {
    init(0,2);
  }

  public synchronized static ICTCLAS getInstance(){
    if (instance==null){
      instance = new ICTCLAS();
    }
    return instance;
  }

  public synchronized native boolean init(int i, int j);
  public synchronized native String paragraphProcess(String sParagraph) throws Exception;
  public synchronized native boolean fileProcess(String source,String target);
  public static void main(String[] args) {
    ICTCLAS split1 = new ICTCLAS();
    for (int i=0;i<=100;i++)
		try {
			System.out.println(split1.paragraphProcess("��������9��ǹ�ҵ�������һ�����塣/"));
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
      System.out.println(split1.fileProcess("c:\\1.txt","c:\\2.txt"));
  }

  static{
    System.loadLibrary("ICTCLAS");
  }
}