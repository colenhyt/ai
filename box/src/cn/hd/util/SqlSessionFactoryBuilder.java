/*    */ package cn.hd.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.Reader;
/*    */ import java.util.Properties;
/*    */ import org.apache.ibatis.executor.ErrorContext;
/*    */ import org.apache.ibatis.io.ReaderInputStream;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
/*    */ 
/*    */ public class SqlSessionFactoryBuilder
/*    */ {
/*    */   public SqlSessionFactory build(Reader reader)
/*    */   {
/* 17 */     return build(reader, null, null);
/*    */   }
/*    */ 
/*    */   public SqlSessionFactory build(Reader reader, String environment) {
/* 21 */     return build(reader, environment, null);
/*    */   }
/*    */ 
/*    */   public SqlSessionFactory build(Reader reader, Properties properties) {
/* 25 */     return build(reader, null, properties);
/*    */   }
/*    */ 
/*    */   public SqlSessionFactory build(Reader reader, String environment, Properties props) {
/* 29 */     return build(new ReaderInputStream(reader), environment, props);
/*    */   }
/*    */ 
/*    */   public SqlSessionFactory build(InputStream inputStream) {
/* 33 */     return build(inputStream, null, null);
/*    */   }
/*    */ 
/*    */   public SqlSessionFactory build(InputStream inputStream, String environment) {
/* 37 */     return build(inputStream, environment, null);
/*    */   }
/*    */ 
/*    */   public SqlSessionFactory build(InputStream inputStream, Properties properties) {
/* 41 */     return build(inputStream, null, properties);
/*    */   }
/*    */ 
/*    */   public SqlSessionFactory build(InputStream inputStream, String environment, Properties props) {
/*    */     try {
/* 46 */       XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, props);
/* 47 */       Configuration config = parser.parse();
/* 48 */       SqlSessionFactory localSqlSessionFactory = build(config);
/*    */ 
/* 57 */       return localSqlSessionFactory;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
				e.printStackTrace();
				return null;
/*    */     }
/*    */     finally
/*    */     {
/* 52 */       ErrorContext.instance().reset();
/*    */       try {
/* 54 */         inputStream.close();
/*    */       }
/*    */       catch (IOException e) {
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public SqlSessionFactory build(Configuration config) {
/* 62 */     return new DefaultSqlSessionFactory(config);
/*    */   }
/*    */ }

/* Location:           D:\mybatis-3.0.4.jar
 * Qualified Name:     org.apache.ibatis.session.SqlSessionFactoryBuilder
 * JD-Core Version:    0.5.4
 */