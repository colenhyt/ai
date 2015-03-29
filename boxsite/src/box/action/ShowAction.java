package box.action;

import java.util.List;

import net.sf.json.JSONArray;
import box.db.Wxpublic;
import box.db.WxpublicService;
import box.db.Wxtitle;
import box.db.WxtitleService;
import box.db.Wxtype;
import cn.hd.base.BaseAction;

public class ShowAction extends BaseAction{
	private WxtitleService wtService;
	public WxtitleService getWtService() {
		return wtService;
	}

	public void setWtService(WxtitleService wtService) {
		this.wtService = wtService;
	}

	private Wxtitle wxtitle;
	private Wxpublic wxpublic;
	private WxpublicService wpService;
	
	public Wxpublic getWxpublic() {
		return wxpublic;
	}

	public void setWxpublic(Wxpublic wxpublic) {
		this.wxpublic = wxpublic;
	}

	public Wxtitle getWxtitle() {
		return wxtitle;
	}

	public void setWxtitle(Wxtitle wxtitle) {
		this.wxtitle = wxtitle;
	}

	public WxpublicService getWpService() {
		return wpService;
	}

	public void setWpService(WxpublicService wpService) {
		this.wpService = wpService;
	}

	public ShowAction(){
		init("wpService","wtService");
	}
	
	public String types()
	{
		List<Wxtype> data = wpService.findtypes();
		JSONArray jsonObject = JSONArray.fromObject(data);
		write(jsonObject.toString(),"utf-8");			
		return null;
	}
	
	public String wps()
	{
		List<Wxpublic> data = wpService.findActiveWP(wxpublic.getType(),wxpublic.getWxhao(),wxpublic.getStatus());
		JSONArray jsonObject = JSONArray.fromObject(data);
		write(jsonObject.toString(),"utf-8");		
		return null;
	}
	
	public String titles()
	{
		List<Wxtitle> data = wtService.findWxTitle(wxtitle.getType(),wxtitle.getWxhao(),wxtitle.getSrcflag());
		
		//System.out.println(data.size()+"fdafdhhhhhhhh"+wxtitle.getStatus());
		JSONArray jsonObject = JSONArray.fromObject(data);
		System.out.println("找到推文: "+jsonObject.size());
		
		write(jsonObject.toString(),"utf-8");	
		
		return null;
	}

	public String updatetitle()
	{
		wtService.updatetitle(wxtitle);
		writeMsg(0);
		System.out.println(wxtitle.getId()+":状态更新:"+wxtitle.getSrcflag());
		return null;
	}
}
