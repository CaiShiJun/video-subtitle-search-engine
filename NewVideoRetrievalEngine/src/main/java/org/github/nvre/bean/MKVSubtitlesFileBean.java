package org.github.nvre.bean;

import java.util.List;

/**
 * 
* @ClassName: MKVSubtitlesFileBean 
* @Description: MKV字幕文件Bean
* @author Cai ShiJun 
* @date 2018年6月1日 下午2:02:24 
*
 */
public class MKVSubtitlesFileBean {
	
	//主键 timeStamp+srtFileName
	private String primaryKey;

	//时间戳
	private long timeStamp;
	
	//文件名
	private String srtFileName;
	
	//字幕内容集合
	private List<MKVSubtitlesBean> mkvSubtitlesBeans;

	public MKVSubtitlesFileBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MKVSubtitlesFileBean(String primaryKey, long timeStamp, String srtFileName, List<MKVSubtitlesBean> mkvSubtitlesBeans) {
		super();
		this.primaryKey = primaryKey;
		this.timeStamp = timeStamp;
		this.srtFileName = srtFileName;
		this.mkvSubtitlesBeans = mkvSubtitlesBeans;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSrtFileName() {
		return srtFileName;
	}

	public void setSrtFileName(String srtFileName) {
		this.srtFileName = srtFileName;
	}

	public List<MKVSubtitlesBean> getMkvSubtitlesBeans() {
		return mkvSubtitlesBeans;
	}

	public void setMkvSubtitlesBeans(List<MKVSubtitlesBean> mkvSubtitlesBeans) {
		this.mkvSubtitlesBeans = mkvSubtitlesBeans;
	}

	@Override
	public String toString() {
		return "MKVSubtitlesFileBean [primaryKey=" + primaryKey + ", timeStamp=" + timeStamp + ", srtFileName=" + srtFileName + ", mkvSubtitlesBeans=" + mkvSubtitlesBeans + "]";
	}
	
}
