package org.github.nvre.controller;

import java.io.File;
import java.io.IOException;

import org.github.nvre.bean.MKVSubtitlesBean;
import org.github.nvre.bean.MKVSubtitlesFileBean;
import org.github.nvre.common.util.MKVSubtitlesParsingUtil;
import org.github.nvre.common.util.PropertiesUtil;
import org.github.nvre.dao.LuceneFTRDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
* @ClassName: HomeController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Cai ShiJun 
* @date 2018年5月31日 上午9:39:16 
*
 */
@RestController
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private LuceneFTRDao luceneFTRDao;
	
	/**
	 * 
	* @Title: goToHomePage 
	* @Description: 进入主页 
	* @return 
	 * @throws Exception 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年5月31日 上午9:48:00
	 */
	@RequestMapping()
	public ModelAndView goToHomePage() throws Exception{
		ModelAndView mv = new ModelAndView("/html/home");
		
		luceneFTRDao.createIndexFromMKVSubtitlesFileBeanList(MKVSubtitlesParsingUtil.ConvertAllSubtitlesInFolder(PropertiesUtil.getProperties("config/application").getProperty("mkv.subtitles.srt.folder.url")));
		luceneFTRDao.createIndexFromMKVSubtitlesFileBeanList(MKVSubtitlesParsingUtil.ConvertAllSubtitlesInFolder(PropertiesUtil.getProperties("config/application").getProperty("mkv.subtitles.ass.folder.url")));
		
		System.out.println("搜索关键字：寺庙");
		for(MKVSubtitlesFileBean subtitlesFileBean: luceneFTRDao.searchByTerm("subtitle_itemContents", "寺庙", 100)){
			System.out.println("文件名："+subtitlesFileBean.getSrtFileName()+"|字幕内容："+subtitlesFileBean.getMkvSubtitlesBeans());
		}
		
		System.out.println();
		return mv;
	}
	
	@RequestMapping(value = {"/uploadFile"})
	public ModelAndView uploadFile(@RequestParam MultipartFile file,ModelAndView mv) {
		if (file.isEmpty()) {
			return mv;
		}
		try {
			file.transferTo(new File("create"+file.getName()));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("/html/home");
	}
	
}
