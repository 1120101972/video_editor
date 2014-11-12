package adqr_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetADList extends DBBase{
	Statement stmt = null;

	// 查询
	public ResultSet search(String sql) throws SQLException {
		stmt = null;
		conn = this.openConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
