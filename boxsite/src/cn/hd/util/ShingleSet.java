package cn.hd.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


 //只能处理a-z+空格
public class ShingleSet  {
	
String filepath;
	
	int k;
	 
	int signatureNumber;
	
	int times;
	
	int [] randomArray;
	
	int [] randomArrayForLSH;
	
	ArrayList <String> array=new ArrayList <String>();//存储所有的Shingles的集合，这些Shingles是无序的
	
	/*数组中的值是哈希桶的编号，即各个Shingles对应的桶号，和Shingles在array中的顺序相同但也是无序的，可以看做矩阵的行号，
	 * 
	 * 但这些行号并没有按从小到大排序
	 * 
	 * 从array变到resultOfHashToShingle的过程采用了相同的哈希函数
	 *
	 * 如果是多篇文档的话，各自的resultOfHashToShingle数组中存储的桶号并不相同，也没有按照桶号的顺序来存储，仅仅存储了文
	 * 
	 * 档的shingles都被哈希到了哪些桶
	 * 
	 */
	long [] resultOfHashToShingle;
	
	long [] signature;//这个数组用于存储文本的签名矩阵
	
	int bandNumber;
	
	int [] bucketNumber;//这个数组用于存储签名被哈希到的桶号
	
	int [] bucketNumberANDOR;//这个数组用于存储签名被哈希到的桶号
	
	BufferedReader  inputStream;

	public ShingleSet (String filepath,int k,int signatureNumber,int bandNumber,int times, int[] randomArray,int [] randomArrayForLSH){
	
		this.filepath=filepath;	
		
		this.k=k;	
		
		this.signatureNumber=signatureNumber;
		
		this.bandNumber=bandNumber;
		
		this.times=times;
		
		this.randomArray=randomArray;
		
		this.randomArrayForLSH=randomArrayForLSH;

	}
	
	public void createShingleSet() {
		
		try{
			
			inputStream =new BufferedReader(new FileReader(filepath));
		
			String line=inputStream.readLine();
			
			Pattern p = Pattern.compile("\\s+|\t|\r|\n");//去掉读入行的空格，制表，换行，回车
			
			while(line!=null){
				   
				Matcher m = p.matcher(line);
				   
				line = m.replaceAll(" ");
				
				if(!(line.length()<k)){//这块的处理有点粗糙，行过短的被忽略，并且是先读入行再进行去除制表符、回车等字符
				
					int start =0;
						
					String tmp=null;
				
					do{
					
						tmp=line.substring(start,start+(k-1));
				
						start++;
						
						if(!array.contains(tmp)){
							
							array.add(tmp);//如果文档长度不同的话，自己所包含的shingle种类大小可能也不同
						
							}
						
						}while(!(start>line.length()-(k-1)));
				
				}
				
				 line=inputStream.readLine();
				
			}
		
		}
		catch(FileNotFoundException e){
			
			System.out.println("文档打开出错");}
		
		catch(IOException e){
			
			System.out.println("文档读取出错");
			
		}
		
	}

	public void hashToShingle(){
		
		resultOfHashToShingle=new long[array.size()];
		
		for(int i=0;i<array.size();i++){
			
			String tmp=array.get(i);
			
			long sum=0;//设为long
			
			for(int t=0;t<k-4;t++){
				
				char[] chartmp=tmp.substring(t,t+4).toCharArray();//将九位字符串中的连续四位以字符数组的形式存储
				
				//将字符串转化为32位整数。这里的强制类型转换将char转为int，再将double转long时，由于double此时为整数且不大于long最大值，所以转换无损
				long  inttmp=(long)((int)chartmp[0]*Math.pow(128,3)+(int)chartmp[1]*Math.pow(128,2)+(int)chartmp[2]*Math.pow(128,1)+(int)chartmp[3]*Math.pow(128,0));
				
				sum+=inttmp;
				
				}
			
			long hashResult=(sum%(long)Math.pow(2,32));//java中模运算的操作数范围大；将字符串哈希到2^32个桶中,而int占-2^31到+2^31。但桶数目小于27^9
			
			//hashResult的结果是0-2^32-1
			
			resultOfHashToShingle[i]=hashResult;
		
		}
		
	}
	
	/*对所有的桶重新进行大量哈希，每个哈希取最小的桶号
	 * 
	 * 强制没和哈希函数的结果共有27^9个桶（每个哈希函数的桶数目可以不一样吗？）因为27^9中字符串
	* */
	public void produceSignature(){
		
		signature=new long[signatureNumber];
		
		for(int i=0;i<signatureNumber;i++){
			
			long min=(long)Math.pow(27, k);
			
			//一个哈希函数将resultOfHashToShingle中的桶号在重新排序到27^k个桶中，找出最小的桶号即为签名存储进signature即可
			
			for(int t=0;t<resultOfHashToShingle.length;t++){
		
				long tmp=(resultOfHashToShingle[t]*randomArray[2*i]+randomArray[2*i+1])%(long)Math.pow(27, k);//结果是0-27^k
				
				if(tmp<min) min=tmp;
			}
	
			signature[i]=min;
		
		}
		
	}	
	
	public void localitySensitiveHahing(){
		
		int rows=signatureNumber/bandNumber;
		
		//因为有bandNumber个行条，所以使得哈希函数也有bandNumber*time个桶。同一个行条必须使用同一个哈希函数。
		
		//这里不同行条使用了不同的hash函数
		
		//所以，第i个行条的哈希值=[（行条内签名之和）*randomArray[row*i]+randomArray(row*i+1)]%(bandNumber*time)
		
		//对一个文档的签名向量的每个行条使用一个哈希函数，并存入了数组bucketNumber，对每篇文档的签明进行了bandNumber次hash
		
		bucketNumber=new int[bandNumber];
		
		for(int i=0;i<bandNumber;i++){
			
			int begin=i*rows;
			
			int end=(i+1)*rows;
			
			long sum=0;
			
			for(int t=begin;t<end;t++)  sum+=signature[t];
				
			//将本文档的第i行条的哈希值(即被哈希到的桶号)放入bucketNumber[i],如果两个文档的bucketNumber[i]相等，这说明这两个文档的第i个行条完全一样
			
			//每个行条一组桶。
			
			bucketNumber[i]=(int)((sum*randomArray[rows*i]+randomArray[rows*i+1])%(bandNumber*times));
		}
		
		//与构造+或构造，选用的hash函数并不一定要是在局部敏感哈希中使用过的哈希函数。所以在再这里再构造4*4*bucketNumber个哈希函数对文档进行重新处理
		
		//也就是对每个行条是用来16个hash函数
		
		//每个行条使用不同的hash函数，并将结果存入数组，每篇文档进行了4*4*bandNumber次哈希。
		
		bucketNumberANDOR=new int[4*4*bandNumber];
		
		for(int i=0;i<bandNumber;i++){
			
			int begin=i*rows;
			
			int end=(i+1)*rows;
			
			long sum=0;
			
			for(int t=begin;t<end;t++)  sum+=signature[t];
			
			for(int k=0;k<(4*4);k+=2) bucketNumberANDOR[(4*4)*i+k]=(int)((sum*randomArrayForLSH[(4*4)*i+k]+randomArrayForLSH[(4*4)*i+k+1])%(bandNumber*times));
			
		}
		
	}
	
	
	public void run() {
		
		this.createShingleSet();
		
		this.hashToShingle();
		
		this.produceSignature();
		
		this.localitySensitiveHahing();
		
	}
	
	
	public static void main(String[] args){
		
		int bandNumber=1;
		
		int times=100;
		
		int signatureNumber=100;
		
		double  Jaccard;
		
		int [] randomArray;
		
		Scanner keyboard=new Scanner(System.in);
		
		//产生最小哈希签名的哈希函数数目强制设初始化为100个(100对随机数)，即每个文本有100个签名，下边进行重新赋值。
		
		System.out.println("请问您希望将使用多少个Hash函数用于为文档产生签名？");
		
		signatureNumber=keyboard.nextInt();
		
		randomArray=new int [signatureNumber*2];
		
		Random random = new Random();
		
		for(int i=0;i<signatureNumber;i++){
		
		int tmp=(int)Math.pow(signatureNumber,0.5);
			
		randomArray[2*i]=(Math.abs(random.nextInt())%tmp)+1;//随机数在0-(tmp-1),改为1-tmp
		 
		randomArray[2*i+1]=(Math.abs(random.nextInt())%tmp)+1;
	
		}
		
		//根据签名向量的长度以及预期的相似度来确定行条的数目，对double进行了运算，可能产生误差
		
		System.out.println("请问您希望将相似度为多少的文档在LSH过程中尽可能成为后选对？");
		
		Jaccard=keyboard.nextDouble();
		
		System.out.println("请问您希望在LSH过程中哈希桶的数目是行条数的几倍？");
		
		times=keyboard.nextInt();
		
		keyboard.close();

		double difference=Math.abs(Math.pow(1.0/1.0,1.0/100.0)-Jaccard);	
		
		for(int i=2;i<=signatureNumber;i++){
			
				if(signatureNumber%i==0){
			
					double tmp=Math.abs(Math.pow((double)1/(double)i,(double)i/(double)signatureNumber)-Jaccard);
					
					System.out.printf("行条=%4d时  ",i);
					
					System.out.printf("差值为%8f",tmp);
					
					if(tmp<difference) {difference=tmp;bandNumber=i;System.out.println("   行条被改变");
					
					}else{
						
						System.out.println("   行条未改变");
						
					}
				
				}
		
		}
		
		System.out.println("签名矩阵被分为了"+bandNumber+"个行条");
		
		int [] randomArrayForLSH=new int [4*4*bandNumber];
		
		for(int i=0;i<(4*4*bandNumber);i++){
			
			//将所需hash数目开方得出的数字作为mod后的值，因这样使得mod后的值尽量小，同时从概率角度认为恰好可以产生足够个不同的hash函数
			
			//运行测试程序过程中出现过F2产生的相同行条相等情况多于F产生的。可能的原因是出现了系数使得出现hash冲突，而这系数被使用了四次。但是
			
			//增加hash系数的范围似乎并不能避免这种系数相同的情况
			
			//在签名足够多时也可能无法区分，原因如上。不过会不会系数并没连续使用四次，而仅仅是因为四对系数均哈希冲突
			
			//在签名数目过少时也会出现F2无法鉴别不同行的情况，原因可能是签名少，所以行条少，所以系数的范围小，所以系数被重复使用。所以“与”无效
			
			//当行条少时，桶的数目也会变少
		
			int tmp=(int)Math.pow(4*4*bandNumber,0.5 );
			
			randomArrayForLSH[i]=(Math.abs(random.nextInt())%tmp)+1;//随机数在0-(tmp-1),改为1-tmp
		
			}
		
		ShingleSet  test1=new ShingleSet ("C:\\Users\\fujiaxiaoshao\\Desktop\\test1.txt",5,signatureNumber,bandNumber,times,randomArray,randomArrayForLSH);
		
		ShingleSet  test2=new ShingleSet ("C:\\Users\\fujiaxiaoshao\\Desktop\\test2.txt",5,signatureNumber,bandNumber,times,randomArray,randomArrayForLSH);
		
		test1.run();
		
		test2.run();
		
		System.out.println("文档的签名为:");
		
		for(int i=0;i<signatureNumber;i++){
			
			System.out.printf("%5d",(i+1));
			
			System.out.printf("%20d",test1.signature[i]);
			
			System.out.printf("%20d",test2.signature[i]);
			
			System.out.println("");
			
			}
		
		for(int i=0;i<signatureNumber;i++){
			
			if(test1.signature[i]==test2.signature[i])
				
					System.out.printf("第%3d个签名相等，签名为：%-13d位于第%d个行条\n",(i+1),test1.signature[i],((i/(signatureNumber/bandNumber))+1));
			
			}
		
		
		System.out.println("\n\n使用"+bandNumber+"个哈希函数，每个哈希函数的桶数目是行条数的"+times+"倍，每个哈希函数hash一个行条：");
		
		int  countF=0;
				
		for(int i=0;i<bandNumber;i++){
			
				if(test1.bucketNumber[i]==test2.bucketNumber[i]){countF++;
					
					System.out.printf("\n在第"+(i+1)+"个行条中，两个文档都被哈希到了第"+test1.bucketNumber[i]+"个桶中\n");
					
					System.out.printf("在第"+(i+1)+"个行条中的所有签名为:\n");
					
					long sumOfTest1=0;
					
					long sumOfTest2=0;
					
					for(int t=(signatureNumber/bandNumber)*i;t<(signatureNumber/bandNumber)*(i+1);t++){
						
						System.out.printf("%5d",t+1);
						
						sumOfTest1+=test1.signature[t];
						
						System.out.printf("%20d",test1.signature[t]);
						
						sumOfTest2+=test2.signature[t];
						
						System.out.printf("%20d\n",test2.signature[t]);
						
					}
					System.out.printf("和：");
					
					System.out.printf("%d",sumOfTest1);
					
					System.out.printf("   %d\n",sumOfTest2);
					
					System.out.printf("模：");
					
					System.out.printf("%d  %d\n",(sumOfTest1*randomArray[(signatureNumber/bandNumber)*i]+randomArray[(signatureNumber/bandNumber)*i+1])%(times*bandNumber),(sumOfTest2*randomArray[(signatureNumber/bandNumber)*i]+randomArray[(signatureNumber/bandNumber)*i+1])%(times*bandNumber));
				
				}
		
		}
		
		System.out.println("\n使用"+(4*4*bandNumber)+"个哈希函数，每个哈希函数的桶数目是行条数的"+times+"倍，"+(4*4)+"个哈希函数hash一个行条：");
		
		int countF2=0;
		for(int k=0;k<bandNumber;k++){
			
			int count=0;
		
			for(int i=0;i<(4*4);i+=4){
				
				if(test1.bucketNumberANDOR[(4*4)*k+i]==test2.bucketNumberANDOR[(4*4)*k+i]&&test1.bucketNumberANDOR[(4*4)*k+i+1]==test2.bucketNumberANDOR[(4*4)*k+i+1]&&test1.bucketNumberANDOR[(4*4)*k+i+2]==test2.bucketNumberANDOR[(4*4)*k+i+2]&&test1.bucketNumberANDOR[(4*4)*k+i+3]==test2.bucketNumberANDOR[(4*4)*k+i+3])
				
					count++;

				}
			
			if(count!=0) {countF2++;System.out.printf("在第%5d个行条中，两个文档的签名很大概率保证完全相同\n",(k+1));}
		
			}
		System.out.printf("\n使用F时，得到相同的行条为："+countF+"个\n使用F2时，得到相同的行条为："+countF2+"个");
		
	}

}