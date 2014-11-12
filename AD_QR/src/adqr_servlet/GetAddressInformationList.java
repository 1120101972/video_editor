package adqr_servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import adqr_biz.makePostListReq;
import adqr_util.RWclient;

import net.sf.json.JSONObject;

/**
 * 获得广告牌的信息
 */
public class GetAddressInformationList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JSONObject jsonob = new JSONObject();
	private RWclient rwc = new RWclient();
	private makePostListReq makeReq = new makePostListReq();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAddressInformationList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String city_id = request.getParameter("city_id");
		// String untilStr = request.getParameter("untilId");
		// String countStr = request.getParameter("count");
		// int sinceId = 0;
		// int untilId = 0;
		// int count = 0;
		// sinceId = Integer.parseInt(sinceStr);
		// untilId = Integer.parseInt(untilStr);
		// count = Integer.parseInt(countStr);
		// System.out.println(sinceId + "  " + untilId + "  " + count);
		String result = makeReq.makeSql(city_id);
		rwc.write(response, result);
		System.out.println(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			jsonob = rwc.read(request);
			String city_id = jsonob.getString("city_id");

			String result = makeReq.makeSql(city_id);

			rwc.write(response, result);
			System.out.println(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("访问GetPostInformationList接口出错");
		}
	}

}
