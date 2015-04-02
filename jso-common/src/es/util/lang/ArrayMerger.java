package es.util.lang;

import java.util.HashSet;
import java.util.Set;

public class ArrayMerger {

	public long[] mergeLong(long[] a1,long[] a2){
		Set news=new HashSet();
		if (a1!=null&&a1.length>0){
			for (int i=0;i<a1.length;i++){
				news.add(String.valueOf(a1[i]));
			}
		}
		if (a2!=null&&a2.length>0){
			for (int i=0;i<a2.length;i++){
				news.add(String.valueOf(a2[i]));
			}
		}
		long[] newlong=new long[news.size()];
		String[] newStrs=(String[])news.toArray(new String[news.size()]);
		for (int j=0;j<news.size();j++){
			newlong[j]=new Long(newStrs[j]).longValue();
		}
		return newlong;
	}
}
