package com.yixia.camera.demo.activity;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yixia.camera.demo.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText et_telephone, et_password;
	private TextView tv_login, tv_register;
	JSONObject json = new JSONObject();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Init();
		listener();
	}

	private void Init() {
		et_password = (EditText) findViewById(R.id.et_password);
		et_telephone = (EditText) findViewById(R.id.et_telephone);
		tv_login = (TextView) findViewById(R.id.tv_login);
		tv_register = (TextView) findViewById(R.id.tv_register);

	}

	private void listener() {
		tv_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (et_password.getText().toString().equals("")
						|| et_telephone.getText().toString().equals("")) {
					Toast.makeText(LoginActivity.this, "请输入手机号和密码", 0).show();
					return;

				}

				try {
					json.put("phone", et_telephone.getText().toString());
					json.put("password", et_password.getText().toString());
				} catch (JSONException e) {

				}

				finish();

				// new RefreshTask().execute();

			}
		});
		tv_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(LoginActivity.this,
				// RegisterSendCodeActivity.class);
				// startActivity(intent);

			}
		});
	}

	public class RefreshTask extends AsyncTask<Void, Void, StringBuffer> {

		@Override
		protected StringBuffer doInBackground(Void... arg0) {

			// TODO Auto-generated method stub
			StringBuffer buffer = new StringBuffer();
			// HttpAsker.Asker(buffer, Constants.TEST_SERVER_STRING + "Login",
			// json);
			return buffer;
		}

		@Override
		protected void onPostExecute(StringBuffer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			JSONObject json;
			try {
				json = new JSONObject(result.toString());
				JSONObject end_result = new JSONObject(json.getString("result"));// username
				boolean isSuccess = json.getBoolean("success");
				if (isSuccess) {
					// Toast.makeText(LoginActivity.this, "登录成功", 0).show();
					// // 将手机号存储起来表示已经登录了
					// SharedPreferences userInfo = getSharedPreferences("User",
					// 0);
					// userInfo.edit()
					// .putString(Constants.NICK_NAME,
					// end_result.getString("username")).commit();
					// userInfo.edit()
					// .putString(Constants.USER_PHONE,
					// et_telephone.getText().toString()).commit();
					//
					// finish();
				} else {
					Toast.makeText(LoginActivity.this,
							json.getString("message"), 0).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
