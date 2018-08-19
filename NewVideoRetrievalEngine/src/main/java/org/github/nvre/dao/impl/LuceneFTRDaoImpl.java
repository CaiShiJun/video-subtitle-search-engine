package org.github.nvre.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.github.nvre.bean.MKVSubtitlesBean;
import org.github.nvre.bean.MKVSubtitlesFileBean;
import org.github.nvre.common.util.FileUtil;
import org.github.nvre.common.util.PropertiesUtil;
import org.github.nvre.dao.LuceneFTRDao;
import org.springframework.stereotype.Repository;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

@Repository
public class LuceneFTRDaoImpl implements LuceneFTRDao {

	Directory directory = null;
	IndexWriter writer = null;
	private IndexReader reader;

	private String mkvSubtitlesSrtFolderUrl;

	private String mkvSubtitlesAssFolderUrl;

	private String luceneIndexFolderUrl;

	//使用application.properties中的lucene.index.folder.url参数作为lucene索引的存储路径
	public LuceneFTRDaoImpl() throws Exception {
		// init
		Properties applicationProperties = PropertiesUtil.getProperties("config/application");
		this.mkvSubtitlesSrtFolderUrl = applicationProperties.getProperty("mkv.subtitles.srt.folder.url");
		FileUtil.getDirNoExistsMkdirs(mkvSubtitlesSrtFolderUrl);
		this.mkvSubtitlesAssFolderUrl = applicationProperties.getProperty("mkv.subtitles.ass.folder.url");
		FileUtil.getDirNoExistsMkdirs(mkvSubtitlesAssFolderUrl);
		this.luceneIndexFolderUrl = applicationProperties.getProperty("lucene.index.folder.url");
		File file = FileUtil.getDirNoExistsMkdirs(luceneIndexFolderUrl);

		this.directory = FSDirectory.open(file);

		// 创建IndexWriter
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_35, new MMSegAnalyzer()); // 创建一个IndexWriterConfig索引配置对象，使用的分词器为new StandardAnalyzer()标准分词器。lucene
																												// 3.5版本需要写明版本号Version.LUCENE_35
		writer = new IndexWriter(directory, indexWriterConfig); // 参数为(Directory arg0, IndexWriterConfig arg1)
	}
	
	@Override
	public void createIndexFromMKVSubtitlesFileBeanList(List<MKVSubtitlesFileBean> mkvSubtitlesFileBeanList) throws Exception {

		// 创建Document对象
		Document document = null; // 声明一个Document文档变量

		// 为Document添加Field。Document文档中的每一个属性就叫这个文档的Field（标题、大小、路径、内容）。
		for (MKVSubtitlesFileBean mkvSubtitlesFileBean : mkvSubtitlesFileBeanList) {
			for (MKVSubtitlesBean mkvSubtitlesBean : mkvSubtitlesFileBean.getMkvSubtitlesBeans()) {

				document = new Document();

				document.add(new NumericField("file_timeStamp", Field.Store.YES, true).setLongValue(mkvSubtitlesFileBean.getTimeStamp())); // 为数字加索引 new NumericField("Field属性名称", Field.Store.YES是否将值保存在索引中 , true
																																			// 是否添加索引).setIntValue(attachs[i] 设置数字的类型和值 )
				document.add(new Field("file_srtFileName", mkvSubtitlesFileBean.getSrtFileName(), Field.Store.YES, Field.Index.ANALYZED)); // 将文件名保存在索引中，并且使用分词

				document.add(new NumericField("subtitle_startTime", Field.Store.YES, true).setLongValue(mkvSubtitlesBean.getStartTime())); // 为数字加索引 new NumericField("Field属性名称", Field.Store.YES是否将值保存在索引中 , true
																																			// 是否添加索引).setIntValue(attachs[i] 设置数字的类型和值 )
				document.add(new NumericField("subtitle_endTime", Field.Store.YES, true).setLongValue(mkvSubtitlesBean.getEndTime())); // 为数字加索引 new NumericField("Field属性名称", Field.Store.YES是否将值保存在索引中 , true
																																		// 是否添加索引).setIntValue(attachs[i] 设置数字的类型和值 )
				document.add(new Field("subtitle_itemContents", mkvSubtitlesBean.getItemContents(), Field.Store.YES, Field.Index.ANALYZED)); // 正常情况 内容 一般不会被存储。使用分词。
				
				System.out.println(mkvSubtitlesBean);
				writer.addDocument(document);
			}

		}
		writer.commit();
	}


	@Override
	public void deleteAllIndex() throws Exception {
		// （1）、删除全部索引(包括回收站) 。 writer.deleteAll();
		writer.deleteAll();
		writer.commit();
	}

	@Override
	public IndexSearcher getSearcher() throws Exception {
		if (reader == null) {
			reader = IndexReader.open(directory);
		} else {
			IndexReader tr = IndexReader.openIfChanged(reader);
			if (tr != null) {
				reader.close();
				reader = tr;
			}
		}
		return new IndexSearcher(reader);
	}

	/**
	 *
	 * @Title: searchByTerm
	 * @Description: 精确匹配查询
	 * @param field 字段名称
	 * @param name 字段值
	 * @param num 最大查询结果返回数量
	 * @throws 
	 * @author Cai ShiJun
	 * @date 2018年2月28日 下午7:49:54
	 */
	@Override
	public List<MKVSubtitlesFileBean> searchByTerm(String field, String keyword, int num) throws Exception{
		List<MKVSubtitlesFileBean> mkvSubtitlesFileBeanList = new ArrayList<MKVSubtitlesFileBean>();

		// 1、获取search
		IndexSearcher searcher = getSearcher();
		// 2、创建Query
		Query query = new TermQuery(new Term(field, keyword)); // newTermQuery(new Term(字段名称,字段值))
		// 3、创建 TopDocs
		TopDocs tds = searcher.search(query, num); // search(Queryquery, num最大查询结果返回数量)
		System.out.println("一共查询了：" + tds.totalHits); // tds.totalHits 一共查询了多少条。 大于等于 num最大查询结果返回数量。
		
		for (ScoreDoc sd : tds.scoreDocs) {
			Document document = searcher.doc(sd.doc);
			
			MKVSubtitlesFileBean mkvSubtitlesFileBean = new MKVSubtitlesFileBean();
			mkvSubtitlesFileBean.setPrimaryKey(document.get("file_timeStamp")+document.get("file_srtFileName"));
			mkvSubtitlesFileBean.setTimeStamp(Long.valueOf(document.get("file_timeStamp")));
			mkvSubtitlesFileBean.setSrtFileName(document.get("file_srtFileName"));

			MKVSubtitlesBean mkvSubtitlesBean = new MKVSubtitlesBean();
			mkvSubtitlesBean.setStartTime(Long.valueOf(document.get("subtitle_startTime")));
			mkvSubtitlesBean.setEndTime(Long.valueOf(document.get("subtitle_endTime")));
			mkvSubtitlesBean.setItemContents(document.get("subtitle_itemContents"));
			List<MKVSubtitlesBean> mkvSubtitlesBeanList = new ArrayList<MKVSubtitlesBean>();
			
			mkvSubtitlesBeanList.add(mkvSubtitlesBean);
			mkvSubtitlesFileBean.setMkvSubtitlesBeans(mkvSubtitlesBeanList);
			
			mkvSubtitlesFileBeanList.add(mkvSubtitlesFileBean);

		} 
		// 4、关闭 IndexSearcher
		searcher.close();
		return mkvSubtitlesFileBeanList;
	}

}
