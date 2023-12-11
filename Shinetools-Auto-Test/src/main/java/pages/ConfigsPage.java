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

    // �����б�������򣬶���������Ҳ�������档����ʵ�������������1��ʼ
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
     * �Աȵ�ǰ������ҳ���������������excel�Ƿ�һ�£��ϸ�Ҫ�����ơ�˳��
     * */
    public void compareConfigText(List<WebElement> actualList, List<ExcelBo> expectList ){

    }

    /**
     * ǰ��ĳһ�������ҳ��
     * */
    public ConfigsPage toConfigPage( List<String> configPath) throws InterruptedException {
        int i = 1;
        try {
            // �����б�������򣬶���������Ҳ�������档����ʵ�������������1��ʼ
            for (i = 1; i < configPath.size(); i++) {
                List<WebElement> actualConfigList = waitVisibilityWithAll(title_area);
                // ����������������ͽ���������͵��ȥ
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
                // ����������������ͽ���������͵��ȥ
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
     * Description:  ������ҳ��Ѱ����������
     */
    public void clickConfigItem( String configName ){
        List<WebElement> actualConfigList = waitVisibilityWithAll(title_area);
        for (WebElement item : actualConfigList){
            if (item.getText().equals(configName)){
                item.click();
                break;
            }
        }
        LoggerLoad.debug("�������� " + configName);
    }

    /**
     * ��ĳһ������ص���ҳ
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
     * Description:  ��ѡ������������Է�������������������һ�飬����ȡ��Ӧ�ļĴ�����ֵ; ���ѽ�����浽excel
     * @param excelRow bo.ExcelBo
     * @return bo.ExcelBo
     * @author Graycat. 2023/12/11 14:12
     */
    public ExcelBo testSelectConfig( ExcelBo excelRow ) throws InterruptedException {
        return  compareSelectOption(excelRow);
    }

    public ExcelBo compareSelectOption( ExcelBo row ) throws InterruptedException {
        // ��ȡ���������Ʋ����
        String configName = null;
        for (String item: row.getPath()){
            if (item.equals("/")){
                break;
            }
            configName = item;
        }
        clickConfigItem(configName);

        List<String> expectSelectList = new ArrayList<>();

        String[] strings = row.getSelectValue().split("��");

        // ��ȡԤ��ѡ��ֵ
        for ( int i = 0; i < strings.length; i++ ){
            expectSelectList.add(strings[i]);
        }
        // ��ȡʵ��ҳ��ѡ��ֵ
        List<WebElement> actualSelectArea = waitVisibilityWithAll(select_value_area);
        List<WebElement> actualSelectList = actualSelectArea.get(0).findElements(select_value);
        List<String> actualSelectStrList = new ArrayList<>();
        for (WebElement item : actualSelectList){
            actualSelectStrList.add(item.getText());
        }

        LoggerLoad.info("ҳ��ʵ��ѡ��ֵ" + actualSelectStrList);
        LoggerLoad.info("ҳ��Ԥ��ѡ��ֵ" + expectSelectList);

        int resultFlag = 1;
        // �ж�ʵ��ҳ���ѡ��ֵ�Ƿ���Ԥ��һ�£���һ�£����뵥ѡ������߼����������ò����ص��߼��������ȡ��Ӧ�ļĴ���
        AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
        if( expectSelectList.equals(actualSelectStrList) ){
            LoggerLoad.info("���뵥ѡ������߼�^_^");
            DeviceHomePage deviceHomePage = new DeviceHomePage(appiumDriver);
            int itemIndex = 0 ; // ��˳��ȶ��±꣬Ĭ�ϴ�0��ʼ

            for(int i = 0; i < actualSelectStrList.size(); i++){
                actualSelectArea = waitVisibilityWithAll(select_value_area);
                actualSelectList = actualSelectArea.get(0).findElements(select_value);
                LoggerLoad.info("���ѡ��" + actualSelectList.get(i).getText());
                actualSelectList.get(i).click();

                backToHomeWithLeftTopBtn(row.getPath());
                deviceHomePage.intoConfig("�߼�����");
                // ��ȡ��Ӧ�Ĵ���ֵ
                Map result = advancedSettingPage.readRegisterValue(row.getRegisterType(),row.getRegister(),row.getRegisterLength());

                advancedSettingPage.back(By.id("���߰����."));
                // �Ա�itemIndex��result��ֵ������һ�£�˵������������֮���Ӧ�ļĴ���ֵΪ�޸ģ�ֱ�ӷ�����ҳ
                int actualRegisterValue = (Integer) result.get(row.getRegister());
                if ( i != actualRegisterValue){
                    resultFlag = 0;
                    break;
                }
                itemIndex++;
                // �ϴ�д���������������������������
                deviceHomePage.intoConfig(row.getPath().get(0));
                toConfigPage(row.getPath());
                clickConfigItem(configName);
            }
            // ����������������룬����ok��flag = 1
            if ( resultFlag == 1 ) {
                row.setResult("Pass");
                back(title_area);
                return row;
            }
            row.setResult("�޸ĺ�Ĵ�����ֵ�����ò�һ�£�δ�ų����ú󷵻�ʧ�ܵ��������");
            back(title_area);
            return row;
        }

        row.setResult("Ԥ��ѡ��ֵ��ʵ��ѡ�һ��");
        back(title_area);
        return row;
    }


    /**
     * Description:  ������ҳ�淵���豸��ҳ�������Ӻ���Ǹ�ҳ��
     */
    public void backToHome(List<String> path) {
        LoggerLoad.info("���뵽���ز���"+path);
        if (path.get(1).equals("/")){
            back(top_title);
        }
        for (int i = 1; i<path.size(); i++){
            if( !path.get(i).equals("/") ){
                back(top_title);
                LoggerLoad.debug("���ز���+1");
            }
        }
    }

    public void backToHomeWithLeftTopBtn(List<String> path) throws InterruptedException {
        LoggerLoad.info("���뵽���ز���(ʹ�����Ͻ�ҳ�淵�ذ�ť)"+path);
        if (path.get(1).equals("/")){
            clickBackBtn();
        }
        for (int i = 1; i<path.size(); i++){
            if( !path.get(i).equals("/") ){
                clickBackBtn();
                LoggerLoad.debug("���ز���+1");
            }
        }
    }


    /**
     * Description:  ��������������Է��������ԣ�0.��Χ�Ƿ�һ��  1. ��Χֵ����С����  2.��Χֵ��Сֵ-��С����  3.��Χֵ���ֵ+��󾫶�  4.���ֵ����Сֵ  5.�����ַ�����ȷ�ϣ�
     * @param row bo.ExcelBo
     * @return void
     * @author Graycat. 2023/11/23 15:17
     */
    public void testInputConfig( ExcelBo row ) throws InterruptedException {
        LoggerLoad.debug("������������Է����������"+row);
        // ��ȡ���������Ʋ����
        String configName = null;
        for (String item: row.getPath()){
            if (item.equals("/")){
                break;
            }
            configName = item;
        }
        clickConfigItem(configName);

        // ��ȡԤ�ڷ�Χ��ʵ��ҳ��չʾ��Χ����ȡʧ�������δ���ձ�׼��ʽչʾ
        double expectHighLimit = 0, expectLowLimit = 0, actualHighLimit = 0, actualLowLimit = 0;
        expectLowLimit = row.getRangeLow();
        expectHighLimit = row.getRangeHigh();

        String rangeStr = readText(input_range_area);
        List<String> actualRangeList = dealActualRangeStr(configName,rangeStr);
        actualHighLimit = Double.parseDouble(actualRangeList.get(1));
        actualLowLimit = Double.parseDouble(actualRangeList.get(0));
        LoggerLoad.info("Ԥ�ڷ�Χ�����ޣ�" + expectLowLimit + "  ���ޣ�" + expectHighLimit);

        if ( (expectHighLimit != actualHighLimit) || (expectLowLimit != actualLowLimit) ){
            LoggerLoad.info("���Խ����ʵ�ʷ�Χ��Ԥ�ڷ�Χ��һ��");
            row.setResult("ʵ�ʷ�Χ��Ԥ�ڷ�Χ��һ��");
            back(By.id("��Чid���������ص���ҳ"));
            return;
        }

        // ����Χ����
        double testValue_higher = expectHighLimit + row.getAccuracy();
        double testValue_lower = expectLowLimit - row.getAccuracy();
        double testValue_random_middle = MathUtils.generateRandomNumber( testValue_lower,testValue_higher );
        int decimalPlaces = MathUtils.countDecimalPlaces(row.getAccuracy());
        String testValue_random_middle_str = MathUtils.formatDecimal(testValue_random_middle,decimalPlaces);

        LoggerLoad.debug(row.toString());
        LoggerLoad.debug("�������ݣ�" + testValue_higher + ", " + testValue_lower + ", " + row.getRangeLow() + ", " + row.getRangeHigh() + ", " + testValue_random_middle_str );

        boolean inputResult = inputOutRangeTest( String.valueOf(testValue_higher) );
        clickConfigItem(configName);
        boolean inputResult2 = inputOutRangeTest( String.valueOf(testValue_lower) );
        if (inputResult == true || inputResult2 == true){
            row.setResult("������Χֵ�����ã�");
            return ;
        }

        // ��Χ������test.
        boolean inputResult3 = inputValueTest( String.valueOf(row.getRangeLow()),row,configName );
        boolean inputResult4 = inputValueTest( String.valueOf(row.getRangeHigh()),row,configName );
        boolean inputResult5 = inputValueTest( testValue_random_middle_str,row,configName );
        if (inputResult3 == false || inputResult4 == false || inputResult5 == false){
            row.setResult("������ֵ��Ĵ�����ȡ��ֵ��һ�£�");
            return;
        }

        row.setResult("Pass");
    }

    /**
     * ��ȡʵ��ҳ�棬������������ķ�Χ������.   list.get(0) Ϊ����
    */
    public List<String> dealActualRangeStr( String configName, String rangeStr ){
        LoggerLoad.info("ʵ��ҳ��������" + configName + " ��ΧstrΪ��" + rangeStr);
        // ����������ʽ
        String regex = "(-?\\d+(?:\\.\\d*)?)~(-?\\d+(?:\\.\\d*)?)";

        // ����������ʽ
        Pattern pattern = Pattern.compile(regex);
        // ���� Matcher ����
        Matcher matcher = pattern.matcher(rangeStr);

        String firstValue = "", secondValue = "";

        // ����ƥ��
        if (matcher.find()) {
            // ��ȡ��һ��ƥ�����ֵ��0 ��ֵ��
            firstValue = matcher.group(1);
            // ��ȡ�ڶ���ƥ�����ֵ��2000 ��ֵ��
            secondValue = matcher.group(2);
            // ������
            LoggerLoad.debug("������������ޣ� " + firstValue + " ���ޣ�" + secondValue);
        }
        List<String> result = new ArrayList<>();
        result.add(firstValue);
        result.add(secondValue);

        return  result;
    }

    public boolean inputOutRangeTest( String inputValue ) throws InterruptedException {
        LoggerLoad.info("���������ֵ��"+inputValue);
        writeText(input_value_area,inputValue);
        click(input_confirm_btn);

        String result = readToastText(input_result);

        LoggerLoad.info("���ý��:"+result);
        if ( result.equals("�������÷�Χ") ){
            return false;
        }
        return true;
    }

    public boolean inputValueTest( String value, ExcelBo row, String configName ) throws InterruptedException {
        AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
        DeviceHomePage deviceHomePage = new DeviceHomePage(appiumDriver);

        clickConfigItem(configName);
        LoggerLoad.info("���������ֵ��"+value);
        writeText(input_value_area,value);
        click(input_confirm_btn);
        String result = readToastText(input_result);
        if ( result.equals("�ɹ�") ){
            // ��ʾ�ɹ�����ȡ��Ӧ�Ĵ���
            toDeviceHomePage(row.getPath()).intoConfig("�߼�����");
            Map registerResult = advancedSettingPage.readRegisterValue(row.getRegisterType(),row.getRegister(),row.getRegisterLength());
            back(By.id("����id�����ص��豸��ҳ, registerResult���ص�mapΪint��int "));
            deviceHomePage.intoConfig(row.getPath().get(0));

            toConfigPage(row.getPath());
            LoggerLoad.debug("���� " + configName + "������ҳ��");
            // �ȽϼĴ�����ֵ�����������ֵ�Ƿ�һ��  ========== δ���
            double actualRegisterValue = Double.parseDouble(registerResult.get(row.getRegister()).toString());
            double testValue = Double.parseDouble(value);
            LoggerLoad.debug("test debug=======�Ĵ�����ֵ:"+actualRegisterValue + " ������Ե���ֵ:" + testValue);
            LoggerLoad.debug("�������ݺͼĴ�����ֵ�Ƿ����:" + MathUtils.compareDoubleEquals(actualRegisterValue,testValue/row.getAccuracy()));
            if ( !MathUtils.compareDoubleEquals(actualRegisterValue,testValue) ){
                return false;
            }
            return true;
        }
        return false;
    }

    public String findConfigNameOnConfigPage( List<String> path ){
        // ��ȡ���������Ʋ����
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
     * Description:  ��������������Է���: ѭ�����Σ�һ�δ򿪣�һ�ιرա���ִ��ʱ�� > 1min��Ӧ�þ����Ǹ�ҳ�����ʱ��̫���ˣ��ж�δ�ӣ�
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

            // �������������Ƶ����ť, ����ȡ״̬
            for (int i=0; i<switch_text_list.size(); i++){
                String configName = findConfigNameOnConfigPage(row.getPath());
                if (configName.equals(switch_text_list.get(i).getText())){
                    btnExistFlag = 1;
                    LoggerLoad.info("�����"+ configName + ", ��ǰswitch status: " +switch_btn_List.get(i).getAttribute("checked") );
                    if (switch_btn_List.get(i).getAttribute("checked").equals("true")){
                        LoggerLoad.info("�رտ���");
                        btnStatus = 0;
                    }else {
                        LoggerLoad.info("�򿪿���");
                        btnStatus = 1;
                    }
                    switch_btn_List.get(i).click();
                    String clickResult = readToastText(input_result);
                    LoggerLoad.info("���ý��(ʵ�ʽ����toast��ʾ)��" + clickResult);
                    break;
                }
            }
            if (btnExistFlag != 1) {
                row.setResult("��ҳ�����޴˿���");
                return;
            }

            // ���ص��߼����ý����ȡ�Ĵ���
            AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
            DeviceHomePage deviceHomePage = new DeviceHomePage(appiumDriver);

            toDeviceHomePage(row.getPath()).intoConfig("�߼�����");
            Map registerResult = advancedSettingPage.readRegisterValue(row.getRegisterType(), row.getRegister(), row.getRegisterLength());
            back(By.id("����id�����ص��豸��ҳ, registerResult���ص�mapΪint��int "));
            // �ж��Ƿ�ɹ�
            if ( (Integer)registerResult.get(row.getRegister()) != btnStatus ){
                row.setResult("���ú�Ĵ�����Ԥ����ֵ��һ��");
                return;
            }
            deviceHomePage.intoConfig(row.getPath().get(0)).toConfigPage(row.getPath());
        }
        row.setResult("Pass");
    }

    /**
     * Description:  �ı������ò��Է�����������Ҫ���Ĵ���Ȼ��תΪASCII��Ĳ������������
     *          ע�⣺���������践��������ҳ��
     * @param row bo.ExcelBo
     * @return void
     * @author Graycat. 2023/12/11 14:08
     */
    public void testTextConfig(ExcelBo row) throws InterruptedException {
        // Ѱ��������ʵ���ı�����
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
            row.setResult("Fail(�������ı�����Ϊ��)");
            return;
        }
        LoggerLoad.info("ʵ���������ı����ݣ�" + actualTextStr);

        // ǰ���߼����ý���
        DeviceHomePage deviceHomePage = new DeviceHomePage(appiumDriver);
        AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
        backToHomeWithLeftTopBtn(row.getPath());
        deviceHomePage.intoConfig("�߼�����");

        // ��ȡ�Ĵ�����ת����ֵ
        String registerResultStr = "";
        Map<Integer,Integer> registerMap = advancedSettingPage.readMultiRegisterValue(row.getRegisterType(), row.getRegister(), row.getRegisterLength());
        for (Map.Entry<Integer,Integer> entry : registerMap.entrySet()){
            registerResultStr += StrUtils.numbersParseToStr(entry.getValue());
        }
        LoggerLoad.info("ʵ�ʶ�ȡ�Ĵ�����ֵΪ(ת����)��" + registerResultStr);

        // ����ȶ�
        if (actualTextStr.equals(registerResultStr)){
            row.setResult("Pass");
        }else{
            row.setResult("Fail");
        }

        // �����豸��ҳ
        back(By.id("��Чid,���������豸��ҳ"));
    }


}
