package org.github.nvre.dao;

import java.util.List;

import org.apache.lucene.search.IndexSearcher;
import org.github.nvre.bean.MKVSubtitlesFileBean;

/**
 * 
* @ClassName: LuceneFTRDao 
* @Description: Lucene全文检索Dao
* @author Cai ShiJun 
* @date 2018年5月31日 下午2:06:12 
*
 */
public interface LuceneFTRDao {

	// MKVSubtitlesFileBean 创建索引
	void createIndexFromMKVSubtitlesFileBeanList(List<MKVSubtitlesFileBean> mkvSubtitlesFileBeanList) throws Exception;
	
	// 删除所有索引
	public void deleteAllIndex() throws Exception;
	
	public IndexSearcher getSearcher() throws Exception;
	
	public List<MKVSubtitlesFileBean> searchByTerm(String field, String keyword, int num) throws Exception;
	
	/*
	// MKVSubtitlesFile 创建索引
	void createIndexFromMKVSubtitlesFile() throws Exception;
	
	//为一整个文件夹中的所有特定格式的文件创建索引
	void createIndexFromFolderAllFile() throws Exception;
	
	void createIndexFromFolderAllFile(List<String> mkvSubtitlesFolderUrls) throws Exception;
	*/
	
	//
}
