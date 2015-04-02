package gs;

import java.io.UnsupportedEncodingException;


public abstract class GSPage {
	protected final byte[] content;
	protected final String encoding;
	protected final String[] queryWords;
	protected final String urlStr;
	protected String context;
	protected final int searchType;

	public GSPage(String _urlStr,byte[] _content,String _encoding){
			this(_urlStr,_content,_encoding,-1,null);
			
		}
	
	public GSPage(String _urlStr,byte[] _content,String _encoding,int _searchType,String[] qWords){
			urlStr=_urlStr;
			content=_content;
			encoding=_encoding;
			searchType=_searchType;
			queryWords=qWords;
			try {
				context=new String(content,encoding);
			} catch (UnsupportedEncodingException e) {
				// log error here
				System.out.println(e.getMessage());
			}
			
		}

		protected static String generatUrl(String[] args,String preFix){
			String paramStr=args[0];
			for (int i=1;i<args.length;i++){
				paramStr=paramStr+"+"+args[i];
			}
			return preFix+paramStr;
		}		
		
		public abstract long outSearchCount();
		public abstract GSBlock[] outBlocks();
		public abstract String getUrlStr();

		public String[] getQueryWords() {
			return queryWords;
		}

		public int getSearchType() {
			return searchType;
		}
		
}
