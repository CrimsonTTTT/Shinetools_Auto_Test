package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * @Author Graycat.
 * @CreateTime 2023/9/25 19:31
 * @Descripe
 */
public class ChooseProductionPage extends BasePage{
    By next_btn = By.id("com.growatt.shinetools:id/btn_next");

    /** direct wifi的产品列表 */
    By MINType_btn = By.id("com.growatt.shinetools:id/tv_type_max");
    By SPH10KTL_US_btn = By.id("com.growatt.shinetools:id/tv_type_230");
    By SPH10KTL_btn = By.id("com.growatt.shinetools:id/tv_type_230_hu");
    By WIT_A_US_btn = By.id("com.growatt.shinetools:id/tv_type_wita");
    By WIT_H_US_btn = By.id("com.growatt.shinetools:id/tv_type_with");
    By WIT_A_AE_AU = By.id("com.growatt.shinetools:id/tv_type_wit_tl3_a");


    /** usb_wifi的产品列表....待补充。。。。 */
    By mic_min_tlx_item = By.id("com.growatt.shinetools:id/ll_type_mic_min");
    By min_tl_xh_item = By.id("com.growatt.shinetools:id/ll_type_min_tl_xh");
    By mod_mid_mac_item = By.id("com.growatt.shinetools:id/ll_type_mod_mid_mac");
    By mod_tl3_item = By.id("com.growatt.shinetools:id/ll_mod_tl3_xh");
    By max_tl3_item = By.id("com.growatt.shinetools:id/ll_type_max_tl3_lvmv");


    public ChooseProductionPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     *  选择Direct wifi下的产品类型，
     * */
    public ScanCodePage chooseProductionType( int type ) throws InterruptedException {
        if ( type == 0 ){
            click(MINType_btn);
        }else if( type == 1 ){
            click(SPH10KTL_US_btn);
        }else if( type == 2 ) {
            click(SPH10KTL_btn);
        }else if( type == 3 ){
            click(WIT_A_US_btn);
        }else if( type == 4 ){
            click(WIT_H_US_btn);
        }else {
            System.out.println("选的啥啊！");
        }
        return new ScanCodePage(appiumDriver);
    }
    /**
     *  usb wifi链接流程
     */
    public ScanCodePage connectUSBWifi(  ) throws InterruptedException {
        click(next_btn);
        return new ScanCodePage(appiumDriver);
    }

    /** 测试用 */
    public void chooseProductionType_USB( String name ) throws InterruptedException {
//        click(min_tl_xh_item);
        swipeDownHalf();
        click(WIT_A_AE_AU);

    }

}
