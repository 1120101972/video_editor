package adqr_biz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import adqr_model.GetADList;
import adqr_model.InsertAD;
import adqr_util.MakeUpJson;
import adqr_util.ResultSetTOList;

/**
 * 检测用户账号和密码的逻辑处理
 * 
 * @author wufangxue
 * 
 */
public class MakeAccountReq {

	private ResultSet rsset = null;
	private GetADList postdb = new GetADList();
	private MakeUpJson muj = new MakeUpJson();
	protected ResultSetTOList rssettolist = new ResultSetTOList();
	private List list;
	private InsertAD aDInsert = new InsertAD();
	private JsonConfig cfg = new JsonConfig();

	/**
	 * 检测账号是否存在
	 * 
	 * @param telephone
	 * @param password
	 * @return
	 */
	public String makeAskUserSql(String telephone, String password) {
		String sql = null;
		// sql = "SELECT * FROM user_info where telephone = '" + telephone
		// + "'and password = '" + password + "' ;";
		sql = "SELECT user_info.city_id , city_info.city_name FROM ad_qr_db.user_info , ad_qr_db.city_info where city_info.id = user_info.city_id and telephone = '"
				+ telephone + "' and password = '" + password + "'";

		try {
			rsset = postdb.search(sql);
			list = rssettolist.resultSetToList(rsset);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//需要返回当前用户是属于哪个城市的
		JSONArray json = JSONArray.fromObject(list, cfg);
		JSONObject rtn = new JSONObject();
		rtn.put("UserInfo", json);
		return rtn.toString();
		// if (list.size() == 0)
		// return muj.MakeUpNewsCommentInsert(false);
		// else {
		// return muj.MakeUpNewsCommentInsert(true);
		// }

	}

	/**
	 * 插入一条新的数据
	 * 
	 * @param telephone
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public String insertUserAccount(String telephone, String password)
			throws SQLException {
		boolean flag = aDInsert.executeUserInsert(telephone, password);
		return muj.MakeUpNewsCommentInsert(flag);
	}

}
