package easyshop.lucene.analysis;

import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class SplitWords {

	public static Set getAllWords(String oriString){
		Set words=new TreeSet();
		if (oriString.trim().length() == 0)
			return words;
		
		Stack keys=findIndexes(oriString);//取得所有索引,并使用堆栈存储以保证词在字符串中的先后顺序

		for (int i = 0; i < keys.size(); i++) {
			for (int j = i+1; j <= keys.size(); j++) {
				int sIndex=new Integer(((String)keys.get(i))).intValue();
				String subStr=null;
				if (j==keys.size())	subStr = oriString.substring(sIndex);//最后一个词
				else {
					int eIndex=new Integer(((String)keys.get(j))).intValue();
					subStr = oriString.substring(sIndex, eIndex);					
				}
				//只收藏多于一个词的字符:
				if (subStr!=null&&subStr.length()>1) words.add(subStr);
			}
		}		
		return words;
	}
	/*
	 * 一个词定义如下: 一个汉字, 整个英文单词, 数字(包括浮点数字), 以及任何的一个其他字符 穷尽规则: 以一个词为最小单位, 最长为整个字符串,
	 * 进行1-length之间的任意的,连续性的配对
	 */
	protected static Stack findIndexes(String oriStr) {
		Stack keys=new Stack();
		if (oriStr.trim().length() == 0)
			return keys;
		int length = oriStr.trim().length();

		//首先, 获取字符串中的全部词,并以该词的位置作为key:
		int i=0;
		for (; i < length; i++) {

			//获取字符串中的英文单词:
			char lartinC = oriStr.charAt(i);
			int k = i;//下一个字符
			if (Character.isUpperCase(lartinC)|| Character.isLowerCase(lartinC)) {
				k = i + 1;//从下一个字符开始循环
				for (; k < length; k++) {
					if (k == length)break;
					boolean b = Character.isUpperCase(oriStr.charAt(k))|| Character.isLowerCase(oriStr.charAt(k));

					if (!b)	break;
				}
				keys.push(String.valueOf(i));
//				wordsMap.put(String.valueOf(i), oriStr.substring(i, k));
			}

			if (k == length)break;//已到达末尾;

			//获取字符串中的数字(包括浮点数字)
			char digitC = oriStr.charAt(k);
			int j = k;
			if (Character.isDigit(digitC)) {
				j = k + 1;
				for (; j < length; j++) {
					if (j == length)break;
					if (oriStr.charAt(j) == '.')continue; //碰到小数点, 继续循环
					boolean b = Character.isDigit(oriStr.charAt(j));
					if (!b)	break;
				}
				keys.push(String.valueOf(k));
//				wordsMap.put(String.valueOf(k), oriStr.substring(k, j));
			}

			//既非单词,也非数字:
			if (i==j&&j == k){
				keys.push(String.valueOf(j));
//				wordsMap.put(String.valueOf(j), String.valueOf(digitC));
			}
			
			if (j>i)i=j-1;//转移到一个词后的位置

			if (j == length)break;//已经到达末尾
		}
		return keys;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str0 = "我们American总共1.2亿人";
		String str1 = "American我们总共1.2亿人";
		String str2 = "我们American总共亿人1.2";
		String str = "我们喜欢American更热爱中华人民共和国但痛恨1.2+亿日本人++想独立的台湾佬";
		Set words=SplitWords.getAllWords(str0);
//		for (Iterator it = words.keySet().iterator(); it.hasNext();) {
//			Object key = it.next();
//			System.out.println(key + ":" + words.get(key));
//		}
		Object[] values= words.toArray();
		for (int i=0;i<values.length;i++) {
			System.out.println( values[i]);
		}
		// System.out.println(Character.isLetter('\\'));

	}

}