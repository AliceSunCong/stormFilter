package cn.scc.storm.network;
import	java.util.Date;

import cn.scc.storm.util.DateUtils;
import cn.scc.storm.util.FileUtils;
import cn.scc.storm.util.JudgeUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * @author SunCongCong
 * @date 2019/8/26 14:05
 * @intro * 切分,过滤network 信息格式
 */
public class NetworkFormatBolt extends BaseBasicBolt {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkFormatBolt.class);

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {

        String message = tuple.getStringByField("message");
        LOGGER.info("kafka数据:{}", message);
        try {
            LOGGER.info("------network----start-------");
            JSONObject object = JSONObject.parseObject(message);
            //判断"ip"对应的value格式为ip && " alarmStatus"对应的value为0/1 && strings[2] 格式为时间
            if (JudgeUtils.isNumeric(object.getString("alarmStatus")) && JudgeUtils.isIP(object.getString("ip"))) {
                //3、发送数据
                collector.emit(new Values(message));
            } else {
                LOGGER.error("--------获取ping测数据格式不正确------------");
                saveWrongMessage(message);
            }
        } catch (Exception e) {
            LOGGER.error("------获取数据格式不正确-------", e);
            saveWrongMessage(message);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("message"));
    }
    /**
     * 存入到文件中
     *
     * @param message 错误的数据格式
     */
    private void saveWrongMessage(String message) {
        String fileName = "network-" + DateUtils.getCurrentDate();
        FileUtils.createFile(fileName, DateUtils.getCurrentDateTime() + "------" + message);
    }
}
