package adqr_biz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import adqr_model.GetADList;
import adqr_model.InsertAD;
import adqr_util.ContentData;
import adqr_util.MakeUpJson;
import adqr_util.ResultSetTOList;

public class makeADListReq {
	private static String result = null;
	private GetADList postdb = new GetADList();
	private ResultSetTOList rssettolist = new ResultSetTOList();
	private InsertAD aDInsert = new InsertAD();
	private ResultSet rsset = null;
	private List list = null;
	private JsonConfig cfg = new JsonConfig();
	private MakeUpJson muj = new MakeUpJson();

	public String makeSql(int sinceId, int untilId, int count) {
		String sql = null;
		if (sinceId == 0 && untilId == 0)
			sql = "SELECT * FROM ad_information order by ad_id DESC limit 0,"
					+ count + ";";
		else
			sql = "SELECT * FROM ad_information where ad_id <= '" + untilId
					+ "' and ad_id > '" + sinceId
					+ "' order by ad_id DESC limit 0," + count + ";";
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
		rtn.put("ADInformationList", json);
		result = rtn.toString();
		return result;
	}

	public String insertADInformation(int id, String time, String content)
			throws SQLException {
		boolean flag = aDInsert.executeFInsert(id, time, content);
		return muj.MakeUpNewsCommentInsert(flag);
	}

	public String insertContentList(ArrayList<ContentData> list) {

		// 用来记录有这一次的上传有多少个广告牌的信息
		ArrayList<ContentData> howMany = new ArrayList<ContentData>();

		// 将这一次上传数据的广告牌信息都记录下来
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIfGet() == 0) {
				// 首先判断这项数据还没有存进去
				int flag = 0;
				for (int j = 0; j < howMany.size(); j++) {
					// 首先判断该广告牌的数据是否已经存在,对于同一个用户上传的文件，username肯定是一致的
					// 在客户端判断同一个广告牌不一样的编码的情况
					if (howMany.get(j).address_id == list.get(i).address_id)
						flag = 1;
				}
				if (flag == 0) {
					howMany.add(list.get(i));
				}
				list.get(i).setIfGet(1);
			}
		}

		// 需要在这里将传过来的数据进行统计并且
		boolean flag = aDInsert.executeContentListInsert(list);
		// 先将数据全部插入数据库再从里面取出来进行对比
		for (int i = 0; i < howMany.size(); i++) {
			InsertResult(howMany.get(i));
		}

		return muj.MakeUpNewsCommentInsert(flag);

	}

	public void InsertResult(ContentData data) {

		String sql = null;
		String day = data.photoTime.substring(0, 10);
		String begintime = day + " 00:00:00";
		String endtime = day + " 23:59:59";

		sql = "SELECT * FROM detail_info where address_id = '"
				+ data.address_id + "' and user_number = '" + data.userNumber
				+ "' and photo_time >='" + begintime + "' and photo_time <='"
				+ endtime + "' order by photo_time ASC;";
		try {
			rsset = postdb.search(sql);
			list = rssettolist.resultSetToList(rsset);
			cfg.setExcludes(new String[] { "handler",
					"hibernateLazyInitializer" });
			JSONArray json = JSONArray.fromObject(list, cfg);
			int minMiao = Integer.MAX_VALUE;
			String lastTime = "";
			int flag = 0;
			// while (rsset.next()) {
			// ++flag;
			// if (flag == 1) {
			// lastTime = rsset.getString(5);
			// } else {
			// int current = dateDiff(lastTime, rsset.getString(5));
			// if (current < minMiao)
			// minMiao = current;
			// }
			//
			// }
			// 找出两个记录差距最小的，然后记录起来
			if (list.size() > 1) {
				for (int i = 0; i < json.size() - 1; i++) {
					JSONObject object3 = json.getJSONObject(i);
					JSONObject object4 = json.getJSONObject(i + 1);
					int current = dateDiff(object3.getString("photo_time"),
							object4.getString("photo_time"));
					if (current < minMiao)
						minMiao = current;
				}
				insertOrUpdate(data, minMiao);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = e.toString();
		}
	}

	public void insertOrUpdate(ContentData data, int minMiao) {
		String sql = null;
		String day = data.photoTime.substring(0, 10);
		List anotherList = null;// 用来记录该广告屏幕每一天开屏
		// 先从结果中查找是否存在该项数据，如果没有的话直接插入，如果存在的话对比两者之间的最小间距，然后判断是否需要修改
		sql = "SELECT * FROM result where address_id = '" + data.address_id
				+ "' and user_number ='" + data.userNumber + "' and day ='"
				+ day + "';";
		try {
			rsset = postdb.search(sql);
			// list = rssettolist.resultSetToList(rsset);

			// 在这里记录已经记录下来的频次
			int lastHz = 0;// 上一次一天播放的频次
			int id = 0;// 该条数据的id，
			// if (list.size() != 0) {
			while (rsset.next()) {
				lastHz = rsset.getInt(5);
				id = rsset.getInt(1);
			}

			// }

			sql = "select open_time from address_info where id = '"
					+ data.address_id + "';";
			rsset = postdb.search(sql);
			anotherList = rssettolist.resultSetToList(rsset);
			cfg.setExcludes(new String[] { "handler",
					"hibernateLazyInitializer" });
			JSONArray json2 = JSONArray.fromObject(anotherList, cfg);
			JSONObject object = json2.getJSONObject(0);
			double open_time = object.getDouble("open_time");
			int howMuchTime = 0;// 一天播放多少次
			howMuchTime = howMuchTime(open_time, minMiao);

			// 如果没有查询到该项数据，那么直接执行插入操作
			if (lastHz == 0) {
				String insertSql = "insert into ad_qr_db.result (address_id,advert_number,day,user_number,hz) VALUES ('"
						+ data.address_id
						+ "','"
						+ data.advert_number
						+ "','"
						+ day
						+ "','"
						+ data.userNumber
						+ "','"
						+ howMuchTime
						+ "')";
				aDInsert.executeInsert(insertSql);
			} else {
				if (lastHz < howMuchTime) {
					String sqlString = "update result set hz = '" + howMuchTime
							+ "' where id = '" + id + "';";
					aDInsert.executeInsert(sqlString);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = e.toString();
		}

	}

	// 获得两个时间相差多少秒
	/**
	 * 返回相差多少秒
	 * 
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public int dateDiff(String startTime, String endTime) {
		// 按照传入的格式生成一个simpledateformate对象
		double diff = 0;
		String format = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat sd = new SimpleDateFormat(format);
		// 获得两个时间的毫秒时间差异
		try {

			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int) diff / 1000;
	}

	/**
	 * 计算一天播放了多少次
	 * 
	 * @param open_time
	 * @param minTime
	 * @return
	 */
	public int howMuchTime(Double open_time, int minTime) {

		int open_miao = (int) (open_time * 60 * 60);
		return open_miao / (minTime + 7);

	}

}
