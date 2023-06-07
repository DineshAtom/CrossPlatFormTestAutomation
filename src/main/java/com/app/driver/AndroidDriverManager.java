package com.app.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.MobileCapabilityType.*;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

/**
 * This class is responsible for managing Android drivers,
 * which are used to automate mobile application testing on Android devices.
 */
public class AndroidDriverManager implements IDriver {

    private AppiumDriver<MobileElement> driver;
    public AppiumDriver<MobileElement> createInstance(String udid, String platformVersion) {
        try {
//            Configuration configuration = ConfigurationManager.getConfiguration();
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability(UDID, udid);
            capabilities.setCapability(PLATFORM_VERSION, platformVersion);
            capabilities.setCapability(DEVICE_NAME, "Android Emulator");
            capabilities.setCapability(PLATFORM_NAME, MobilePlatform.ANDROID);
            capabilities.setCapability(AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            capabilities.setCapability("autoGrantPermissions", true);
            capabilities.setCapability("unlockType", "pin");
//			capabilities.setCapability("unlockType", "pinWithKeyEvent");
            capabilities.setCapability("unlockKey", "1111");
            capabilities.setCapability("newCommandTimeout", 300 * 60);
            capabilities.setCapability("ignoreHiddenApiPolicyError" , true);

/*            if (Boolean.TRUE.equals(configuration.installApp())) {
                capabilities.setCapability(APP, new File(configuration.androidAppPath()).getAbsolutePath());
            } else {*/
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.amazon.avod.thirdpartyclient");
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                    "com.amazon.avod.client.activity.HomeScreenActivity");
//            }

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
//        Configuration configuration = ConfigurationManager.getConfiguration();
        return String.format("http://%s:%s/wd/hub", "127.0.0.1", 4724);
    }
}
