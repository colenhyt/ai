package box.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.db.WxpublicExample.Criteria;
import cn.hd.base.BaseService;

public class WxpublicService extends BaseService {
		private WxtypeMapper wxtypeMapper;
		
	public WxtypeMapper getWxtypeMapper() {
			return wxtypeMapper;
		}

		public void setWxtypeMapper(WxtypeMapper wxtypeMapper) {
			this.wxtypeMapper = wxtypeMapper;
		}

	private Map<String,Boolean> wxhaokeys;
	
	public WxpublicMapper getWxpublicMapper() {
		return wxpublicMapper;
	}

	public List<Wxtype>	findtypes()
	{
		WxtypeExample example = new WxtypeExample();
		return wxtypeMapper.selectByExample(example);		
	}
	
	public List<Wxpublic> findAllNotSearchWp()
	{
		List<Wxtype> types= findtypes();
		List<Wxpublic> list  = new ArrayList<Wxpublic>();
		for (int i=0;i<types.size();i++)
		{
			List<Wxpublic> ll = findNotSearchWp(types.get(i).getType());
			if (ll.size()<=0)
				resetWp(types.get(i).getType());
			else
			 list.addAll(ll);
		}
		return list;
	}
	
	public List<Wxpublic> findNotSearchWp(int type)
	{
		WxpublicExample example = new WxpublicExample();
		Criteria criteria = example.createCriteria();
		if (type>0)
			criteria.andTypeEqualTo(type);
		 criteria.andStatusEqualTo(0);
		example.setLimit(5);
		List<Wxpublic> list = wxpublicMapper.selectByExample(example);
		return list;
	}
	
	public void resetWp(int type)
	{
		WxpublicExample example = new WxpublicExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		if (type>0)
			criteria.andTypeEqualTo(type);
			
		Wxpublic record = new Wxpublic();
		record.setStatus(0);
		wxpublicMapper.updateByExampleSelective(record, example);
		DBCommit();
	}
	
	public List<Wxpublic> findActiveWP(int type,String wxhao,int status)
	{
		WxpublicExample example = new WxpublicExample();
		Criteria criteria = example.createCriteria();
		if (status>=0)
		 criteria.andStatusEqualTo(status);
		if (type>0)
			criteria.andTypeEqualTo(type);
		if (wxhao!=null&&wxhao.length()>0)
			criteria.andWxhaoEqualTo(wxhao);
		example.setLimit(null);
		example.setOrderByClause(" udate desc ");
		return wxpublicMapper.selectByExample(example);
	}
	
	public void addWxpublic(List<Wxpublic> wxps)
	{
		for (int i=0;i<wxps.size();i++)
		{
			Wxpublic record = wxps.get(i);
			if (!wxhaokeys.containsKey(record.getWxhao().toLowerCase())){
			 wxpublicMapper.insert(record);
			 wxhaokeys.put(record.getWxhao().toLowerCase(), true);
			}
		}
		DBCommit();
	}
	
	public void setWxpublicMapper(WxpublicMapper wxpublicMapper) {
		this.wxpublicMapper = wxpublicMapper;
	}

	public WxpublicMapper wxpublicMapper;
	
	public WxpublicService()
	{
		initMapper("wxpublicMapper","wxtypeMapper");
		init();
	}
	
	public void updateByHao(String wxHao,Wxpublic record)
	{
		WxpublicExample example = new WxpublicExample();
		Criteria criteria = example.createCriteria();	
		criteria.andWxhaoEqualTo(wxHao);
		record.setWxhao(wxHao);
		record.setUdate(new Date());
		wxpublicMapper.updateByExampleSelective(record, example);
		DBCommit();
	}	
	
	public void updateStatusByHao(String wxHao,int status)
	{
		WxpublicExample example = new WxpublicExample();
		Criteria criteria = example.createCriteria();	
		criteria.andWxhaoEqualTo(wxHao);
		Wxpublic record = new Wxpublic();
		record.setWxhao(wxHao);
		record.setStatus(status);
		record.setUdate(new Date());
		wxpublicMapper.updateByExampleSelective(record, example);
		DBCommit();
	}
	
	public void init()
	{
		wxhaokeys = new HashMap<String,Boolean>();
		WxpublicExample example = new WxpublicExample();
		List<Wxpublic> wxps = wxpublicMapper.selectByExample(example);
		for (int i=0;i<wxps.size();i++)
		{
			wxhaokeys.put(wxps.get(i).getWxhao().toLowerCase(), true);
		}
	}

}
