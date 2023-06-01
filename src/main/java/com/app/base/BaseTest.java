package com.app.base;

import com.app.driver.PlatForm;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BaseTest {
//    protected AppiumDriver<MobileElement> driver;
//    protected RemoteWebDriver Webdriver;
    protected AppiumDriverLocalService service;

    @BeforeSuite(alwaysRun = true)
    @Parameters({ "platform", "os"})
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
                                    .withIPAddress("127.0.0.1").usingPort(4723)
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
}
