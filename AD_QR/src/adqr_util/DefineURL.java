package adqr_util;

public class DefineURL {
	
	static String localurl;
	
	public static String LocalURL()
	{
		//测试环境
		localurl = "http://10.1.112.12/";
		//
		//localurl = "http://10.1.112.231/";
		//生产路径
		//localUrl = "http://1.202.222.148/";
		
		return localurl;
	}

}
