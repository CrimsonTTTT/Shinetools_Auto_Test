package util;

import config.LoggerLoad;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @Author Graycat.
 * @CreateTime 2023/11/24 18:01
 * @Descripe
 */
public class MathUtils {

    /**
     * 获取随机数
     * */
    public static double generateRandomNumber(double minValue, double maxValue) {
        Random rand = new Random();
        return minValue + (maxValue - minValue) * rand.nextDouble();
    }
    /**
     * 格式化数字位数, 跟随机数一起用的，用来保留几位小数
     * */
    public static String formatDecimal(double value, int decimalPlaces) {
        String pattern = "0.";
        for (int i = 0; i < decimalPlaces; i++) {
            pattern += "0";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    /**
     * 计算浮点数存在多少位小数
     * */
    public static int countDecimalPlaces(float number) {
        String numberString = Float.toString(number);

        // Check if the number is an integer
        if (numberString.indexOf('.') == -1 || numberString.equals("1.0") || numberString.equals("1") ) {
            LoggerLoad.debug("待计算小数位的float str " + numberString + "; 计算结果：0" );
            return 0;  // No decimal places
        }
        // Count the number of decimal places
        int result = numberString.length() - numberString.indexOf('.') - 1;
        LoggerLoad.info("待计算小数位的float str:" + numberString + "; 计算结果：" + result);
        return result;
    }

    /**
     * 对比两个double数值是否相等
     * */
    public static boolean compareDoubleEquals(double a, double b){
        // 定义一个精度范围，根据需求调整
        double epsilon = 0.0001;
        // 使用精度范围进行比较
        return Math.abs(a - b) < epsilon;
    }
}
