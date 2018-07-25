package ARC;

public class Row {
	private int reviewId;
	private String appName;
	private String versionNum;
	private String userName;
	private String date;
	private int rating;
	private String title;
	private String text;
	private int infoGiv;
	private int infoSeek;
	private int featRequest;
	private int hasBug;
	private int sentScore;
	
	public Row(int _reviewId, String _appName, String _versionNum, String _userName, String _date, int _rating, String _title, String _text, int _infoGiv,int _infoSeek,   int _featRequest,int _hasBug, int _sentScore) {
		reviewId = _reviewId;
		appName = _appName;
		versionNum = _versionNum;
		userName = _userName;
		date = _date;
		rating=_rating;
		title=_title;
		text = _text;
		infoGiv = _infoGiv;
		infoSeek = _infoSeek;
		featRequest = _featRequest;
		hasBug = _hasBug;
		sentScore =_sentScore;
	}
	public int getReviewId() {
		return reviewId;
	}
	public String getAppName() {
		return appName;
	}
	public String versionNum() {
		return versionNum;
	}
}
