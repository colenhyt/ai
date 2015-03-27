package box.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.db.WxpublicExample.Criteria;
import cn.hd.base.BaseService;
import es.util.url.URLStrHelper;

public class WxService extends BaseService {
	private WxtitleMapper wxtitleMapper;
	private Map<String,Boolean> wxnameKeys;
	private Map<String,Boolean> wxtitleKeys;
	private URLStrHelper urlHelper;
	
	public WxtitleMapper getWxtitleMapper() {
		return wxtitleMapper;
	}

	public void setWxtitleMapper(WxtitleMapper wxtitleMapper) {
		this.wxtitleMapper = wxtitleMapper;
	}

	public WxpublicMapper getWxpublicMapper() {
		return wxpublicMapper;
	}

	public List<Wxsite> findsites()
	{
		WxsiteExample example = new WxsiteExample();
		return WxsiteMapper.selectByExample(example);
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
	}
	
	public void addNewwxtitle(List<Wxtitle> wxts)
	{
		for (int i=0;i<wxts.size();i++)
		{
			Wxtitle record = wxts.get(i);
			String key = urlHelper.getParamValue(record.getTitleurl(), "biz");
			if (!wxtitleKeys.containsKey(key))
			{
				record.setTitlekey(key);
				wxtitleMapper.insert(record);
				wxtitleKeys.put(record.getTitlekey(), true);
			}
		}		
	}
	
	public List<Wxtitle> findWxTitle(int type)
	{
		WxtitleExample example = new WxtitleExample();
		return wxtitleMapper.selectByExample(example);
	}	
	
	public void setWxpublicMapper(WxpublicMapper wxpublicMapper) {
		this.wxpublicMapper = wxpublicMapper;
	}

	public WxsiteMapper getWxsiteMapper() {
		return WxsiteMapper;
	}

	public void setWxsiteMapper(WxsiteMapper WxsiteMapper) {
		this.WxsiteMapper = WxsiteMapper;
	}

	private WxpublicMapper wxpublicMapper;
	private WxsiteMapper WxsiteMapper;
	
	public WxService()
	{
		initMapper("wxpublicMapper","WxsiteMapper","wxtitleMapper");
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
		
		
		wxtitleKeys = new HashMap<String,Boolean>();
		List<Wxtitle> wxts = findWxTitle(1);
		for (int i=0;i<wxts.size();i++)
		{
			wxtitleKeys.put(wxts.get(i).getTitlekey(), true);
		}
	}

}
