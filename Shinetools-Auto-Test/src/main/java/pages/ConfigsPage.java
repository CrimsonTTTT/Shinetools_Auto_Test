package pages;

import bo.ExcelBo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.settings.AdvancedSettingPage;
import util.ExcelUtlis;
import util.MathUtils;

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
            for (int j = i; i < configPath.size(); i++) {
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
        }
        return this;
    }

    /**
     * Description:  ������ҳ��Ѱ����������
     * @param configName java.lang.String
     * @return void
     * @author Graycat. 2023/10/21 17:28
     */
    public void clickConfigItem( String configName ){
//        System.out.println("���뵥ѡ��.��ѡ����������Ϊ " + configName);
        List<WebElement> actualConfigList = waitVisibilityWithAll(title_area);
        for (WebElement item : actualConfigList){
            if (item.getText().equals(configName)){
                item.click();
                break;
            }
        }
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
     * ��ѡ������������ԣ���������������һ�飬����ȡ��Ӧ�ļĴ�����ֵ; ���ѽ�����浽excel
     * */
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

        System.out.println("ҳ��ʵ��ѡ��ֵ" + actualSelectStrList);
        System.out.println("ҳ��Ԥ��ѡ��ֵ" + expectSelectList);

        int resultFlag = 1;
        // �ж�ʵ��ҳ���ѡ��ֵ�Ƿ���Ԥ��һ�£���һ�£����뵥ѡ������߼����������ò����ص��߼��������ȡ��Ӧ�ļĴ���
        AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
        if( expectSelectList.equals(actualSelectStrList) ){
            System.out.println("���뵥ѡ������߼�^_^");
            DeviceHomePage deviceHomePage = new DeviceHomePage(appiumDriver);
            int itemIndex = 0 ; // ��˳��ȶ��±꣬Ĭ�ϴ�0��ʼ

            for(int i = 0; i < actualSelectStrList.size(); i++){
                actualSelectArea = waitVisibilityWithAll(select_value_area);
                actualSelectList = actualSelectArea.get(0).findElements(select_value);
                actualSelectList.get(i).click();

                toDeviceHomePage(row.getPath()).intoConfig("�߼�����");
                // ��ȡ��Ӧ�Ĵ���ֵ
                Map result = advancedSettingPage.readRegisterValue(row.getRegisterType(),row.getRegister(),row.getRegisterLength());
//                System.out.println("��ȡ�Ĵ�����ֵΪ��" + result.toString());

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
                row.setResult("ok,��������");
                back(title_area);
                return row;
            }
            row.setResult("�޸ĺ�Ĵ�����ֵδ���ģ�δ�ų����ú󷵻�ʧ�ܵ��������");
            back(title_area);
            return row;
        }

        row.setResult("Ԥ��ѡ��ֵ��ʵ��ѡ�һ��");
        back(title_area);
        return row;
    }


    /**
     * Description:  ������ҳ�淵���豸��ҳ�������Ӻ���Ǹ�ҳ��
     * @param path java.util.List<java.lang.String>
     * @return void
     * @author Graycat. 2023/10/16 20:21
     */
    public void backToHome(List<String> path) {
        System.out.println("���뵽���ز���"+path);
        if (path.get(1).equals("/")){
            back(top_title);
        }
        for (int i = 1; i<path.size(); i++){
            if( !path.get(i).equals("/") ){
                back(top_title);
                System.out.println("���ز���+1");
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

        if ( (expectHighLimit != actualHighLimit) || (expectLowLimit != actualLowLimit) ){
            row.setResult("ʵ�ʷ�Χ��Ԥ�ڷ�Χ��һ��");
            return;
        }
        // ����Χ����
        double testValue_higher = expectHighLimit + row.getAccuracy();
        double testValue_lower = expectLowLimit - row.getAccuracy();
        double testValue_random_middle = MathUtils.generateRandomNumber( testValue_lower,testValue_higher );
        int decimalPlaces = MathUtils.countDecimalPlaces(row.getAccuracy());
        String testValue_random_middle_str = MathUtils.formatDecimal(testValue_random_middle,decimalPlaces);

        System.out.println(row);
        System.out.println("�������ݣ�" + testValue_random_middle_str + ", " + testValue_higher + ", " + testValue_lower + ", " + row.getRangeHigh() + ", " + row.getRangeLow());

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
            row.setResult("���Ϸ�Χ��������ʧ�ܵ������");
            return;
        }

        row.setResult("pass");

    }

    /**
     * ��ȡҳ�棬������������ķ�Χ������.   list.get(0) Ϊ����
    */
    public List<String> dealActualRangeStr( String configName, String rangeStr ){
        System.out.println("ʵ��ҳ��������" + configName + " ��ΧstrΪ��" + rangeStr);
        // ����������ʽ
        String regex = "(\\d+)~(\\d+)";
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
//            System.out.println("firstValue: " + firstValue);
//            System.out.println("secondValue: " + secondValue);
        }
        List<String> result = new ArrayList<>();
        result.add(firstValue);
        result.add(secondValue);

        return  result;
    }

    public boolean inputOutRangeTest( String inputValue ) throws InterruptedException {
        writeText(input_value_area,inputValue);
        click(input_confirm_btn);

        String result = readToastText(input_result);

        System.out.println("���ý��:"+result);
        if ( result.equals("�������÷�Χ") ){
            return false;
        }
        return true;
    }

    public boolean inputValueTest( String value, ExcelBo row, String configName ) throws InterruptedException {
        AdvancedSettingPage advancedSettingPage = new AdvancedSettingPage(appiumDriver);
        DeviceHomePage deviceHomePage = new DeviceHomePage(appiumDriver);

        clickConfigItem(configName);
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
            System.out.println("���� " + configName + "������ҳ��");
            if ( !registerResult.get(row.getRegister()).equals(value) ){
                return false;
            }
            return true;
        }
        return false;
    }

}
