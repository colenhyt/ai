package box.site.parser;

import box.site.model.TopItem;

public interface ITopItemParser {
	//得到item:
	public TopItem parse(String url,String pageContent);
}
