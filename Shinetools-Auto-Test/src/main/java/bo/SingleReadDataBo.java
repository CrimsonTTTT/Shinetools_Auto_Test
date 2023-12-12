package bo;

import lombok.Data;

/**
 * @Author Graycat.
 * @CreateTime 2023/12/12 16:50
 * @Descripe
 */
@Data
public class SingleReadDataBo {

    private int testSN;             // 用例id

    private String name;            // 测试数据点名称

    private int registerType;        // 寄存器类型03 04 06

    private int register;            // 寄存器地址

    private int registerLength;      // 寄存器长度

    private float accuracy;          // 设置项精度

    private String elementId;        // 寻找元素定位用id

    private int runIgnore = 0;      // 运行否

    private String remark;          // 备注

    private String result;          // 测试结果


}
