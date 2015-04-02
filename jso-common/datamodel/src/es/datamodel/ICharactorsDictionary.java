package es.datamodel;

public interface ICharactorsDictionary {

	public boolean isCatalog(String urlstr);

	public boolean isItem(String urlstr);
	
	public String getItemKey(String urlstr);
	
	public boolean isItemSmallImg(String imgUrl,String urlkey);
}
