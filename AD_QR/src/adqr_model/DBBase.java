package adqr_model;

import java.sql.*;

public class DBBase {
	//此类用于建立服务器连接
	protected static Connection conn = null;
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	 
	 //建立连接
	 public Connection openConn(){
		  //服务器配置：

	        String driver = "com.mysql.jdbc.Driver";
	        String url = "jdbc:mysql://localhost:3306/ad_qr_db";
	        String dbName = "root";
	        //String dbPass= "3gtelecom";     
	        String dbPass= "13934043458";  
	        try {    
				Class.forName(driver);//加载数据库驱动
				String temp_url = url + "?user="+ dbName + "&password=" + dbPass + "&useUnicode=true&characterEncoding=UTF-8";
				//String temp_url = url + "?user="+ dbName + "&password=" + dbPass + "&useUnicode=true&characterEncoding=UTF-8";
				//aConn = DriverManager .getConnection(url,dbName,dbPass);        //获得数据库连接
				conn = DriverManager .getConnection(temp_url);
				//DriverManager.get
			} catch (ClassNotFoundException e) {
				System.out.println("加载数据库驱动" + driver + "失败！");
				e.printStackTrace();
			}catch (SQLException e) {
				e.printStackTrace();
			}    
		return conn;   
	  }
	 
	 /**
	     * 释放资源
	     * @param conn 数据库连接
	     * @param pstmt PreparedStatement对象
	     * @param rs 结果集
	     */
	    public void closeAll() {
	        /*  如果rs不空，关闭rs  */
	        if(rs != null){
	            try { rs.close(); rs = null;} catch (SQLException e) {e.printStackTrace();}
	        }
	        /*  如果pstmt不空，关闭pstmt  */
	        if(pstmt != null){
	            try { pstmt.close(); pstmt = null;} catch (SQLException e) {e.printStackTrace();}
	        }
	        /*  如果stmt不空，关闭stmt  */
	        if(stmt != null){
	            try { stmt.close(); stmt = null;} catch (SQLException e) {e.printStackTrace();}
	        }
	        /*  如果conn不空，关闭conn  */
	        if(conn != null){
	            try { conn.close(); conn = null;} catch (SQLException e) {e.printStackTrace();}
	        }
	    }
}
