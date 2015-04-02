/*
 * Created on 2005-9-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.model;

/**
 * @author Allenhuang
 *
 * created on 2005-9-16
 */
public class StatusConstants {
    public static final int ORIGINAL_STATUS_DEAD=-2;
    public static final int ORIGINAL_STATUS_UNKNOWN=-3;
    
    //wrong item page analyse status
    public static final int ORIGINAL_STATUS_ERROR_ITEMPAGE=-4;
    public static final int ORIGINAL_STATUS_SELLOUT_ITEMPAGE=-5;
    public static final int ORIGINAL_STATUS_NOTFOUND_ITEMPAGE=-6;
    public static final int ORIGINAL_STATUS_NOTBOOK_ITEMPAGE=-7;
    public static final int ORIGINAL_STATUS_WRONGINFO_ITEMPAGE=-8;
    public static final int ORIGINAL_STATUS_WRONG_URLADDRESS_ITEMPAGE=-9;
    public static final int ORIGINAL_STATUS_ERROR_NOTFOUNDISBN=-10;
    
    public static final int ORIGINAL_STATUS_OLD=-1;
    public static final int ORIGINAL_STATUS_NORMAL=0;
   public static final int ORIGINAL_STATUS_NEW=1;
    public static final int ORIGINAL_STATUS_NEW_YET=2;
    public static final int ORIGINAL_STATUS_UPDATE=3;
    public static final int ORIGINAL_STATUS_UPDATE_YET=4;
    
    public static final int ORIGINAL_PAGE_STATUS_ERROR_NULLNAME=-10;
    public static final int ORIGINAL_PAGE_STATUS_ERROR_NULLIMGURL=-11;
    public static final int ORIGINAL_PAGE_STATUS_ERROR_NULLAUTHOR=-12;
    public static final int ORIGINAL_PAGE_STATUS_ERROR_NULLPUB=-13;
    public static final int ORIGINAL_PAGE_STATUS_ERROR_NULLISBN=-14;
    
    public static final int ITEM_STATUS_NORMAL=0;
    
    //是否处理了页面中链接:
    public static final int ORIGINSREF_DEAD_DEAL=-2;
    public static final int ORIGINSREF_NO_DEAL=-1;
    public static final int ORIGINSREF_REF_DEALED=0;
    public static final int ORIGINSREF_REF_WAITING_DEAL=1;

    public static final int ORIGINAL_REF_STATUS_DEAD=-2;
   public static final int ORIGINAL_REF_STATUS_OLD=-1;
    public static final int ORIGINAL_REF_STATUS_NORMAL=0;
    public static final int ORIGINAL_REF_STATUS_NEW=1;
    
    public static final int FULL_ACTIVEPAGES_COUNT=150000;
   

}
