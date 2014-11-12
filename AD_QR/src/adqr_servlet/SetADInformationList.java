package adqr_servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import adqr_biz.makeADListReq;
import adqr_util.RWclient;

import net.sf.json.JSONObject;

public class SetADInformationList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JSONObject jsonob = new JSONObject();
	private RWclient rwc = new RWclient();
	private makeADListReq makeReq = new makeADListReq();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetADInformationList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response) i -> ad_id l -> ad_location t -> ad_title d -> ad_duration
	 *      s -> ad_shottime u -> ad_user
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String adLocationStr = request.getParameter("l");
			String adTitleStr = request.getParameter("t");
			String adDurationStr = request.getParameter("d");
			System.out.println(adLocationStr+' '+adTitleStr+' '+adDurationStr);
			String result;
			result = makeReq.insertADInformation(0,
					adTitleStr, adDurationStr);
			rwc.write(response, result);
			System.out.println(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
			int sinceId = jsonob.getInt("sinceId");
			int untilId = jsonob.getInt("untilId");
			int count = jsonob.getInt("count");

			String result = makeReq.makeSql(sinceId, untilId, count);

			rwc.write(response, result);
			System.out.println(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("访问SetADInformationList接口出错");
		}
	}

}