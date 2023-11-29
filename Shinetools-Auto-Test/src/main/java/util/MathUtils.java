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
     * ��ȡ�����
     * */
    public static double generateRandomNumber(double minValue, double maxValue) {
        Random rand = new Random();
        return minValue + (maxValue - minValue) * rand.nextDouble();
    }
    /**
     * ��ʽ������λ��, �������һ���õģ�����������λС��
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
     * ���㸡�������ڶ���λС��
     * */
    public static int countDecimalPlaces(float number) {
        String numberString = Float.toString(number);
        System.out.println("�������flaot str " + numberString);

        // Check if the number is an integer
        if (numberString.indexOf('.') == -1 || numberString.equals("1.0") || numberString.equals("1") ) {
            return 0;  // No decimal places
        }
        // Count the number of decimal places
        int result = numberString.length() - numberString.indexOf('.') - 1;
        System.out.println("; ��������"+result);
        return result;
    }
}
