package com.app.base;

import com.app.driver.DriversManager;
import com.app.driver.PlatForm;
import com.app.driver.config.Configuration;
import com.app.driver.config.ConfigurationManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BaseTest extends DriversManager {

    Configuration config = ConfigurationManager.getConfig();
    protected AppiumDriver<MobileElement> driver;
    protected RemoteWebDriver Webdriver;
    protected AppiumDriverLocalService service;
    String test = config.test();
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
            case WINDOWSCHROME -> System.out.println("[AFTER-SUITE] For Web/Pwa Appium Server is not Started.");
            default -> System.out.println(
                    "[AFTER-SUITE] Default Case Not implemented: " + platformName);
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
    public void launchApp(String platform, @Optional("optional") String os, String udid, String platformVersion) throws Exception {
        try {
            System.out.println("[BEFOREMETHOD] Parameters Received: "+ os +", "+ platform);

            PlatForm platFormName = PlatForm.valueOf(platform.toUpperCase());
            switch (platFormName) {
                case ANDROIDCHROME:
                case WINDOWSCHROME: {
                    Webdriver = createInstanceWeb(platform, os, platformVersion, udid);
                    break;
                }
                case ANDROID: {
                    driver = createInstance(udid, platformVersion);
                    break;
                }
                default: {
                    System.out.println("[BEFORE-METHOD] Default Case Not implemented for: " + platFormName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass (alwaysRun = true)
    @Parameters({ "platform"})
    public void closeApp(String platform) throws Exception {
        try {
            PlatForm platFormName = PlatForm.valueOf(platform.toUpperCase());
            switch (platFormName) {
                case ANDROIDCHROME:
                case WINDOWSCHROME: {
                    if (Webdriver != null){
                        Webdriver.quit();
                        System.out.println("Closed Web Driver");
                    }
                    break;
                }
                case ANDROID: {
                    if (driver != null){
                        driver.quit();
                        System.out.println("Closed Android Driver");
                    }
                }
                default: {
                    System.out.println("[After-Class] Default Case Not implemented for: " + platFormName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
