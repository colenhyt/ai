package box.action;

import java.util.List;

import net.sf.json.JSONArray;
import box.db.Wxtitle;
import box.db.WxtitleService;
import cn.hd.base.BaseAction;

public class ShowAction extends BaseAction{
	WxtitleService wpService;
	private Wxtitle wxtitle;
	
	public Wxtitle getWxtitle() {
		return wxtitle;
	}

	public void setWxtitle(Wxtitle wxtitle) {
		this.wxtitle = wxtitle;
	}

	public WxtitleService getWpService() {
		return wpService;
	}

	public void setWpService(WxtitleService wpService) {
		this.wpService = wpService;
	}

	public ShowAction(){
		init("wpService");
	}
	
	public String titles()
	{
		
		List<Wxtitle> titles = wpService.findWxTitle(1);
		System.out.println(titles.size()+"fdafdhhhhhhhh"+wxtitle.getStatus());
		JSONArray jsonObject = JSONArray.fromObject(titles);
		write(jsonObject.toString(),"utf-8");	
		
		return null;
	}

}
