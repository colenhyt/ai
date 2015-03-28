package box.db;

import java.util.List;

import box.db.GoodsitemExample.Criteria;
import cn.hd.base.BaseService;

public class GoodsitemService extends BaseService{
	private GoodsitemMapper goodsitemMapper;
	
	public GoodsitemService()
	{
		initMapper("goodsitemMapper");
	}
	
	public List<Goodsitem> findBySite(String site)
	{
		GoodsitemExample example = new GoodsitemExample();
		Criteria criteria = example.createCriteria();	
		//criteria.andSid2EqualTo(site);
		List<Goodsitem> list = goodsitemMapper.selectByExample(example);
		return list;
	}
	
	public GoodsitemMapper getGoodsitemMapper() {
		return goodsitemMapper;
	}

	public void setGoodsitemMapper(GoodsitemMapper goodsitemMapper) {
		this.goodsitemMapper = goodsitemMapper;
	}

	public static void main(String[] args) {
		GoodsitemService service = new GoodsitemService();
		service.findBySite("aa");
		int a = 10;
		a = 100;
		// TODO Auto-generated method stub

	}

}
