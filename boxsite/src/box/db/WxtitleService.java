package box.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.db.WxtitleExample.Criteria;
import cn.hd.base.BaseService;

public class WxtitleService extends BaseService {
	private WxtitleMapper wxtitleMapper;
	private Map<String,Boolean> wxtitleKeys;
	
	public WxtitleMapper getWxtitleMapper() {
		return wxtitleMapper;
	}

	public void setWxtitleMapper(WxtitleMapper wxtitleMapper) {
		this.wxtitleMapper = wxtitleMapper;
	}

	public List<Wxtitle> findWxTitle()
	{
		return findWxTitle(-1,null);
	}
	
	public void updatetitle(Wxtitle wxtitle)
	{
		wxtitleMapper.updateByPrimaryKeySelective(wxtitle);	
		DBCommit();
	}
	
	public List<Wxtitle> findWxTitle(int type,String wxhao)
	{
		WxtitleExample example = new WxtitleExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(0);	
		if (wxhao!=null&&wxhao.length()>0)
			criteria.andWxhaoEqualTo(wxhao);
		if (type>0)
			criteria.andTypeEqualTo(type);
		example.setOrderByClause("pubdate desc,viewcount desc");
		return wxtitleMapper.selectByExample(example);
	}	

	public boolean existTitle(String key)
	{
		return wxtitleKeys.containsKey(key);		
	}
	
	public void addTitlekey(String key)
	{
		wxtitleKeys.put(key, true);
	}
	
	public void addNewwxtitle(List<Wxtitle> wxts)
	{
		try {
		for (int i=0;i<wxts.size();i++)
		{
			Wxtitle record = wxts.get(i);
			{
				wxtitleMapper.insert(record);
			}
		}		
		DBCommit();
	}catch (Exception e){
		e.printStackTrace();
	}		
	}

	public void init()
	{
		wxtitleKeys = new HashMap<String,Boolean>();
		List<Wxtitle> wxts = findWxTitle();
		for (int i=0;i<wxts.size();i++)
		{
			wxtitleKeys.put(wxts.get(i).getTitlekey(), true);
		}
	}

	public WxtitleService()
	{
		initMapper("wxtitleMapper");
		//init();
	}

}
