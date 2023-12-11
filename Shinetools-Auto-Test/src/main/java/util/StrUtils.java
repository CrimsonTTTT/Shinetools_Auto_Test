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
     * Description:  ������ת�ɶ�Ӧ���ַ���������16λ�ļĴ�������
     * @return java.lang.String
     * @author Graycat. 2023/12/11 14:28
     */
    public static String numbersParseToStr(int num) {
        String binaryNums = decimalToBinary(num);

        // ��ȡǰ��λ+���λ
        String num1 = binaryNums.substring(0,8);
        String num2 = binaryNums.substring(8,16);

        // ��������ת��Ϊ����
        int intValue1 = Integer.parseInt(num1, 2);
        int intValue2 = Integer.parseInt(num2, 2);

        LoggerLoad.debug("ת���ɶ�����ֵΪ��"+  num1 + " " + num2 + "  ��Ӧ���ַ�Ϊ��" + (char)intValue1 + " " + (char)intValue2);

        // ������ת��Ϊ�ַ���
        return "" + (char)intValue1 + (char)intValue2;
    }

    /**
     *  ��ʮ������ת��Ϊ16λ�Ķ�������������16λ����ǰ�油0  */
    private static String decimalToBinary(int decimalNumber) {
        StringBuilder binaryRepresentation = new StringBuilder();
        int targetBitLength = 16;
        // ��������2����¼����
        while (decimalNumber > 0) {
            int remainder = decimalNumber % 2;
            binaryRepresentation.insert(0, remainder); // ���������뵽�ַ�������ǰ��
            decimalNumber /= 2;
        }

        // ���ԭʼʮ��������0��ֱ�ӷ��� "0"
        if (binaryRepresentation.length() == 0) {
            return "0";
        }

        // �ڸ�λ����
        while (binaryRepresentation.length() < targetBitLength) {
            binaryRepresentation.insert(0, '0');
        }

        return binaryRepresentation.toString();
    }

    /**   ȡ����һ�õ�ԭ��    */
    private static String negate(String binaryNumber) {
        StringBuilder result = new StringBuilder();

        for (char bit : binaryNumber.toCharArray()) {
            result.append((bit == '0') ? '1' : '0');
        }

        // ��һ
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
