package es.util.word;

public class WordToken {
	String word;
	int freq;
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getFreq() {
		return freq;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	public void addFreq() {
		this.freq++;;
	}	
	
	public String toString(){
		return this.word+","+this.freq;
	}
}
