package adqr_util;

import net.sf.json.*;

public class MakeUpJson {
	JSONObject obj = new JSONObject();

	public String MakeUpNews(JSONArray ja, String tablename, int num) {
		int SourceID = 0;
		if (tablename == "news_headline") {
			SourceID = 1;
		} 
		else if (tablename == "news_notice") {
			SourceID = 2;
		} 
		else if (tablename == "news_jwc") {
			SourceID = 3;
		} 
		else if (tablename == "ss_news_headline") {
			SourceID = 4;
		} 
		else if (tablename == "ss_news_notice") {
			SourceID = 5;
		} 
		else if (tablename == "ss_news_job") {
			SourceID = 6;
		}
		else if (tablename == "ss_news_unedu") {
			SourceID = 7;
		}
		else if (tablename == "ss_news_poedu") {
			SourceID = 8;
		}
		else if (tablename == "ss_news_biter") {
			SourceID = 9;
		}
		else if (tablename == "news_jobfair") {
			SourceID = 10;
		}
		else if (tablename == "news_job") {
			SourceID = 11;
		}
		else if (tablename == "news_intern") {
			SourceID = 12;
		}
		else if (tablename == "news_training") {
			SourceID = 13;
		}
		else {
			num = -1;
			SourceID = -1;
			ja = null;
		}
		obj.put("Number", num);
		obj.put("SourceID", SourceID);
		obj.put("NewsInfo", ja);
		return obj.toString();
	}

	public String MakeUpMeeting(JSONArray meetingInfo, int WeekID) {
		JSONObject obj = new JSONObject();
		obj.put("WeekID", WeekID);
		obj.put("MeetingInfo", meetingInfo);
		//obj.put("Remarks", meetingAid);
		return obj.toString();
	}
	
	public String MakeUpNewsComment(JSONArray newsCommentInfo){
		JSONObject obj = new JSONObject();
		obj.put("NewsCommentInfoJSONArray", newsCommentInfo);
		return obj.toString();
	}
	
	public String MakeUpNewsCommentInsert(boolean flag){
		JSONObject obj = new JSONObject();
		obj.put("Success", flag);
		return obj.toString();
	}
	
	public String MakeUpNewsCommentNum(int num){
		JSONObject obj = new JSONObject();
		obj.put("CommentNum", num);
		return obj.toString();
	}
	
	public String MakeUpAnnounceComment(JSONArray announceCommentInfo){
		JSONObject obj = new JSONObject();
		obj.put("AnnounceCommentInfoJSONArray", announceCommentInfo);
		return obj.toString();
	}
	
	public String MakeUpAnnounceCommentInsert(boolean flag){
		JSONObject obj = new JSONObject();
		obj.put("Success", flag);
		return obj.toString();
	}
	
	public String MakeUpAnnounceCommentNum(int num){
		JSONObject obj = new JSONObject();
		obj.put("CommentNum", num);
		return obj.toString();
	}
}
