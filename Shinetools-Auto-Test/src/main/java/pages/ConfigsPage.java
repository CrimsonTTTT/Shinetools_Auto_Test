package pages;

import bo.ExcelBo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import config.LoggerLoad;
import io.appium.java_client.AppiumDriver;
import lombok.extern.java.Log;
import org.openqa.selenium.*;
import pages.settings.AdvancedSettingPage;
import util.ExcelUtlis;
import util.MathUtils;
import util.StrUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Graycat.
 * @CreateTime 2023/9/26 11:25
 * @Descripe
 */
public class ConfigsPage extends BasePage{

    // 设置列表界面区域，顶部的名称也在这里面。所以实际设置项坐标从1开始
    By title_area = By.id("com.growatt.shinetools:id/tv_title");
    By select_value_area = By.className("android.widget.ListView");
    By select_value = By.className("android.widget.TextView");
    By top_title = By.id("com.growatt.shinetools:id/toolbar");
    By load_icon = By.id("com.growatt.shinetools:id/avi");

    By input_range_area = By.id("com.growatt.shinetools:id/tv_sub_title");
    By input_value_area = By.id("com.growatt.shinetools:id/et_input");
    By input_confirm_btn = By.id("com.growatt.shinetools:id/tv_button_confirm");

    By input_result = By.xpath("//android.widget.Toast");
    By switch_configure_area = By.id("com.growatt.shinetools:id/rv_setting");
    By switch_btn = By.id("com.growatt.shinetools:id/sw_switch");
    By switch_text = By.id("com.growatt.shinetools:id/tv_title");

    By text_text = By.id("com.growatt.shinetools:id/tv_value");
    By text_area = By.className("android.view.ViewGroup");
    By config_name_area = By.id("com.growatt.shinetools:id/tv_title");


    public ConfigsPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * 对比当前设置项页面的设置项名称与excel是否一致，严格要求名称、顺序
     * */
    public void compareConfigText(List<WebElement> actualList, List<ExcelBo> expectList ){

    }

    /**
     * 前往某一设置项的页面
     * */
    public ConfigsPage toConfigPage( List<String> configPath) throws InterruptedException {
        int i = 1;
        try {
            // 设置列表界面区域，顶部的名称也在这里面。所以实际设置项坐标从1开始
            for (i = 1; i < configPath.size(); i++) {
                List<WebElement> actualConfigList = waitVisibilityWithAll(title_area);
                // 如果遇到结束符，就结束。否则就点进去
                if (configPath.equals("/")) {
                    break;
                }
                if (configPath.get(i + 1).equals("/")) {
                    break;
                }
                for (WebElement configItem : actualConfigList) {
                    if (configItem.getText().equals(configPath.get(i))) {
                        configItem.click();
                        break;
                    }
                }
            }
        }catch (StaleElementReferenceException e){
            Thread.sleep(2*1000);
            for (int j = i; i < configPath.size(); i++) {
                List<WebElement> actualConfigList = waitPresenceOfAllElements(title_area);
                // 如果遇到结束符，就结束。否则就点进去
                if (configPath.equals("/")) {
                    break;
                }
                if (configPath.get(i + 1).equals("/")) {
                    break;
                }
                for (WebElement configItem : actualConfigList) {
                    if (configItem.getText().equals(configPath.get(i))) {
                        configItem.click();
                        break;
                    }
                }
            }
        }
        return this;
    }

    /**
     * Description:  从设置页面寻找设置项点击
     */
    public void clickConfigItem( String configName ){
        List<WebElement> actualConfigList = waitVisibilityWithAll(title_area);
        for (WebElement item : actualConfigList){
            if (item.getText().equals(configName)){
                item.click();
                break;
            }
        }
        LoggerLoad.debug("点击设置项： " + configName);
    }

    /**
     * 从某一设置项返回到首页
     * */
    public DeviceHomePage toDeviceHomePage( List<String> configPath ){
        for ( int i = 1; i < configPath.size() ; i++ ){
            if (!configPath.get(i).equals("/")){
                back(top_title);
            }
        }
        return new DeviceHomePage(appiumDriver);
    }

    /**
     * Description:  单选类型设置项测试方法；各设置类型设置一遍，并读取对应的寄存器数值; 最后把结果保存到excel
     * @param excelRow bo.ExcelBo
     * @return bo.ExcelBo
     * @author Graycat. 2023/12/11 14:12
     */
    public ExcelBo testSelectConfig( ExcelBo excelRow ) throws InterruptedException {
        return  compareSelectOption(excelRow);
    }

    public ExcelBo compareSelectOption( ExcelBo row ) throws InterruptedException {
        // 获取设置项名称并点击
        String configName = null;
        for (String item: row.getPath()){
            if (item.equals("/")){
                break;
            }
            configName = item;
        }
        clickConfigItem(configName);

        List<String> expectSelectList = new ArrayList<>();

        String[] strings = row.getSelectValue().split("、");

        // 获取预期选项值
        for ( int i = 0; i < strings.length; i++ ){
            expectSelectList.add(strings[i]);
        }
        // 获取实际页面选项值
        List<WebElement> actualSelectArea = waitVisibilityWithAll(select_value_area);
        List<WebElement> actualSelectList = actualSelectArea.get(0).findElements(select_value);
        List<String> actualSelectStrList = new ArrayList<>();
        for (WebElement item : actualSelectList){
            actualSelectStrList.add(item.getText());
        }

        LoggerLoad.info("页面实际选项值" + actualSelectStrList);
        LoggerLoad.info("页面预期选项值" + expectSelectList);

        int resultFlag = 1;
        // 判断实际页面的选项值是否与预期一致，若一致，进入单选项测试逻辑：逐项设置并返回到高级设置里读取对应的寄存器
        AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
        if( expectSelectList.equals(actualSelectStrList) ){
            LoggerLoad.info("进入单选项测试逻辑^_^");
            DeviceHomePage deviceHomePage = new DeviceHomePage(appiumDriver);
            int itemIndex = 0 ; // 按顺序比对下标，默认从0开始

            for(int i = 0; i < actualSelectStrList.size(); i++){
                actualSelectArea = waitVisibilityWithAll(select_value_area);
                actualSelectList = actualSelectArea.get(0).findElements(select_value);
                LoggerLoad.info("点击选择：" + actualSelectList.get(i).getText());
                actualSelectList.get(i).click();

                backToHomeWithLeftTopBtn(row.getPath());
                deviceHomePage.intoConfig("高级设置");
                // 读取对应寄存器值
                Map result = advancedSettingPage.readRegisterValue(row.getRegisterType(),row.getRegister(),row.getRegisterLength());

                advancedSettingPage.back(By.id("乱七八糟的."));
                // 对比itemIndex与result的值，若不一致，说明设置项设置之后对应的寄存器值为修改；直接返回首页
                int actualRegisterValue = (Integer) result.get(row.getRegister());
                if ( i != actualRegisterValue){
                    resultFlag = 0;
                    break;
                }
                itemIndex++;
                // 上次写到这里。。。。。。。。。。。。
                deviceHomePage.intoConfig(row.getPath().get(0));
                toConfigPage(row.getPath());
                clickConfigItem(configName);
            }
            // 跑完所有设置项进入，代表ok，flag = 1
            if ( resultFlag == 1 ) {
                row.setResult("Pass");
                back(title_area);
                return row;
            }
            row.setResult("修改后寄存器数值与设置不一致（未排除设置后返回失败的情况！）");
            back(title_area);
            return row;
        }

        row.setResult("预期选项值与实际选项不一致");
        back(title_area);
        return row;
    }


    /**
     * Description:  从设置页面返回设备首页，即连接后的那个页面
     */
    public void backToHome(List<String> path) {
        LoggerLoad.info("进入到返回操作"+path);
        if (path.get(1).equals("/")){
            back(top_title);
        }
        for (int i = 1; i<path.size(); i++){
            if( !path.get(i).equals("/") ){
                back(top_title);
                LoggerLoad.debug("返回操作+1");
            }
        }
    }

    public void backToHomeWithLeftTopBtn(List<String> path) throws InterruptedException {
        LoggerLoad.info("进入到返回操作(使用左上角页面返回按钮)"+path);
        if (path.get(1).equals("/")){
            clickBackBtn();
        }
        for (int i = 1; i<path.size(); i++){
            if( !path.get(i).equals("/") ){
                clickBackBtn();
                LoggerLoad.debug("返回操作+1");
            }
        }
    }


    /**
     * Description:  输入型设置项测试方法，策略：0.范围是否一致  1. 范围值内最小精度  2.范围值最小值-最小精度  3.范围值最大值+最大精度  4.最大值、最小值  5.特殊字符（待确认）
     * @param row bo.ExcelBo
     * @return void
     * @author Graycat. 2023/11/23 15:17
     */
    public void testInputConfig( ExcelBo row ) throws InterruptedException {
        LoggerLoad.debug("进入设置项测试方法，设置项："+row);
        // 获取设置项名称并点击
        String configName = null;
        for (String item: row.getPath()){
            if (item.equals("/")){
                break;
            }
            configName = item;
        }
        clickConfigItem(configName);

        // 获取预期范围与实际页面展示范围。获取失败则表明未按照标准格式展示
        double expectHighLimit = 0, expectLowLimit = 0, actualHighLimit = 0, actualLowLimit = 0;
        expectLowLimit = row.getRangeLow();
        expectHighLimit = row.getRangeHigh();

        String rangeStr = readText(input_range_area);
        List<String> actualRangeList = dealActualRangeStr(configName,rangeStr);
        actualHighLimit = Double.parseDouble(actualRangeList.get(1));
        actualLowLimit = Double.parseDouble(actualRangeList.get(0));
        LoggerLoad.info("预期范围，下限：" + expectLowLimit + "  上限：" + expectHighLimit);

        if ( (expectHighLimit != actualHighLimit) || (expectLowLimit != actualLowLimit) ){
            LoggerLoad.info("测试结果：实际范围与预期范围不一致");
            row.setResult("实际范围与预期范围不一致");
            back(By.id("无效id，用来返回到首页"));
            return;
        }

        // 超范围设置
        double testValue_higher = expectHighLimit + row.getAccuracy();
        double testValue_lower = expectLowLimit - row.getAccuracy();
        double testValue_random_middle = MathUtils.generateRandomNumber( testValue_lower,testValue_higher );
        int decimalPlaces = MathUtils.countDecimalPlaces(row.getAccuracy());
        String testValue_random_middle_str = MathUtils.formatDecimal(testValue_random_middle,decimalPlaces);

        LoggerLoad.debug(row.toString());
        LoggerLoad.debug("测试数据：" + testValue_higher + ", " + testValue_lower + ", " + row.getRangeLow() + ", " + row.getRangeHigh() + ", " + testValue_random_middle_str );

        boolean inputResult = inputOutRangeTest( String.valueOf(testValue_higher) );
        clickConfigItem(configName);
        boolean inputResult2 = inputOutRangeTest( String.valueOf(testValue_lower) );
        if (inputResult == true || inputResult2 == true){
            row.setResult("超出范围值可设置！");
            return ;
        }

        // 范围内设置test.
        boolean inputResult3 = inputValueTest( String.valueOf(row.getRangeLow()),row,configName );
        boolean inputResult4 = inputValueTest( String.valueOf(row.getRangeHigh()),row,configName );
        boolean inputResult5 = inputValueTest( testValue_random_middle_str,row,configName );
        if (inputResult3 == false || inputResult4 == false || inputResult5 == false){
            row.setResult("设置数值与寄存器读取数值不一致！");
            return;
        }

        row.setResult("Pass");
    }

    /**
     * 提取实际页面，输入型设置项的范围上下限.   list.get(0) 为下限
    */
    public List<String> dealActualRangeStr( String configName, String rangeStr ){
        LoggerLoad.info("实际页面设置项" + configName + " 范围str为：" + rangeStr);
        // 定义正则表达式
        String regex = "(-?\\d+(?:\\.\\d*)?)~(-?\\d+(?:\\.\\d*)?)";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(rangeStr);

        String firstValue = "", secondValue = "";

        // 查找匹配
        if (matcher.find()) {
            // 获取第一个匹配组的值（0 的值）
            firstValue = matcher.group(1);
            // 获取第二个匹配组的值（2000 的值）
            secondValue = matcher.group(2);
            // 输出结果
            LoggerLoad.debug("解析结果，下限： " + firstValue + " 上限：" + secondValue);
        }
        List<String> result = new ArrayList<>();
        result.add(firstValue);
        result.add(secondValue);

        return  result;
    }

    public boolean inputOutRangeTest( String inputValue ) throws InterruptedException {
        LoggerLoad.info("输入测试数值："+inputValue);
        writeText(input_value_area,inputValue);
        click(input_confirm_btn);

        String result = readToastText(input_result);

        LoggerLoad.info("设置结果:"+result);
        if ( result.equals("超出设置范围") ){
            return false;
        }
        return true;
    }

    public boolean inputValueTest( String value, ExcelBo row, String configName ) throws InterruptedException {
        AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
        DeviceHomePage deviceHomePage = new DeviceHomePage(appiumDriver);

        clickConfigItem(configName);
        LoggerLoad.info("输入测试数值："+value);
        writeText(input_value_area,value);
        click(input_confirm_btn);
        String result = readToastText(input_result);
        if ( result.equals("成功") ){
            // 提示成功并读取对应寄存器
            toDeviceHomePage(row.getPath()).intoConfig("高级设置");
            Map registerResult = advancedSettingPage.readRegisterValue(row.getRegisterType(),row.getRegister(),row.getRegisterLength());
            back(By.id("无用id，返回到设备首页, registerResult返回的map为int，int "));
            deviceHomePage.intoConfig(row.getPath().get(0));

            toConfigPage(row.getPath());
            LoggerLoad.debug("进入 " + configName + "设置项页面");
            // 比较寄存器数值与输入测试数值是否一致  ========== 未完成
            double actualRegisterValue = Double.parseDouble(registerResult.get(row.getRegister()).toString());
            double testValue = Double.parseDouble(value);
            LoggerLoad.debug("test debug=======寄存器数值:"+actualRegisterValue + " 输入测试的数值:" + testValue);
            LoggerLoad.debug("测试数据和寄存器数值是否相等:" + MathUtils.compareDoubleEquals(actualRegisterValue,testValue/row.getAccuracy()));
            if ( !MathUtils.compareDoubleEquals(actualRegisterValue,testValue) ){
                return false;
            }
            return true;
        }
        return false;
    }

    public String findConfigNameOnConfigPage( List<String> path ){
        // 获取设置项名称并点击
        String configName = null;
        for (String item: path){
            if (item.equals("/")){
                break;
            }
            configName = item;
        }
       return configName;
    }

    /**
     * Description:  开关型设置项测试方法: 循环两次，一次打开，一次关闭。若执行时间 > 1min，应该就是那个页面加载时间太长了（判断未加）
     * @param row bo.ExcelBo
     * @return void
     * @author Graycat. 2023/12/6 10:39
     */
    public void testSwitchConfig(ExcelBo row) throws InterruptedException {
        for (int j=0; j<2; j++){
            int btnExistFlag = 0;
            int btnStatus = 0;
            WebElement config_element = waitVisibility(switch_configure_area);
            List<WebElement> switch_text_list = config_element.findElements(switch_text);
            List<WebElement> switch_btn_List = config_element.findElements(switch_btn);

            // 根据设置项名称点击按钮, 并获取状态
            for (int i=0; i<switch_text_list.size(); i++){
                String configName = findConfigNameOnConfigPage(row.getPath());
                if (configName.equals(switch_text_list.get(i).getText())){
                    btnExistFlag = 1;
                    LoggerLoad.info("设置项："+ configName + ", 当前switch status: " +switch_btn_List.get(i).getAttribute("checked") );
                    if (switch_btn_List.get(i).getAttribute("checked").equals("true")){
                        LoggerLoad.info("关闭开关");
                        btnStatus = 0;
                    }else {
                        LoggerLoad.info("打开开关");
                        btnStatus = 1;
                    }
                    switch_btn_List.get(i).click();
                    String clickResult = readToastText(input_result);
                    LoggerLoad.info("设置结果(实际界面的toast提示)：" + clickResult);
                    break;
                }
            }
            if (btnExistFlag != 1) {
                row.setResult("该页面下无此开关");
                return;
            }

            // 返回到高级设置界面读取寄存器
            AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
            DeviceHomePage deviceHomePage = new DeviceHomePage(appiumDriver);

            toDeviceHomePage(row.getPath()).intoConfig("高级设置");
            Map registerResult = advancedSettingPage.readRegisterValue(row.getRegisterType(), row.getRegister(), row.getRegisterLength());
            back(By.id("无用id，返回到设备首页, registerResult返回的map为int，int "));
            // 判断是否成功
            if ( (Integer)registerResult.get(row.getRegister()) != btnStatus ){
                row.setResult("设置后寄存器与预期数值不一致");
                return;
            }
            deviceHomePage.intoConfig(row.getPath().get(0)).toConfigPage(row.getPath());
        }
        row.setResult("Pass");
    }

    /**
     * Description:  文本项设置测试方法：那种需要读寄存器然后转为ASCII码的不可设置设置项；
     *          注意：结束后无需返回设置项页面
     * @param row bo.ExcelBo
     * @return void
     * @author Graycat. 2023/12/11 14:08
     */
    public void testTextConfig(ExcelBo row) throws InterruptedException {
        // 寻找设置项实际文本内容
        String configName = findConfigNameOnConfigPage(row.getPath());
        String actualTextStr = "";

        List<WebElement> config_list = waitVisibilityWithAll(text_area);
        for (WebElement item : config_list){
            if (item.findElement(config_name_area).getText().equals(configName)){
                 actualTextStr = item.findElement(text_text).getText();
                 break;
            }
        }
        if (actualTextStr == null || actualTextStr.equals("")){
            row.setResult("Fail(设置项文本内容为空)");
            return;
        }
        LoggerLoad.info("实际设置项文本内容：" + actualTextStr);

        // 前往高级设置界面
        DeviceHomePage deviceHomePage = new DeviceHomePage(appiumDriver);
        AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
        backToHomeWithLeftTopBtn(row.getPath());
        deviceHomePage.intoConfig("高级设置");

        // 读取寄存器并转换数值
        String registerResultStr = "";
        Map<Integer,Integer> registerMap = advancedSettingPage.readMultiRegisterValue(row.getRegisterType(), row.getRegister(), row.getRegisterLength());
        for (Map.Entry<Integer,Integer> entry : registerMap.entrySet()){
            registerResultStr += StrUtils.numbersParseToStr(entry.getValue());
        }
        LoggerLoad.info("实际读取寄存器数值为(转换后)：" + registerResultStr);

        // 结果比对
        if (actualTextStr.equals(registerResultStr)){
            row.setResult("Pass");
        }else{
            row.setResult("Fail");
        }

        // 返回设备首页
        back(By.id("无效id,用来返回设备首页"));
    }


}
