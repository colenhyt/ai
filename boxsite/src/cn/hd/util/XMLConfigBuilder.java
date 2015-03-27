package cn.hd.util;

import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.ReaderInputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.Environment.Builder;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

public class XMLConfigBuilder extends BaseBuilder
{
  private boolean parsed;
  private XPathParser parser;
  private String environment;

  public static String dialect; //方言

  @Deprecated
  public XMLConfigBuilder(Reader reader)
  {
	  this(reader, null, null);
  }

  @Deprecated
  public XMLConfigBuilder(Reader reader, String environment) {
     this(reader, environment, null);
  }

  @Deprecated
  public XMLConfigBuilder(Reader reader, String environment, Properties props) {
     this(new ReaderInputStream(reader), environment, props);
  }

  public XMLConfigBuilder(InputStream inputStream) {
     this(inputStream, null, null);
  }

  public XMLConfigBuilder(InputStream inputStream, String environment) {
     this(inputStream, environment, null);
  }

  public XMLConfigBuilder(InputStream inputStream, String environment, Properties props) {
     super(new Configuration());
     ErrorContext.instance().resource("SQL Mapper Configuration");
     this.configuration.setVariables(props);
     this.parsed = false;
     this.environment = environment;
     this.parser = new XPathParser(inputStream, true, props, new XMLMapperEntityResolver());
  }

  public Configuration parse() {
     if (this.parsed) {
       throw new BuilderException("Each MapperConfigParser can only be used once.");
    }
     this.parsed = true;
     parseConfiguration(this.parser.evalNode("/configuration"));
     return this.configuration;
  }

  private void parseConfiguration(XNode root) {
    try {
       typeAliasesElement(root.evalNode("typeAliases"));
       pluginElement(root.evalNode("plugins"));
       objectFactoryElement(root.evalNode("objectFactory"));
       objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
       propertiesElement(root.evalNode("properties"));
       settingsElement(root.evalNode("settings"));
       environmentsElement(root.evalNode("environments"));
       typeHandlerElement(root.evalNode("typeHandlers"));
       mapperElement(root.evalNode("mappers"));
    } catch (Exception e) {
       throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
    }
  }

  private void typeAliasesElement(XNode parent) {
     if (parent != null)
       for (XNode child : parent.getChildren()) {
         String alias = child.getStringAttribute("alias");
         String type = child.getStringAttribute("type");
        try {
           Class clazz = Resources.classForName(type);
           if (alias == null)
             this.typeAliasRegistry.registerAlias(clazz);
          else
             this.typeAliasRegistry.registerAlias(alias, clazz);
        }
        catch (ClassNotFoundException e) {
           throw new BuilderException("Error registering typeAlias for '" + alias + "'. Cause: " + e, e);
        }
      }
  }

  private void pluginElement(XNode parent) throws Exception
  {
     if (parent != null)
       for (XNode child : parent.getChildren()) {
         String interceptor = child.getStringAttribute("interceptor");
         Properties properties = child.getChildrenAsProperties();
         Interceptor interceptorInstance = (Interceptor)resolveClass(interceptor).newInstance();
         interceptorInstance.setProperties(properties);
         this.configuration.addInterceptor(interceptorInstance);
      }
  }

  private void objectFactoryElement(XNode context)
    throws Exception
  {
     if (context != null) {
       String type = context.getStringAttribute("type");
       Properties properties = context.getChildrenAsProperties();
       ObjectFactory factory = (ObjectFactory)resolveClass(type).newInstance();
       factory.setProperties(properties);
       this.configuration.setObjectFactory(factory);
    }
  }

  private void objectWrapperFactoryElement(XNode context) throws Exception {
     if (context != null) {
       String type = context.getStringAttribute("type");
       ObjectWrapperFactory factory = (ObjectWrapperFactory)resolveClass(type).newInstance();
       this.configuration.setObjectWrapperFactory(factory);
    }
  }

  private void propertiesElement(XNode context) throws Exception {
     if (context != null) {
       Properties defaults = context.getChildrenAsProperties();
       String resource = context.getStringAttribute("resource");
       String url = context.getStringAttribute("url");
       if ((resource != null) && (url != null)) {
         throw new BuilderException("The properties element cannot specify both a URL and a resource based property file reference.  Please specify one or the other.");
      }
       if (resource != null)
         defaults.putAll(Resources.getResourceAsProperties(resource));
       else if (url != null) {
         defaults.putAll(Resources.getUrlAsProperties(url));
      }
       Properties vars = this.configuration.getVariables();
       if (vars != null) {
         defaults.putAll(vars);
      }
       this.parser.setVariables(defaults);
       this.configuration.setVariables(defaults);
    }
  }

  private void settingsElement(XNode context) throws Exception {
     if (context != null) {
       Properties props = context.getChildrenAsProperties();

       MetaClass metaConfig = MetaClass.forClass(Configuration.class);
       for (Iterator i$ = props.keySet().iterator(); i$.hasNext(); ) { Object key = i$.next();
         if (!metaConfig.hasSetter(String.valueOf(key))) {
           throw new BuilderException("The setting " + key + " is not known.  Make sure you spelled it correctly (case sensitive).");
        } }

       this.configuration.setAutoMappingBehavior(AutoMappingBehavior.valueOf(stringValueOf(props.getProperty("autoMappingBehavior"), "PARTIAL")));
       this.configuration.setCacheEnabled(booleanValueOf(props.getProperty("cacheEnabled"), Boolean.valueOf(true)).booleanValue());
       this.configuration.setLazyLoadingEnabled(booleanValueOf(props.getProperty("lazyLoadingEnabled"), Boolean.valueOf(false)).booleanValue());
       this.configuration.setAggressiveLazyLoading(booleanValueOf(props.getProperty("aggressiveLazyLoading"), Boolean.valueOf(true)).booleanValue());
       this.configuration.setMultipleResultSetsEnabled(booleanValueOf(props.getProperty("multipleResultSetsEnabled"), Boolean.valueOf(true)).booleanValue());
       this.configuration.setUseColumnLabel(booleanValueOf(props.getProperty("useColumnLabel"), Boolean.valueOf(true)).booleanValue());
       this.configuration.setUseGeneratedKeys(booleanValueOf(props.getProperty("useGeneratedKeys"), Boolean.valueOf(false)).booleanValue());
       this.configuration.setDefaultExecutorType(ExecutorType.valueOf(stringValueOf(props.getProperty("defaultExecutorType"), "SIMPLE")));
       this.configuration.setDefaultStatementTimeout(integerValueOf(props.getProperty("defaultStatementTimeout"), null));
    }
  }

  private void environmentsElement(XNode context) throws Exception {
     if (context != null) {
       if (this.environment == null) {
         this.environment = context.getStringAttribute("default");
      }
       dialect = this.environment;//设置方言
       for (XNode child : context.getChildren()) {
         String id = child.getStringAttribute("id");
         if (isSpecifiedEnvironment(id)) {
           TransactionFactory txFactory = transactionManagerElement(child.evalNode("transactionManager"));
           DataSourceFactory dsFactory = dataSourceElement(child.evalNode("dataSource"));
           Environment.Builder environmentBuilder = new Environment.Builder(id).transactionFactory(txFactory).dataSource(dsFactory.getDataSource());

           this.configuration.setEnvironment(environmentBuilder.build());
        }
      }
    }
  }

  private TransactionFactory transactionManagerElement(XNode context) throws Exception {
     if (context != null) {
       String type = context.getStringAttribute("type");
       Properties props = context.getChildrenAsProperties();
       TransactionFactory factory = (TransactionFactory)resolveClass(type).newInstance();
       factory.setProperties(props);
       return factory;
    }
     throw new BuilderException("Environment declaration requires a TransactionFactory.");
  }

  private DataSourceFactory dataSourceElement(XNode context) throws Exception {
     if (context != null) {
       String type = context.getStringAttribute("type");
       Properties props = context.getChildrenAsProperties();
       DataSourceFactory factory = (DataSourceFactory)resolveClass(type).newInstance();
       factory.setProperties(props);
       return factory;
    }
     throw new BuilderException("Environment declaration requires a DataSourceFactory.");
  }

  private void typeHandlerElement(XNode parent) throws Exception
  {
     if (parent != null)
       for (XNode child : parent.getChildren()) {
         String javaType = child.getStringAttribute("javaType");
         String jdbcType = child.getStringAttribute("jdbcType");
         String handler = child.getStringAttribute("handler");

         Class javaTypeClass = resolveClass(javaType);
         TypeHandler typeHandlerInstance = (TypeHandler)resolveClass(handler).newInstance();

         if (jdbcType == null)
           this.typeHandlerRegistry.register(javaTypeClass, typeHandlerInstance);
        else
           this.typeHandlerRegistry.register(javaTypeClass, resolveJdbcType(jdbcType), typeHandlerInstance);
      }
  }

  private void mapperElement(XNode parent)
    throws Exception
  {
     if (parent != null)
       for (XNode child : parent.getChildren()) {
         String resource = child.getStringAttribute("resource");
         String url = child.getStringAttribute("url");

         if ((resource != null) && (url == null)) {
//        	 if(dialect != null){
//        		 resource = resource.replace(".xml", "_"+dialect+".xml");//从方言指定位置查找
//        	 }

           ErrorContext.instance().resource(resource);
           InputStream inputStream = Resources.getResourceAsStream(resource);
           XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, this.configuration, resource, this.configuration.getSqlFragments());
           mapperParser.parse();
         } else if ((url != null) && (resource == null)) {
        	 if(dialect != null){
        		 url = url.replace(".xml", "_"+dialect+".xml");//从方言指定位置查找
        	 }
           ErrorContext.instance().resource(url);
           InputStream inputStream = Resources.getUrlAsStream(url);
           XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, this.configuration, url, this.configuration.getSqlFragments());
           mapperParser.parse();
        } else {
           throw new BuilderException("A mapper element may only specify a url or resource, but not both.");
        }
      }
  }

  private boolean isSpecifiedEnvironment(String id)
  {
     if (this.environment == null)
       throw new BuilderException("No environment specified.");
     if (id == null) {
       throw new BuilderException("Environment requires an id attribute.");
    }
     return this.environment.equals(id);
  }
}

/* Location:           D:\mybatis-3.0.4.jar
 * Qualified Name:     org.apache.ibatis.builder.xml.XMLConfigBuilder
 * JD-Core Version:    0.5.4
 */