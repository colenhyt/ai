/*
 * Created on 2005-9-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.common.dao;

/**
 * @author Allenhuang
 *
 * created on 2005-9-14
 */
public interface EasyDAOFinder {
    
    public static final String PROPERTIES_FILE="dao.properties";
    
    public static final int DAO_ROOT=0;
    
    public static final int DAO_ORIGINALpAGES=1;
    
    public EasyDAO findDAO(int daoType);

}
