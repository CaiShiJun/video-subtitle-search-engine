package org.github.nvre.bean;

public class MKVSubtitlesBean {

	//该条字幕开始时间
	private long startTime;

	//该条字幕结束时间
	private long endTime;
	
	//该条字幕的文字内容
	private String itemContents;
	
	public MKVSubtitlesBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MKVSubtitlesBean(long startTime, long endTime, String itemContents) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.itemContents = itemContents;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getItemContents() {
		return itemContents;
	}

	public void setItemContents(String itemContents) {
		this.itemContents = itemContents;
	}

	@Override
	public String toString() {
		return "MKVSubtitlesBean [startTime=" + startTime + ", endTime=" + endTime + ", itemContents=" + itemContents + "]";
	}

}
