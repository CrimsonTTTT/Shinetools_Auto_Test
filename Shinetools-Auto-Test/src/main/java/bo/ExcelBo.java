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

    public double testSN;           // 测试序号

    public List<String> path;       // 功能路径

    public String type;             // 设置项类型

    public double rangeHigh;        // 设置项为输入型时，范围上限

    public double rangeLow;         // 设置项为输入型时，范围下限

    public String selectValue;      // 设置项为Select时，选项值

    public String result;           // 测试结果

    public String remark;           // 备注

    public int registerType;        // 寄存器类型03 04 06

    public int register;            // 寄存器地址

    public int registerLength;      // 寄存器长度

    public float accuracy;          // 设置项精度

    public int runIgnore = 0;       // 是否忽略执行
}
