package cl.xjt.nlp.word;

/**
 * <p>Title: Java���ķִ����</p>
 * <p>Description: ��������п�ԺICTCLASϵͳΪ�������֮�ϸı࣬�������ѧϰ���о���;���κ���ҵ��;�����ге����ɺ���������д���޹ء�</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ����ʦ����ѧ</p>
 * @author ����
 * @version 1.0
 */

public class ThreadTest extends Thread {
  private long sleepTime=1000;
  private String word = null;
  public ThreadTest() {

  }

  public void init(long sleepTime,String word){
    this.word = word;
    this.sleepTime = sleepTime;
  }

  public void run(){
    ICTCLAS split1 = ICTCLAS.getInstance();
    try{
      while (true) {
        try {
			System.out.println(split1.paragraphProcess(word));
		} catch (Exception e1) {
			// TODO �Զ���� catch ��
			e1.printStackTrace();
		}
        //System.out.println(split1.fileProcess("c:\\1.txt","c:\\2.txt"));
        sleep(sleepTime);
      }
    }catch(InterruptedException e){
      System.out.print("�˳��߳�"+sleepTime);
    }
  }

  public static void main(String[] args) {
    ThreadTest test1 = new ThreadTest();
    test1.init(10,"��������9��ǹ�ҵ�������һ�����塣");
    ThreadTest test2 = new ThreadTest();
    test2.init(15,"�����ɡ�ר�����+��ͨ��ʡ�(��ר��+ͨ��)��ʽ���ɵĵ���ȫ��ͳһ����,���з�����ϣ����г��������⡣");
    ThreadTest test3 = new ThreadTest();
    test3.init(13,"��˶��ǵ���:����ˣ���ʼ�ʺͿ�����Ǭ¡�ʵ��Ǳ���ʦ����ѧ��ѧ�����Ƕ�ʹʹ����ѧϰ�š�");
    test1.start();
    test2.start();
    test3.start();
  }
}