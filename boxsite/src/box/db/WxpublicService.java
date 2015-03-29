package box.db;

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
	
	public List<Wxpublic> findNotSearchWp()
	{
		return findActiveWP(-1,null,3);
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
		//init();
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
		List<Wxpublic> wxps = findActiveWP(-1,null,-1);
		for (int i=0;i<wxps.size();i++)
		{
			wxhaokeys.put(wxps.get(i).getWxhao().toLowerCase(), true);
		}
	}

}
