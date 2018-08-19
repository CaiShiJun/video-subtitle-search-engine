package org.github.nvre.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final long START_DATE_IN_MILLISECONDS = -28800000L;

	/**
	 * 用于处理 ass 字幕文件的特殊时间格式 h:mm:ss:毫秒数百分比，例如1:26:12.30
	 * 
	 * @param assTimeStr
	 * @return 总毫秒数
	 * @throws Exception
	 */
	public static long handleAssSpecialTimeFormatsToLong(String assTimeStr) throws Exception {

		DateFormat dateFormat = new SimpleDateFormat("H:mm:ss");
		String[] timeItem = assTimeStr.split("\\.");
		long timeItem0 = dateFormat.parse(timeItem[0]).getTime();
		long timeItem1 = Long.valueOf(timeItem[1]) * 10; // 因为timeItem[1]为百分比，所以等效于：Long.valueOf(timeItem[1]) * 1000 / 100;
		return timeItem0 + timeItem1 - START_DATE_IN_MILLISECONDS;
	}

	/**
	 * 用于处理 srt 字幕文件的特殊时间格式 HH:mm:ss,SSS，例如00:01:11,172
	 * 
	 * @param srtTimeStr
	 * @return
	 * @throws Exception
	 */
	public static Date handleSrtSpecialTimeFormatsToDate(String srtTimeStr) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss,SSS");
		return dateFormat.parse(srtTimeStr);
	}

	public static long handleSrtSpecialTimeFormatsToLong(String srtTimeStr) throws Exception {
		return handleSrtSpecialTimeFormatsToDate(srtTimeStr).getTime() - START_DATE_IN_MILLISECONDS;
	}

	/**
	 * 把毫秒转化成日期
	 * 
	 * @param dateFormat(日期格式，例如：MM/dd/yyyy HH:mm:ss)
	 * @param millSec(毫秒数)
	 * @return
	 */
	public static String transferLongToDate(String dateFormat, Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millSec);
		return sdf.format(date);
	}

}
