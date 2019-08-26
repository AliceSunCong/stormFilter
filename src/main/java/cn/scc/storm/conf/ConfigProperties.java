package cn.scc.storm.conf;

import cn.scc.storm.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @ClassName ConfigProperties
 * @Version 1.0
 */
public class ConfigProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigProperties.class);

    public static final Properties properties = PropertiesUtils.getProperties("config.properties");

    public static String getProperty(final String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static boolean getStormLogFlag() {
        String stormLog = getProperty("storm.log");
        boolean stormLogFlag = false;
        try {
            stormLogFlag = Boolean.parseBoolean(stormLog);
        } catch (Exception e) {
            LOGGER.error("获取storm debug标志异常，默认false");
            LOGGER.error(e.getMessage(), e);
        }
        return stormLogFlag;
    }
}
