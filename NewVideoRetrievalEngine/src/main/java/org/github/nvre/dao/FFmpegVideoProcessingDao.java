package org.github.nvre.dao;

/**
 * 
* @ClassName: FFmpegVideoProcessingDao 
* @Description: FFmpeg视频处理Dao
* @author Cai ShiJun 
* @date 2018年5月31日 下午1:25:25 
*
 */
public interface FFmpegVideoProcessingDao {

	//使用FFmpeg分离mkv文件中的ass格式字幕和srt格式字幕
	//ffmpeg -i 被解析文件.mkv -an -vn -scodec copy 生成的ass格式字幕.ass

	//使用FFmpeg分离mkv文件中的ass格式字幕和srt格式字幕
	//ffmpeg -i 被解析文件.mkv -an -vn -scodec copy 生成的srt格式字幕.srt
}
