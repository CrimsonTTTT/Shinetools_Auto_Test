package util;

import config.LoggerLoad;

import java.util.Map;

/**
 * @Author Graycat.
 * @CreateTime 2023/12/11 14:23
 * @Descripe
 */
public class StrUtils {


    /**
     * Description:  将数字转成对应的字符串。仅限16位的寄存器机制
     * @return java.lang.String
     * @author Graycat. 2023/12/11 14:28
     */
    public static String numbersParseToStr(int num) {
        String binaryNums = decimalToBinary(num);

        // 提取前八位+后八位
        String num1 = binaryNums.substring(0,8);
        String num2 = binaryNums.substring(8,16);

        // 将二进制转换为整数
        int intValue1 = Integer.parseInt(num1, 2);
        int intValue2 = Integer.parseInt(num2, 2);

        LoggerLoad.debug("转换成二进制值为："+  num1 + " " + num2 + "  对应的字符为：" + (char)intValue1 + " " + (char)intValue2);

        // 将整数转换为字符串
        return "" + (char)intValue1 + (char)intValue2;
    }

    /**
     *  将十进制数转换为16位的二进制数，不足16位的在前面补0  */
    private static String decimalToBinary(int decimalNumber) {
        StringBuilder binaryRepresentation = new StringBuilder();
        int targetBitLength = 16;
        // 反复除以2，记录余数
        while (decimalNumber > 0) {
            int remainder = decimalNumber % 2;
            binaryRepresentation.insert(0, remainder); // 将余数插入到字符串的最前面
            decimalNumber /= 2;
        }

        // 如果原始十进制数是0，直接返回 "0"
        if (binaryRepresentation.length() == 0) {
            return "0";
        }

        // 在高位补零
        while (binaryRepresentation.length() < targetBitLength) {
            binaryRepresentation.insert(0, '0');
        }

        return binaryRepresentation.toString();
    }

    /**   取反加一得到原码    */
    private static String negate(String binaryNumber) {
        StringBuilder result = new StringBuilder();

        for (char bit : binaryNumber.toCharArray()) {
            result.append((bit == '0') ? '1' : '0');
        }

        // 加一
        int carry = 1;
        for (int i = binaryNumber.length() - 1; i >= 0; i--) {
            char originalBit = result.charAt(i);
            char newBit = (char) ('0' + ((originalBit - '0' + carry) % 2));
            carry = (originalBit - '0' + carry) / 2;
            result.setCharAt(i, newBit);
        }

        return result.toString();
    }

}
