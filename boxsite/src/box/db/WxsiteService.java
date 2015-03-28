package box.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.db.WxpublicExample.Criteria;
import cn.hd.base.BaseService;
import es.util.url.URLStrHelper;

public class WxsiteService extends BaseService {
	
	public List<Wxsite> findsites()
	{
		WxsiteExample example = new WxsiteExample();
		return WxsiteMapper.selectByExample(example);
	}

	public WxsiteMapper getWxsiteMapper() {
		return WxsiteMapper;
	}

	public void setWxsiteMapper(WxsiteMapper WxsiteMapper) {
		this.WxsiteMapper = WxsiteMapper;
	}

	private WxsiteMapper WxsiteMapper;
	
	public WxsiteService()
	{
		initMapper("WxsiteMapper");
		init();
	}

}
