package cn.scc.storm.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/7/18.
 */
public class PropertiesUtils {
    public static Properties getProperties(String propertiesName) {
        Properties p = new Properties();
        try {
            InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesName);
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return p;
    }

    public static Properties getProperties(String propertiesName, String charsetName) {
        Properties p = new Properties();
        try {
            InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesName);
            p.load(new InputStreamReader(inputStream, charsetName));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return p;
    }
}
