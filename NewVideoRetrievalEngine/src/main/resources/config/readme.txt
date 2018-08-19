未来可使用分布式文件存储系统，比如hadoop
Lucene部分可以使用 ElasticSearch 来替代。

待修改项：
	--1、时间工具类因为 Date 1970-1-1 8:00 的问题导致返回的毫秒数为负数。暂时使用public static final long START_DATE_IN_MILLISECONDS = -28800000L;来进行转换。
	2、上传文件有大小限制，几十G的文件如何上传。
















