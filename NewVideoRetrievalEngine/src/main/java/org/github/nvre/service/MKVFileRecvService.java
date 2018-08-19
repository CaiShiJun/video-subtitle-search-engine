package org.github.nvre.service;

/**
 * 
* @ClassName: MKVFileRecvService 
* @Description: mkv视频文件接收服务 
* @author Cai ShiJun 
* @date 2018年5月31日 下午12:09:03 
*
 */
public interface MKVFileRecvService {

	
	//通过FFmpeg分离mkv文件中的ass格式字幕和srt格式字幕
	public boolean separationSubtitles();
	
	//将ass格式字幕和srt格式字幕分别存储到不同的文件夹下
	
}
