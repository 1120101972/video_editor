package adqr_util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ResultSetTOList {
	@SuppressWarnings("rawtypes")
	public  List resultSetToList(ResultSet rs) throws java.sql.SQLException {      
		  if (rs == null)      
	           {
	    	   System.out.println("查询结果集为空");
	    	   return Collections.EMPTY_LIST;      
	           }
	       ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等      
	       int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数  
	       List list = new ArrayList();    
	       Map rowData = new HashMap(); 
	       while (rs.next())
	       {      
			        rowData = new HashMap(columnCount); 
			        for (int i = 1; i <= columnCount; i++) 
			        {      
			                rowData.put(md.getColumnName(i), rs.getObject(i));      
			        }      
			     
			        list.add(rowData);      
	       }      
	       return list;      
	}    
}
