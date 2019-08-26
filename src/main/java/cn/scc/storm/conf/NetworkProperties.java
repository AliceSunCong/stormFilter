package cn.scc.storm.conf;


import cn.scc.storm.util.PropertiesUtils;

import java.util.Properties;

/**
 * @ClassName ConfigProperties
 * @Date 2019/1/22 18:52
 * @Version 1.0
 */
public class NetworkProperties {

    private static final Properties properties = PropertiesUtils.getProperties("network.properties");

    public static String getProperty(final String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
