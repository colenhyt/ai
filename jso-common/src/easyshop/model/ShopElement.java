/*
 * Created on 2005-9-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.model;


/**
 * @author Allenhuang
 *
 * created on 2005-9-15
 */
public class ShopElement extends WebElement{
    private int uId;
    private int status=0;
  
    public int getUId() {
        return uId;
    }
    public void setUId(int id) {
        uId = id;
    }
    

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }    
}
