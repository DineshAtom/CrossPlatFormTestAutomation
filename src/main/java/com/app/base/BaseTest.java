package com.app.base;

import com.app.driver.PlatForm;
import com.app.driver.config.Configuration;
import com.app.driver.config.ConfigurationManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.MobileCapabilityType.*;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

public class BaseTest {
    Configuration config = ConfigurationManager.getConfig();
    protected AppiumDriver<MobileElement> driver;
    protected RemoteWebDriver Webdriver;
    protected AppiumDriverLocalService service;
    int port=Integer.parseInt(config.port());

    @BeforeSuite(alwaysRun = true)
    @Parameters({"platform", "os"})
    public void startServer(String platformName, String osName) {
//        getMavenDependenciesVersion();
        System.out.println("[BEFORESUITE] Parameters Received: " + osName + ", " + platformName);
        PlatForm platForm = PlatForm.valueOf(platformName.toUpperCase());

        switch (platForm) {
            case ANDROIDCHROME, ANDROID -> {
                try {
                    System.out.println("[EVENT] Starting Appium in Windows Machine");
                    service = AppiumDriverLocalService.
                            // buildDefaultService();
                                    buildService(new AppiumServiceBuilder()
                                    .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
                                    .withArgument(() -> "--allow-insecure", "chromedriver_autodownload")
                                    .withAppiumJS(new File("C:\\Users\\" + System.getProperty("user.name")
                                            + "\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                                    .withIPAddress(config.ip()).usingPort(port)
                                    .withArgument(GeneralServerFlag.LOG_LEVEL, "error"));// Set log level to "error" // configure the Appium server to log only error-level message

                    if (service.isRunning()) {
                        service.stop();
                    } else {
                        service.start();
                        System.out.println("[BEFORE SUITE] Appium Server Started Successfully.");
                    }
                    // Get the address and port of the started Appium server
                    String serverUrl = service.getUrl().toString();
                    System.out.println("Appium server started at: " + serverUrl);
                } catch (AppiumServerHasNotBeenStartedLocallyException ae) {
                    ae.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Parameters({ "platform", "os"})
    @AfterSuite(alwaysRun = true)
    public void stopServer(String platformName, @Optional("optional") String os) {
        System.out.println("[AFTER-SUITE] Parameters Received: "+ os +", "+ platformName);

        PlatForm platForm = PlatForm.valueOf(platformName.toUpperCase());

        switch (platForm) {
            case ANDROIDCHROME, ANDROID -> {
                if (service != null) {
                    service.stop();
                    System.out.println("[AFTER-SUITE] Appium Server Stopped Successfully.");
                } else {
                    System.err.println("[AFTER-SUITE] Appium Server already Stopped.");
                }
            }
            case WINDOWSCHROME -> {
                System.out.println("[AFTER-SUITE] For Web/Pwa Appium Server is not Started.");
            }
            default -> {
                System.out.println(
                        "[AFTER-SUITE] Default Case Not implemented: " + platformName);
            }
        }

    }

        public  void getMavenDependenciesVersion() {
            try {
                // Read the pom.xml file
                MavenXpp3Reader reader = new MavenXpp3Reader();
                Model model = reader.read(new FileReader("pom.xml"));

                // Print the versions of dependencies
                for (Dependency dependency : model.getDependencies()) {
                    String groupId = dependency.getGroupId();
                    String artifactId = dependency.getArtifactId();
                    String version = dependency.getVersion();
                    System.out.println(groupId + ":" + artifactId + ":" + version);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "platform", "os", "udid", "platformVersion"})
    public void launchApp(String platform, @Optional("optional") String os, String udid, String platformVersion) throws IOException, Exception {
        try {
            System.out.println("[BEFOREMETHOD] Parameters Received: "+ os +", "+ platform);

            PlatForm platFormName = PlatForm.valueOf(platform.toUpperCase());
            switch (platFormName) {
/*                case PWA:
                case WEB: {
                    Webdriver = new DriverFactory().createInstanceofWeb(platform, os, udid, platformVersion);
                    break;
                }*/
                case ANDROID:{
                    driver = createInstance(udid, platformVersion);
                    break;
                }
                default: {
                    System.out.println("[BEFORE-METHOD] Default Case Not implemented for: " + platFormName);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

            if (Boolean.TRUE.equals(config.installApp())) {
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
}
