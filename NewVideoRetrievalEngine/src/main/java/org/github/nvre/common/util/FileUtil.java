package org.github.nvre.common.util;

import java.io.File;

public class FileUtil {

	public static File getDirNoExistsMkdirs(String dirUrl) {
		File dir = new File(dirUrl);
		if(!dir.exists()){  
			dir.mkdirs();  
		}
		return dir;
	}
	
	
	//判断文件后缀名
	public static boolean determineFileSuffix(File file,String suffix){
		return file.getName().endsWith(suffix);
	}

	
	
}
