package cn.baopz.core;

import cn.baopz.utils.PropertiesTool;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author baopz
 */
public final class WebDriverFactory {

    /**
     * Create Chrome Web Driver
     * @return
     */
    public static WebDriver getChromeWebDriver() {
        try {
            System.setProperty("webdriver.chrome.driver", Objects.requireNonNull(PropertiesTool.getProperty("geekHelper.properties", "webDriver")));
        } catch (IOException e) {
            //ignore
            return null;
        }
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>(2 << 3);
        //后续处理IE下面下载问题
        String userAgentIE11 = "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko";
        chromePrefs.put("profile.general_useragent_override", userAgentIE11);
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("profile.managed_default_content_settings.images", 2);
        //对于实验性质的选项
        options.setExperimentalOption("prefs", chromePrefs);
        //消除安全校验
        options.addArguments("--allow-running-insecure-content");
        //最大化
        options.addArguments("--ash-enable-fullscreen-app-list");
        //启动最大化，防止失去焦点
        options.addArguments("--start-maximized");
        //关闭gpu图片渲染
        options.addArguments("--disable-gpu");
        //无界面浏览器，貌似不起作用
        //options.setHeadless(true);
        //user-agent 设置 profile.general_useragent_override
        //webDriver错误弹框，同意,貌似没啥用
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        return new ChromeDriver(options);
    }
}
