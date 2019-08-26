package cn.scc.storm.util;

import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

/**
 * 解码KafkaSpout数据，生成Storm内部传递数据
 */
public class MessageScheme implements Scheme {

    public List<Object> deserialize(ByteBuffer ser) {
        String msg = byteBufferToString(ser);
        return new Values(msg);
    }

    public Fields getOutputFields() {
        // TODO Auto-generated method stub
        return new Fields("msg");
    }

    /**
     * 将kafka出来的数据转换成字符串
     * @param buffer
     * @return
     */
    public static String byteBufferToString(ByteBuffer buffer) {
        CharBuffer charBuffer = null;
        try {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}

