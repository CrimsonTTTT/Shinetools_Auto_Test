package bo;

import lombok.Data;

/**
 * @Author Graycat.
 * @CreateTime 2023/12/12 16:50
 * @Descripe
 */
@Data
public class SingleReadDataBo {

    private int testSN;             // ����id

    private String name;            // �������ݵ�����

    private int registerType;        // �Ĵ�������03 04 06

    private int register;            // �Ĵ�����ַ

    private int registerLength;      // �Ĵ�������

    private float accuracy;          // �������

    private String elementId;        // Ѱ��Ԫ�ض�λ��id

    private int runIgnore = 0;      // ���з�

    private String remark;          // ��ע

    private String result;          // ���Խ��


}
