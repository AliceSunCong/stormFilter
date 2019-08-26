package cn.scc.storm.conf;

import cn.scc.storm.util.MessageScheme;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;

import java.util.Arrays;


public class KafkaSpoutConf {

    /**
     * Network 配置
     *
     * @return
     */
    public static SpoutConfig getNetworkKafkaSpout() {
        SpoutConfig spoutConfig = new SpoutConfig(
                new ZkHosts(ConfigProperties.getProperty("zk.hosts")),
                NetworkProperties.getProperty("storm.kafka.topic.in"),
                NetworkProperties.getProperty("storm.zookeeper.root"),
                NetworkProperties.getProperty("storm.kafka.groupId"));
        spoutConfig.zkServers = Arrays.asList(ConfigProperties.getProperty("zk.server"));
        spoutConfig.zkPort = Integer.valueOf(ConfigProperties.getProperty("zk.port"));
        //MessageScheme解码kafka数据
        spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());
        //是否强制从Kafka中offset最小的开始读起
        spoutConfig.ignoreZkOffsets = false;
        //如果所请求的offset对应的消息在Kafka中不存在，是否使用startOffsetTime
        spoutConfig.useStartOffsetTimeIfOffsetOutOfRange = true;
        //从何时的offset时间开始读，默认为最旧的offset
        spoutConfig.startOffsetTime = kafka.api.OffsetRequest.EarliestTime();
        return spoutConfig;
    }
}
