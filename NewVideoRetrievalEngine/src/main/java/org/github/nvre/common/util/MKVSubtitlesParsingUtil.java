package org.github.nvre.common.util;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.github.nvre.bean.MKVSubtitlesBean;
import org.github.nvre.bean.MKVSubtitlesFileBean;

public class MKVSubtitlesParsingUtil {

	public static MKVSubtitlesFileBean srtParsing(File srtFile) throws Exception {
		MKVSubtitlesFileBean mkvSubtitlesFileBean = new MKVSubtitlesFileBean();
		mkvSubtitlesFileBean.setTimeStamp(new Date().getTime());
		mkvSubtitlesFileBean.setSrtFileName(srtFile.getName());
		mkvSubtitlesFileBean.setPrimaryKey(new Date().getTime() + srtFile.getName());
		List<MKVSubtitlesBean> mkvSubtitlesBeanList = new ArrayList<>();
		List<String> srtContents = FileUtils.readLines(srtFile);

		// 正则表达式，用于匹配类似于“01:54:16,332 --> 01:54:18,163”的时间描述字符行
		String regex = "\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d --> \\d\\d:\\d\\d:\\d\\d,\\d\\d\\d";

		MKVSubtitlesBean mkvSubtitlesBean = null;
		StringBuilder stringBuilder = new StringBuilder();
		for (String lineStr : srtContents) {
			if (lineStr.equals("")) {
				mkvSubtitlesBean.setItemContents(stringBuilder.toString());
				mkvSubtitlesBeanList.add(mkvSubtitlesBean);
			}
			if (Pattern.matches(regex, lineStr)) { // 使用静态方法进行正则式的匹配。
				stringBuilder.setLength(0);
				mkvSubtitlesBean = new MKVSubtitlesBean();
				int symbolIndex = lineStr.indexOf(" --> ");
				String startTimeStr = lineStr.substring(0, symbolIndex);
				String endTimeStr = lineStr.substring(symbolIndex + 4);
				mkvSubtitlesBean.setStartTime(DateUtil.handleSrtSpecialTimeFormatsToLong(startTimeStr));
				mkvSubtitlesBean.setEndTime(DateUtil.handleSrtSpecialTimeFormatsToLong(endTimeStr));
			} else {
				stringBuilder.append(lineStr);
			}
		}

		mkvSubtitlesFileBean.setMkvSubtitlesBeans(mkvSubtitlesBeanList);
		return mkvSubtitlesFileBean;
	}

	public static MKVSubtitlesFileBean assParsing(File assFile) throws Exception {
		MKVSubtitlesFileBean mkvSubtitlesFileBean = new MKVSubtitlesFileBean();
		mkvSubtitlesFileBean.setTimeStamp(new Date().getTime());
		mkvSubtitlesFileBean.setSrtFileName(assFile.getName());
		mkvSubtitlesFileBean.setPrimaryKey(new Date().getTime() + assFile.getName());
		List<MKVSubtitlesBean> mkvSubtitlesBeanList = new ArrayList<>();
		List<String> srtContents = FileUtils.readLines(assFile);

		MKVSubtitlesBean mkvSubtitlesBean = null;
		StringBuilder stringBuilder = new StringBuilder();
		for (String lineStr : srtContents) {
			if (lineStr.startsWith("Dialogue")) {
				stringBuilder.setLength(0);
				mkvSubtitlesBean = new MKVSubtitlesBean();
				String[] dialogueItem = lineStr.split(",");
				String startTimeStr = dialogueItem[1];
				String endTimeStr = dialogueItem[2];
				mkvSubtitlesBean.setStartTime(DateUtil.handleAssSpecialTimeFormatsToLong(startTimeStr));
				mkvSubtitlesBean.setEndTime(DateUtil.handleAssSpecialTimeFormatsToLong(endTimeStr));
				for (int i = 9; i < dialogueItem.length; i++) {
					stringBuilder.append(dialogueItem[i]);
				}
				mkvSubtitlesBean.setItemContents(stringBuilder.toString());
				mkvSubtitlesBeanList.add(mkvSubtitlesBean);
			}
		}

		mkvSubtitlesFileBean.setMkvSubtitlesBeans(mkvSubtitlesBeanList);
		return mkvSubtitlesFileBean;
	}

	public static List<MKVSubtitlesFileBean> ConvertAllSubtitlesInFolder(List<String> subtitlesFolderUrlList) throws Exception {
		List<MKVSubtitlesFileBean> mkvSubtitlesFileBeanList = new ArrayList<MKVSubtitlesFileBean>();
		for (String subtitlesFolderUrl : subtitlesFolderUrlList) {
			mkvSubtitlesFileBeanList.addAll(ConvertAllSubtitlesInFolder(subtitlesFolderUrl));
		}
		return mkvSubtitlesFileBeanList;
	}

	public static List<MKVSubtitlesFileBean> ConvertAllSubtitlesInFolder(String subtitlesFolderUrl) throws Exception {
		List<MKVSubtitlesFileBean> mkvSubtitlesFileBeanList = new ArrayList<MKVSubtitlesFileBean>();
		//创建要获取文件的文件夹File对象，new File()中是文件夹的路径，注意用“/”。
		File folder = new File(subtitlesFolderUrl);
		//遍历获取文件夹File对象中的所有文件
		for(File file : folder.listFiles()){
			if(FileUtil.determineFileSuffix(file, ".srt")) {
				mkvSubtitlesFileBeanList.add(srtParsing(file));
			}else if(FileUtil.determineFileSuffix(file, ".ass")){
				mkvSubtitlesFileBeanList.add(assParsing(file));
			}
		}
		return mkvSubtitlesFileBeanList;
	}

}
