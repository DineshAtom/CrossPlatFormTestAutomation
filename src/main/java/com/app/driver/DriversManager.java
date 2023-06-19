package com.app.driver;

import com.app.driver.config.Configuration;
import com.app.driver.config.ConfigurationManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.MobileCapabilityType.*;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

/**
 * This class is responsible for managing Android drivers,
 * which are used to automate mobile application testing on Android devices.
 */
public class DriversManager implements IDriver {

    Configuration config = ConfigurationManager.getConfig();
    int port=Integer.parseInt(config.port());
    boolean installAPP = Boolean.parseBoolean(config.installApp());
    private AppiumDriver<MobileElement> driver;

    public AppiumDriver<MobileElement> createInstance(String udid, String platformVersion) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability(UDID, udid);
            capabilities.setCapability(PLATFORM_VERSION, platformVersion);
            capabilities.setCapability(DEVICE_NAME, "Android Emulator");
            capabilities.setCapability(PLATFORM_NAME, MobilePlatform.ANDROID);
            capabilities.setCapability(AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            capabilities.setCapability("autoGrantPermissions", true);
            capabilities.setCapability("unlockType", "pin");
            capabilities.setCapability("unlockKey", "1111");
            capabilities.setCapability("newCommandTimeout", 300 * 60);
            capabilities.setCapability("ignoreHiddenApiPolicyError" , true);

            if (installAPP){
                capabilities.setCapability(APP, new File(config.appPath()).getAbsolutePath());
            } else {
                capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, config.appPackageName());
                capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,config.appActivityName());
            }

            driver = new AndroidDriver<>(new URL(gridUrl()), capabilities);
        } catch (MalformedURLException e) {
/*            new Event().log("ERROR","Error in Creating Driver and Launching App." + e.getMessage());
            logger.error("Failed to initiate the tests for the Android device", e);*/
            e.printStackTrace();
        } catch (Exception e) {
/*            new Event().log("ERROR","Error in Creating Driver and Launching App." + e.getMessage());
            logger.error("Failed to initiate the tests for the Android device", e.getMessage());*/
            e.printStackTrace();
        }

        return driver;
    }

    String gridUrl() {
        return String.format("http://%s:%s/wd/hub",config.ip(),port);
    }

    @Parameters({ "platform", "os","platformVersion","udid"})
    public RemoteWebDriver createInstanceWeb(String platform,String os,String platformVersion, String udid) {
        RemoteWebDriver Webdriver = null;
        PlatForm platFormName = PlatForm.valueOf(platform.toUpperCase());
        switch (platFormName) {
            case WINDOWSCHROME: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-site-isolation-trials");
                options.addArguments("--start-maximized");
                Webdriver = new ChromeDriver(options);
//                bDriver.set(Webdriver);
                Webdriver.manage().deleteAllCookies();
                Webdriver.manage().window().maximize();
                break;
            }
            case ANDROIDCHROME:{
                DesiredCapabilities capabilities = new DesiredCapabilities();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-infobars");
                options.addArguments("androidPackage", "com.android.chrome");
                capabilities.setCapability(MobileCapabilityType.PLATFORM, platform);
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, os);
                capabilities.setCapability(MobileCapabilityType.UDID, udid);
                capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
                capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
                capabilities.setCapability("deviceOrientation", "portrait");
                capabilities.setCapability("unlockType", "pin");
                capabilities.setCapability("unlockKey", "1111");
                capabilities.setCapability("autoAcceptAlerts", true);
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                capabilities.setCapability(MobileCapabilityType.SUPPORTS_APPLICATION_CACHE, true);
                capabilities.setCapability(MobileCapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, false);
                capabilities.setCapability("newCommandTimeout", 60 * 5);
                // the path to the ChromeDriver executable file
                System.setProperty("webdriver.chrome.driver", ".//drivers//chromedriver.exe");
                try {
                    Webdriver = new AndroidDriver<MobileElement>(new URL(gridUrl()), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            }
            default: throw new IllegalArgumentException(
                    "Platform not supported! "+platFormName);
        }
        return Webdriver;
    }

}
