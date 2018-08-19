package org.github.nvre.common.util;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesUtil{

	/**
	 * 
	* @Title: getProperties 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param propertiesFileName
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年6月1日 上午9:09:37
	 */
    public static Properties getProperties(String propertiesFileName){
        Properties properties = new Properties();
        String propertiesName = propertiesFileName+".properties";
        InputStream in = null;
        try {
            ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
            URL url = classLoader.getResource(propertiesName);
            File file = new File(url.getFile());
            in = new BufferedInputStream(new FileInputStream(file));
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}