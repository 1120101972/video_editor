package adqr_biz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import adqr_model.GetPostList;
import adqr_util.ResultSetTOList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class makePostListReq {
	protected static String result = null;
	protected GetPostList postdb = new GetPostList();
	protected ResultSetTOList rssettolist = new ResultSetTOList();
	protected ResultSet rsset = null;
	protected List list = null;
	private JsonConfig cfg = new JsonConfig();

	// 获得广告牌的位置地址信息
	public String makeSql(String city_id) {
		String sql = null;

		if (city_id.equals("-1"))
			sql = "SELECT * FROM  ad_qr_db.address_info ";
		else {
			sql = "SELECT * FROM  ad_qr_db.address_info where city_id = "
					+ city_id;
		}

		try {
			rsset = postdb.search(sql);
			list = rssettolist.resultSetToList(rsset);
			result = list.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			result = e.toString();
		}
		cfg.setExcludes(new String[] { "handler", "hibernateLazyInitializer" });
		JSONArray json = JSONArray.fromObject(list, cfg);
		JSONObject rtn = new JSONObject();
		rtn.put("AddressInformationList", json);
		result = rtn.toString();
		return result;
	}
}