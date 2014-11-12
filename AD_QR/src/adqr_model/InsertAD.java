package adqr_model;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import adqr_util.ContentData;

public class InsertAD extends DBBase {
	/**
	 * 存入获得的二维码信息
	 * 
	 * @param id
	 * @param time
	 * @param content
	 * @return
	 * @throws SQLException
	 */
	public boolean executeFInsert(int id, String time, String content)
			throws SQLException {
		String sqlStr = "";
		boolean flag = true;
		Statement stmt = null;
		conn = this.openConn();
		try {
			flag = false;
			sqlStr = "INSERT INTO base_info(id,time,content) VALUES (NULL"
					+ ",'" + time + "','" + content + "')";
			stmt = conn.createStatement();
			int n = stmt.executeUpdate(sqlStr);
			if (n > 0)
				flag = true;
			else
				flag = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stmt.close();
		conn.close();
		return flag;
	}

	/**
	 * 存入用户的账号和密码
	 * 
	 * @param telephone
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean executeUserInsert(String telephone, String password)
			throws SQLException {
		String sqlStr = "";
		boolean flag = true;
		Statement stmt = null;
		conn = this.openConn();
		try {
			flag = false;
			sqlStr = "INSERT INTO ad_qr_db.user_info (telephone,password) VALUES ('"
					+ telephone + "','" + password + "')";
			// sqlStr = "INSERT INTO user_info(telephone,password) VALUES (" +
			// telephone + "','" + password + "')";
			stmt = conn.createStatement();
			int n = stmt.executeUpdate(sqlStr);
			if (n > 0)
				flag = true;
			else
				flag = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stmt.close();
		conn.close();
		return flag;
	}

	/**
	 * 直接传入sql语句执行插入操作
	 * 
	 * @param sql
	 * @return
	 */
	public boolean executeInsert(String sql) {
		boolean flag = true;
		int n = 0;
		Statement stmt = null;
		conn = this.openConn();

		try {
			stmt = conn.createStatement();

			n = stmt.executeUpdate(sql);

			if (n > 0)
				flag = true;
			else
				flag = false;
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return flag;

	}

	public boolean executeContentListInsert(ArrayList<ContentData> list) {
		String sqlString = "";
		boolean flag = true;
		int n = 0;
		Statement stmt = null;
		conn = this.openConn();
		System.out.print("上传了" + list.size() + "条数据");
		try {
			stmt = conn.createStatement();
			for (int i = 0; i < list.size(); i++) {
				sqlString = "INSERT INTO ad_qr_db.detail_info (address_id,photo_time,user_number,advert_number) VALUES ('"
						+ list.get(i).getAddressId()
						+ "','"
						+ list.get(i).getPhotoTime()
						+ "','"
						+ list.get(i).getUserNumber()
						+ "','"
						+ list.get(i).getadvert_number() + "');";
				n = stmt.executeUpdate(sqlString);
			}

			if (n > 0)
				flag = true;
			else
				flag = false;
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return flag;

	}
}
