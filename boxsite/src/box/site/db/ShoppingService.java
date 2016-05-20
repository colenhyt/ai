package box.site.db;

import java.util.List;

import box.site.model.Shoppingdata;
import box.site.model.ShoppingdataExample;
import box.site.model.ShoppingdataMapper;
import cn.hd.base.BaseService;

public class ShoppingService extends BaseService {
	private ShoppingdataMapper shoppingdataMapper;
	
	public ShoppingdataMapper getShoppingdataMapper() {
		return shoppingdataMapper;
	}
	public void setShoppingdataMapper(ShoppingdataMapper shoppingdataMapper) {
		this.shoppingdataMapper = shoppingdataMapper;
	}
	public ShoppingService(){
		initMapper("shoppingdataMapper");		
	}
	
	public List<Shoppingdata> getData(){
		ShoppingdataExample example = new ShoppingdataExample();
		return shoppingdataMapper.selectByExample(example);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShoppingService s = new ShoppingService();
		System.out.println(s.getData().size());
	}

}
