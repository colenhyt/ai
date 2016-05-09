package es.util.word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

public class JiebaHelper {
	static JiebaSegmenter segmenter = new JiebaSegmenter();
	
	public static void main(String[] args){
		JiebaHelper.getWords("这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。");
	}
	
	public static Map<String,WordToken> getWords(String sentence){
		Map<String,WordToken> tokens = new HashMap<String,WordToken>();
		List<SegToken> segToken = segmenter.process(sentence, SegMode.INDEX);
		for (SegToken item:segToken){
			if (!tokens.containsKey(item.word)){
				WordToken token = new WordToken();
				token.setWord(item.word);
				tokens.put(item.word, token);
			}
			tokens.get(item.word).addFreq();	
		}
		System.out.println("语句:"+sentence);
		System.out.println(tokens.toString());
		return tokens;
	}
	
	public static void test(){
		JiebaSegmenter segmenter = new JiebaSegmenter();
	    String[] sentences =
	        new String[] {"这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。", "我不喜欢日本和服。", "雷猴回归人间。",
	                      "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", "结果婚的和尚未结过婚的"};
	    for (String sentence : sentences) {
	        System.out.println(segmenter.process(sentence, SegMode.SEARCH).toString());
	    }		
	}
}
