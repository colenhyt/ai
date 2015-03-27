package box.db;

import java.util.List;

import cn.hd.base.BaseService;

public class WxtitleService extends BaseService {
	private WxtitleMapper wxtitleMapper;
	
	public WxtitleMapper getWxtitleMapper() {
		return wxtitleMapper;
	}

	public void setWxtitleMapper(WxtitleMapper wxtitleMapper) {
		this.wxtitleMapper = wxtitleMapper;
	}

	public List<Wxtitle> findWxTitle(int type)
	{
		WxtitleExample example = new WxtitleExample();
		return wxtitleMapper.selectByExample(example);
	}	

	public WxtitleService()
	{
		initMapper("wxtitleMapper");
	}

}
