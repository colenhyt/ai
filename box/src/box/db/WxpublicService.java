package box.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.db.WxpublicExample.Criteria;
import cn.hd.base.BaseService;
import es.util.url.URLStrHelper;

public class WxpublicService extends BaseService {
	private Map<String,Boolean> wxnameKeys;
	private URLStrHelper urlHelper;
	
	public WxpublicMapper getWxpublicMapper() {
		return wxpublicMapper;
	}

	public List<Wxpublic> findpublics(String siteId)
	{
		WxpublicExample example = new WxpublicExample();
		Criteria criteria = example.createCriteria();
		return wxpublicMapper.selectByExample(example);
	}
	
	public void addWxpublic(List<Wxpublic> wxps)
	{
		for (int i=0;i<wxps.size();i++)
		{
			Wxpublic record = wxps.get(i);
			if (!wxnameKeys.containsKey(record.getWxname()))
			 wxpublicMapper.insert(record);
		}
		DBCommit();
	}
	
	public void setWxpublicMapper(WxpublicMapper wxpublicMapper) {
		this.wxpublicMapper = wxpublicMapper;
	}

	private WxpublicMapper wxpublicMapper;
	
	public WxpublicService()
	{
		initMapper("wxpublicMapper");
		init();
	}
	
	public void init()
	{
		urlHelper = new URLStrHelper();
		wxnameKeys = new HashMap<String,Boolean>();
		List<Wxpublic> wxps = findpublics("aa");
		for (int i=0;i<wxps.size();i++)
		{
			wxnameKeys.put(wxps.get(i).getWxname(), true);
		}
	}

}
