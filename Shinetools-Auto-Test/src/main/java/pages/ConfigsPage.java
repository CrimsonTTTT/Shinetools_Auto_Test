package pages;

import bo.ExcelBo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.settings.AdvancedSettingPage;
import util.ExcelUtlis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // �����б�������򣬶���������Ҳ�������档����ʵ�������������1��ʼ
        for ( int i = 1; i < configPath.size(); i++ ){
            List<WebElement> actualConfigList = waitVisibilityWithAll(title_area);
            // ����������������ͽ���������͵��ȥ
            if ( configPath.equals("/") ){
                break;
            }
            if ( configPath.get(i+1).equals("/") ){
                break;
            }
            for ( WebElement configItem: actualConfigList ){
                if( configItem.getText().equals(configPath.get(i)) ){
                    configItem.click();
                    break;
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
        System.out.println("���뵥ѡ��.��ѡ����������Ϊ " + configName);
        List<WebElement> actualConfigList = waitVisibilityWithAll(title_area);
        for (WebElement item : actualConfigList){
            if (item.getText().equals(configName)){
                item.click();
                break;
            }
        }
    }

    /**
     * ��ĳһ������ص���ҳ��������߼�����ҳ��
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
        // �Ҳ������˷�����λԪ��������
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

}
