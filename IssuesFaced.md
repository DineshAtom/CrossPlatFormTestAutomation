

Error:-
 1. java.lang.AbstractMethodError: Receiver class io.appium.java_client.service.local.AppiumServiceBuilder does not define or inherit an implementation of the resolved method 'abstract void loadSystemProperties()' of abstract class org.openqa.selenium.remote.service.DriverService$Builder

    #### RCA: appium java client vs Selenium driver version compatibility issue.
    ### Solution:- 
        Downgraded Appium java client version
2. while executing PageFactory.initElements(new AppiumFieldDecorator(driver),this);
   getting java.lang.RuntimeException: java.lang.NoSuchMethodException: jdk.proxy2.$Proxy16.proxyClassLookup() error
    #### RCA: due to java Target byte code version is 20 which is used compile java code and jdk used to execute is 11
3. ### Solution:-
        Downgraded java compiler version from 20 to 11