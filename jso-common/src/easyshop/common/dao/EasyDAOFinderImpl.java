/*
 * Created on 2005-9-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.common.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * @author Allenhuang
 *
 * created on 2005-9-14
 */
public class EasyDAOFinderImpl implements EasyDAOFinder {
    
    public static final String SQLORIGINALPAGESDAO_CLASS="easyshop.download.dao.SQLServerOriginalPagesDAO";
    private static EasyDAOFinder impl,impl2;
    private int dbType=-1;
    private static Properties props;
    static {
        try {
            FileInputStream in=new FileInputStream(PROPERTIES_FILE);
            props.load(in);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private EasyDAOFinderImpl(){
        
    }
    private EasyDAOFinderImpl(int dbType){
        this.dbType=dbType;
    }
    
    public static EasyDAOFinder get(){
        if (impl==null)
            impl=new EasyDAOFinderImpl();
        return impl;
    }
    
    public static EasyDAOFinder get(int dbType){
        if (impl2==null)
            impl2=new EasyDAOFinderImpl(dbType);
        return impl2;
    }

    public EasyDAO findDAO(int daoType) {
        if (dbType==-1){
        switch (daoType){
           case  EasyDAOFinder.DAO_ROOT:return (EasyDAO)getInstance(SQLORIGINALPAGESDAO_CLASS);
        }
    }
        return null;
    }
    
    private Object getInstance(String className){
        try {
            return Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
