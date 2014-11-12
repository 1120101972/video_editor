package adqr_util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.*;

public class RWclient {

	// 读取HttpServletRequest，将容器内数据转化成JSONObject对象
	public JSONObject read(HttpServletRequest req) throws ServletException,
			IOException {
		JSONObject jobj = null;
		String strJSON = "";

		ServletInputStream inStream = req.getInputStream();
		byte[] keykb;

		BufferedInputStream reader;
		reader = new BufferedInputStream(inStream);

		try {
			// 从客户端读写对象字符串
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			// buff用于存放循环读取的临时数据
			byte[] buff = new byte[1024];
			int rc = 0;
			while ((rc = reader.read(buff, 0, 1024)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			keykb = swapStream.toByteArray();
			// 字符流转str，str转JSON
			strJSON = new String(keykb, "UTF-8");
			System.out.println(strJSON);
			jobj = JSONObject.fromObject(strJSON);

		} catch (IOException e) {
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (inStream != null) {
					inStream.close();
				}
				// 释放内存
				reader = null;
				inStream = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jobj;
	}

	public boolean write(HttpServletResponse response, String str)
			throws ServletException, IOException {
		boolean bres = true;
		BufferedOutputStream writer;
		byte[] bt = null;

		response.setCharacterEncoding("UTF-8");
		bt = str.getBytes("UTF-8");

		// response.setContentType("application/octet-stream");
		response.setContentType("application/json");
		response.setContentLength(bt.length);
		OutputStream outStream = response.getOutputStream();
		writer = new BufferedOutputStream(outStream);

		try {
			// 将流写入到客户端
			writer.write(bt, 0, bt.length);
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			bres = false;
		}
		return bres;
	}
}
