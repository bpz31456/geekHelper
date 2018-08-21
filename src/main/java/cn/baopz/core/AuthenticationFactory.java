package cn.baopz.core;

import org.openqa.selenium.WebDriver;

/**
 * @author baopz
 */
public final class AuthenticationFactory {

    public static Authentication getInstance(WebDriver driver){
        return new UsernameAndPasswordAuthentication(driver);
    }

}
