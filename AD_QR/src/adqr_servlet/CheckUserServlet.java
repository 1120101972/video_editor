package adqr_servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import adqr_biz.MakeAccountReq;
import adqr_util.RWclient;

/**
 * 接口，用于检测输入的用户名和密码是否正确
 * @author wufangxue
 *
 */
public class CheckUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RWclient rwc = new RWclient();
	private JSONObject jsonob = new JSONObject();
	private MakeAccountReq makeReq = new MakeAccountReq();
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String telephone = request.getParameter("telephone");
			String password = request.getParameter("password");
			
			String result;
			result = makeReq.makeAskUserSql(telephone, password);
			rwc.write(response, result);
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
			String telephone = jsonob.getString("telephone");
			String password = jsonob.getString("password");
			String result = makeReq.makeAskUserSql(telephone, password);
			rwc.write(resp, result);
			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("访问CheckUserServlet接口出错");
		}
	}
	
	

}
