package adqr_servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import adqr_biz.makeADListReq;
import adqr_util.ContentData;
import adqr_util.RWclient;

/**
 * 存储拍摄记录，能够多条数据一起上传 传入的是一个JSONARRAY组成的字符串，其名称为data
 * 
 * @author wufangxue
 * 
 */
public class InsertContentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JSONObject jsonob = new JSONObject();
	private RWclient rwc = new RWclient();
	private makeADListReq makeReq = new makeADListReq();
	private ArrayList<ContentData> list = new ArrayList<ContentData>();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		try {
			jsonob = rwc.read(request);
			JSONArray data = jsonob.getJSONArray("data");

			// 将客户端传过来的数据放到列表里面
			for (int i = 0; i < data.size(); i++) {
				JSONObject object3 = data.getJSONObject(i);
				ContentData some = new ContentData();
				some.setAddressId(object3.getInt("address_id"));
				some.setadvert_number(object3.getString("advert_number"));
				some.setPhotoTime(object3.getString("photoTime"));
				some.setUserNumber(object3.getString("userNumber"));
				list.add(some);

			}

			// String content = jsonob.getString("content");
			String result = makeReq.insertContentList(list);
			rwc.write(response, result);
			System.out.println(result);
			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			jsonob = rwc.read(req);
			list.clear();
			JSONArray data = jsonob.getJSONArray("data");

			// 将客户端传过来的数据放到列表里面
			for (int i = 0; i < data.size(); i++) {
				JSONObject object3 = data.getJSONObject(i);
				ContentData some = new ContentData();
				some.setAddressId(object3.getInt("address_id"));
				some.setadvert_number(object3.getString("advert_number"));
				some.setPhotoTime(object3.getString("photoTime"));
				some.setUserNumber(object3.getString("userNumber"));
				list.add(some);

			}

			// String content = jsonob.getString("content");
			String result = makeReq.insertContentList(list);
			rwc.write(resp, result);
			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("访问InsertContentServlet接口出错");
		}

	}
}
