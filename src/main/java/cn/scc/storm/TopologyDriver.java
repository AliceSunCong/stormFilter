package cn.scc.storm;

import cn.scc.storm.conf.ConfigProperties;
import cn.scc.storm.conf.KafkaSpoutConf;
import cn.scc.storm.conf.NetworkProperties;
import cn.scc.storm.network.NetworkFormatBolt;
import cn.scc.storm.network.NetworkReceiveBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.topology.TopologyBuilder;

/**
 * @author SunCongCong
 * @date 2019/8/26 13:53
 * @intro 服务器数据storm启动类 使Kafka、strom、Kafka进行串联起来
 */
@SuppressWarnings("deprecation")
public class TopologyDriver {

    public static final String STORM_NAME = "networkStorm";

    public static void main(String[] args) throws Exception {

        Config config = new Config();
        config.setDebug(ConfigProperties.getStormLogFlag());
        config.setNumWorkers(2);
        StormTopology stormTopology = buildTopology();

        if (args != null && args.length > 0) {
            //集群
            StormSubmitter.submitTopology(args[0], config, buildTopology());
        } else {
            //本地模式
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology(STORM_NAME, config, stormTopology);
        }

    }

    public static StormTopology buildTopology() {
        //1、准备任务信息
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        //定义输出为string类型
        topologyBuilder.setSpout("network-input",
                new KafkaSpout(KafkaSpoutConf.getNetworkKafkaSpout()), 2);
        //过滤信息
        topologyBuilder.setBolt("network-filter", new NetworkReceiveBolt(), 2).shuffleGrouping("network-input");
        //切分
        topologyBuilder.setBolt("network-format", new NetworkFormatBolt(), 2).shuffleGrouping("network-filter");
        //将 format 主题信息发给kafkastorm
        topologyBuilder.setBolt("network-Output",
                new KafkaBolt<String, Integer>()
                        .withProducerProperties(ConfigProperties.properties)
                        .withTopicSelector(
                                new DefaultTopicSelector(NetworkProperties.getProperty("storm.kafka.topic.out"))
                        )
                        .withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper()))
                .shuffleGrouping("network-format");
        return topologyBuilder.createTopology();
    }
}
