package adqr_servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;
import adqr_biz.MakeAccountReq;
import adqr_util.RWclient;

/**
 * 输入新的用户名和密码
 * 
 * @author wufangxue
 * 
 */
public class InsertUserAccountServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private JSONObject jsonob = new JSONObject();
	private RWclient rwc = new RWclient();
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
			result = makeReq.insertUserAccount(telephone, password);
			rwc.write(response, result);
			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		boolean isOk = true;
		String saveFileName = null;
		String funName = "";
		// 判断本次表单是否是一个multipart表单
		String user = "";

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 文件保存路径
			String savePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ "upload/";
			// String savePath = "D:/soft_anzhuangbao/xampp/htdocs/images/";
			// 文件上传缓冲区
			String tempPath = request.getSession().getServletContext()
					.getRealPath("/")
					+ "upload/temp/";
			File saveFile = new File(savePath);
			File tempFile = new File(tempPath);
			// 不存在以上路径则创建
			// isDirectory()是一个目录吗？
			if (!saveFile.isDirectory())
				saveFile.mkdirs();
			if (!tempFile.isDirectory())
				tempFile.mkdirs();

			// 获取工厂对象
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置缓冲区大小,单位字节
			factory.setSizeThreshold(1024 * 4);
			// 设置缓冲区位置
			factory.setRepository(tempFile);
			// 产生servlet上传对象
			ServletFileUpload uploader = new ServletFileUpload(factory);
			// 设置上传文件的最大大小，位置字节
			uploader.setSizeMax(4 * 1024 * 1024);
			// 获取表单项
			try {
				List<FileItem> fileItems = uploader.parseRequest(request);
				for (FileItem item : fileItems) {
					// 判断表单项是普通字段还是上传项
					if (item.isFormField()) {
						// String fieldName = item.getFieldName();
						// if (fieldName.equals("user")) {
						// user = item.getString();
						// System.out.print(user);
						// }

						funName = item.getString();
						try {
							jsonob = JSONObject.fromObject(funName);
							user = jsonob.getString("user");
							System.out.print(user);
						} catch (Exception e) {
							// TODO: handle exception
						}

					} else {
						// 上传项目
						String fileName = item.getName();
						// String
						// fix=fileName.substring(fileName.lastIndexOf("\\")+1);可以获取文件名
						String fix = fileName.substring(fileName
								.lastIndexOf(".") + 1);
						// if (ValiTool.isAllowFix(fix)) {
						// 根据当前时间和随机数生成新的文件名
						/*
						 * 可以通过日历对象获取Calendar Calendar
						 * date=Calendar.getInstance();
						 * fileName=""+date.get(Calendar.YEAR);
						 * fileName+=(date.get(Calendar.MONTH)+1);
						 * fileName+=date.get(Calendar.DAY_OF_MONTH);
						 * fileName+=date.get(Calendar.HOUR_OF_DAY);
						 * fileName+=date.get(Calendar.MINUTE);
						 * fileName+=date.get(Calendar.SECOND);
						 */
						// 通过util中的Date获取当前时间
						Date nowDate = new Date();
						// 格式化时间对象返回字符串
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyyMMddhhmmss");
						fileName = sdf.format(nowDate);
						// 毫秒数,类似于随机数为了避免文件重名
						fileName += System.currentTimeMillis();
						fileName += "." + fix;
						saveFileName = "/upload/" + fileName;
						// 构建文件对象的路径
						File file = new File(savePath + fileName);
						// 写入文件对象
						item.write(file);
						// } else {
						// isOk = false;
						// }
					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 下面是网页的处理可以自己选择
			if (isOk) {
				writer.write("<script>window.parent." + funName + "('"
						+ saveFileName + "');</script>");
			} else {
				System.out.println("格式不正确");
				writer.write("<script>window.parent." + funName + "("
						+ saveFileName + ");</script>");
			}
		}
	}

}
