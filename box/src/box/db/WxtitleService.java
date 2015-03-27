package box.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.db.WxpublicExample.Criteria;
import cn.hd.base.BaseService;
import es.util.url.URLStrHelper;

public class WxtitleService extends BaseService {
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

	public void addNewwxtitle(List<Wxtitle> wxts)
	{
		try {
		for (int i=0;i<wxts.size();i++)
		{
			Wxtitle record = wxts.get(i);
			String titleKey1 = URLStrHelper.getParamValue(record.getTitleurl(), "__biz");
			String titleKey2 = URLStrHelper.getParamValue(record.getTitleurl(), "mid");
			String key = titleKey1+"_"+titleKey2;
			if (!wxtitleKeys.containsKey(key))
			{
				record.setTitlekey(key);
				wxtitleMapper.insert(record);
				wxtitleKeys.put(record.getTitlekey(), true);
			}
		}		
		DBCommit();
	}catch (Exception e){
		e.printStackTrace();
	}		
	}
	
	public List<Wxtitle> findWxTitle(int type)
	{
		WxtitleExample example = new WxtitleExample();
		return wxtitleMapper.selectByExample(example);
	}	

	public WxtitleService()
	{
		initMapper("wxtitleMapper");
		init();
	}
	
	public void init()
	{
		urlHelper = new URLStrHelper();
		
		wxtitleKeys = new HashMap<String,Boolean>();
		List<Wxtitle> wxts = findWxTitle(1);
		for (int i=0;i<wxts.size();i++)
		{
			wxtitleKeys.put(wxts.get(i).getTitlekey(), true);
		}
	}

}
