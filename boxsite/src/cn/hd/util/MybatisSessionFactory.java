package cn.hd.util;

import java.io.Reader;

import javax.servlet.ServletRequest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;



/**
 * Configures and provides access to Hibernate sessions, tied to the
 * current thread of execution.  Follows the Thread Local Session
 * pattern, see {@link http://hibernate.org/42.html }.
 */
public class MybatisSessionFactory {

    /** 
     * Location of hibernate.cfg.xml file.
     * Location should be on the classpath as Hibernate uses  
     * #resourceAsStream style lookup for its configuration file. 
     * The default classpath location of the hibernate config file is 
     * in the default package. Use #setConfigFile() to update 
     * the location of the configuration file for the current session.   
     */
    private static String CONFIG_FILE_LOCATION = "mybatis.xml";
	private static final ThreadLocal threadLocal = new ThreadLocal();
    private  static Reader reader ;
    private  static SqlSessionFactoryBuilder sessionFactoryBuilder=new SqlSessionFactoryBuilder(); ;
    private static SqlSessionFactory sessionFactory;
    private static String configFile = CONFIG_FILE_LOCATION;

	static {
    	try {
			reader=Resources.getResourceAsReader(CONFIG_FILE_LOCATION);
			sessionFactory = sessionFactoryBuilder.build(reader);
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
    }
    private MybatisSessionFactory() {
    }
	
	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static SqlSession getSession()  {
    	SqlSession session = (SqlSession) threadLocal.get();

		if (session == null) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

        return session;
    }

	/**
     *  Rebuild hibernate session factory
     *
     */
	public static void rebuildSessionFactory() {
		try {
			reader=Resources.getResourceAsReader(CONFIG_FILE_LOCATION);
			sessionFactory = sessionFactoryBuilder.build(reader);
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession(ServletRequest req) {
    	SqlSession session = (SqlSession) threadLocal.get();
        threadLocal.set(null);
        
        if (session != null) {
        	session.close();
        }
    }

	/**
     *  return session factory
     *
     */
	public static SqlSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
     *  return session factory
     *
     *	session factory will be rebuilded in the next call
     */
	public static void setConfigFile(String configFile) {
		MybatisSessionFactory.configFile = configFile;
		sessionFactory = null;
	}

	/**
	 * 处理异常
	 * @param e
	 */
	public static void proException(Exception e){
		e.printStackTrace();
		getSession().rollback();
	}

}