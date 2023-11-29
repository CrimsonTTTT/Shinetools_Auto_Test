package util;

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
        System.out.println("待计算的flaot str " + numberString);

        // Check if the number is an integer
        if (numberString.indexOf('.') == -1 || numberString.equals("1.0") || numberString.equals("1") ) {
            return 0;  // No decimal places
        }
        // Count the number of decimal places
        int result = numberString.length() - numberString.indexOf('.') - 1;
        System.out.println("; 计算结果："+result);
        return result;
    }
}
