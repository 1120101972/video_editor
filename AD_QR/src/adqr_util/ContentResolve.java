package adqr_util;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ContentResolve {
	private JSONArray jsary = new JSONArray();
	String d[] =null;
	int num=0;
	
	public JSONArray ResolveContent(String Content) throws IOException //解析数据库中NewsContent内容
	{	
		jsary.clear();
		String newsLabel[] = Content.split("\n");
		
		String localUrl = DefineURL.LocalURL();
		
		int len1 =newsLabel.length;
		num = 0;
		for(int i=0 ; i<len1 ; i++)
		{
			String s = null;
			JSONObject json = new JSONObject();
			if(newsLabel[i].contains("<text>"))
			{		
				s = newsLabel[i].replaceAll("<.+?>", "");
				s = s + "\n";
				json.put("type", "text");
				json.put("detail", s);
			}
			else if(newsLabel[i].contains("<img>"))
			{			
				s = newsLabel[i].replaceAll("<.+?>", "");
				s = s.substring(63,s.length());
				s = localUrl + s;
				json.put("type", "img");
				json.put("detail", s);
				
			}
			else if(newsLabel[i].contains("<atturl>"))
			{		
				s = newsLabel[i].replaceAll("<.+?>", "");
				s = s.substring(63,s.length());
				s = localUrl + s;
				json.put("type", "atturl");
				json.put("detail", s);			
			}
			else if(newsLabel[i].contains("<attname>"))
			{		
				s = newsLabel[i].replaceAll("<.+?>", "");	
				json.put("type", "attname");
				json.put("detail", s);			
			}
			jsary.add(num, json);
			num++;
//			if(newsLabel[i].contains("<img>"))
//			{
//				d = newsLabel[i].split("<img>");
//				int len2 =d.length;
//				for(int j=0;j<len2;j++){
//					if(d[j].startsWith("C:"))
//					{
//     					JSONObject json = new JSONObject();
//						json.put("type", "img");
//						String b = d[j].substring(68,d[j].length());//截取图片存储相对路径
//						String a = "http://1.202.222.147:8181/"+b;
//					//	String a = "http://10.1.112.100/Digital_BIT_Sever/"+d[j];/*这里已经没有图片了，楼上更新数据有点问题*/
//						int len3 = a.length();
//						a = a.substring(0, len3-1);//去掉回车
//						json.put("detail",a);	
//						jsary.add(num,json);
//						num++;
//					}
//					else
//					{
//						JSONObject json = new JSONObject();
//						json.put("type", "text");
//						json.put("detail", d[j]);	
//						jsary.add(num,json);
//						num++;
//					}
//				}
//			}
//			else{
//				JSONObject json = new JSONObject();
//				json.put("type", "text");
//				json.put("detail", newsLabel[i]);	
//				jsary.add(num,json);
//				num++;
//			}
		}
		return jsary;
	}

	public String ResolveContentList(String Content){
		String content = "";
		String s1[] = Content.split("\n");
		int len1 =s1.length;
		for(int i=0 ; i<len1 ; i++)
		{
			if(s1[i].contains("<text>"))
			{
				String s = s1[i].replaceAll("<.+?>", "");
				content = content + s;
			}
			else if(s1[i].contains("<attname>"))
			{
				String s = s1[i].replaceAll("<.+?>", "");
				content = content + s;
			}
			if(content.length()>=50)
				return content.substring(0, 50);
		}
		return content;
	}
//	  public static void main(String [] args){
//		//String s ="<text>供稿：德语培训中心编辑：吴昊 <img>Image/News/77645/0.jpg ";
//		String s = "<text>供稿：后勤集团摄影：学管中心编辑：王海涛 <img>C://Program Files//Apache Software Foundation//Tomcat 6.0//webapps//Digital_BIT_Sever//Image/News/77800/0.jpg <text>供稿：后勤集团摄影：学管中心编辑：王海涛 <img>C://Program Files//Apache Software Foundation//Tomcat 6.0//webapps//Digital_BIT_Sever//Image/News/77800/0.jpg <text>供稿：后勤集团摄影：学管中心编辑：王海涛 ";
//		ContentResolve r =new ContentResolve();
//		JSONArray js = r.ResolveContent(s);
//		System.out.print(js);
//	}
}
