package bo;

import lombok.Data;

import java.util.List;

/**
 * @Author Graycat.
 * @CreateTime 2023/9/23 11:15
 * @Descripe
 */
@Data
public class ExcelBo {

    public double testSN;           // �������

    public List<String> path;       // ����·��

    public String type;             // ����������

    public double rangeHigh;        // ������Ϊ������ʱ����Χ����

    public double rangeLow;         // ������Ϊ������ʱ����Χ����

    public String selectValue;      // ������ΪSelectʱ��ѡ��ֵ

    public String result;           // ���Խ��

    public String remark;           // ��ע

    public int registerType;        // �Ĵ�������03 04 06

    public int register;            // �Ĵ�����ַ

    public int registerLength;      // �Ĵ�������

    public float accuracy;          // �������

    public int runIgnore = 0;       // �Ƿ����ִ��
}
