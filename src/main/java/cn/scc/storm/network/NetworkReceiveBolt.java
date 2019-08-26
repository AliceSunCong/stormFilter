package cn.scc.storm.network;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author SunCongCong
 * @date 2019/8/26 14:04
 * @intro 接收 network 服务器信息
 */
public class NetworkReceiveBolt implements IBasicBolt {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkReceiveBolt.class);

    @Override
    public void prepare(Map map, TopologyContext topologyContext) {

    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        if (tuple != null) {
            try {
                String message = tuple.getString(0);
            }catch (Exception e){
                LOGGER.error("---------接收信息失败---------",e);
            }
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("message"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
