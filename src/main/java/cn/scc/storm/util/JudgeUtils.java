package cn.scc.storm.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式判断工具类
 */
public class JudgeUtils {
    /**
     * Ip 格式判断
     * @param addr
     * @return
     */
        public static boolean isIP(String addr) {
            if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
                return false;
            }
            /**
             * 判断IP格式和范围
             */
            String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

            Pattern pat = Pattern.compile(rexp);

            Matcher mat = pat.matcher(addr);

            boolean ipAddress = mat.find();

            return ipAddress;

    }

    /**
     * 判断数据是否是0/1
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-1]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断时间格式 格式必须为“yyyy-MM-dd HH:mm:ss”
     * 2004-2-30 是无效的
     * 2003-2-29 是无效的
     * @param sDate
     * @return
     */
    public static boolean isLegalDate(String sDate) {

        if (StringUtils.isEmpty(sDate)) {
            return false;
        }

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(sDate);
            return sDate.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param args
     * @Title : main
     * @Type : JudgeUtils
     * @Description : IP可能的范围是0-255.0-255.0-255.0-255
     */
    public static void main(String[] args) {
        /**
         * 符合IP地址的范围
         */
        //String oneAddress = "10.127.30.45";
        /**
         * 符合IP地址的长度范围但是不符合格式
         */
        //String twoAddress = "127.30.45";
        /**
         * 不符合IP地址的长度范围
         */
        //String threeAddress = "7.0.4";
        /**
         * 符合IP地址的长度范围但是不符合IP取值范围
         */
        //String fourAddress = "255.255.255.2567";

        String sdate = "2019-01-10 17:07:03";
        System.out.println(isLegalDate(sdate));;

    }

}
