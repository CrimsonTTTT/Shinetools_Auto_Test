package pages.settings;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import pages.BasePage;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Graycat.
 * @CreateTime 2023/9/28 11:24
 * @Descripe    �߼�����ҳ��
 */
public class AdvancedSettingPage extends BasePage {

    By commandType_input = By.id("com.growatt.shinetools:id/etCommand");
    By address_input = By.id("com.growatt.shinetools:id/etRegisterAddress");
    By lengthData_input = By.id("com.growatt.shinetools:id/etLengthData");
    By confirm_btn = By.id("com.growatt.shinetools:id/btnStart");

    By result_area = By.id("com.growatt.shinetools:id/rvReceiver");
    By result_data_list = By.xpath("//androidx.recyclerview.widget.RecyclerView[2]//android.widget.TextView");
    By top_title = By.id("com.growatt.shinetools:id/toolbar");
    public AdvancedSettingPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Description:  ��ȡָ��
     * @param type int
     * @param address int
     * @param lengthOrData int
     * @return void
     * @author Graycat. 2023/9/28 11:32
     */
    public Map readRegisterValue( int type, int address, int lengthOrData ){
        writeText(commandType_input, String.valueOf(type));
        writeText(address_input, String.valueOf(address));
        writeText(lengthData_input, String.valueOf(lengthOrData));
        click(confirm_btn);
        String result = readText(result_data_list);     // ֻ����һ���Ĵ������ȵ����ݣ�ʵ��ҳ����ܴ��ڶ�����
        return analysisResultStrToMap(result);
    }

    public void backToDeviceHome(  ){
        back(top_title);
    }


    public Map analysisResultStrToMap( String str ){

        Pattern pattern = Pattern.compile("(\\d+)--(\\d+)");
        Matcher matcher = pattern.matcher(str);

        Map<Integer, Integer> numberMap = new HashMap<>();
        while (matcher.find()) {
            int key = Integer.parseInt(matcher.group(1));
            int value = Integer.parseInt(matcher.group(2));
            numberMap.put(key, value);
        }

        System.out.println("��ȡ�Ĵ����Ľ��str: " + str);
//        System.out.println("ת�����Map: " + numberMap);

        return numberMap;
    }

}
