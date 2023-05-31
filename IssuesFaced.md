

Error:-
 1. java.lang.AbstractMethodError: Receiver class io.appium.java_client.service.local.AppiumServiceBuilder does not define or inherit an implementation of the resolved method 'abstract void loadSystemProperties()' of abstract class org.openqa.selenium.remote.service.DriverService$Builder

    #### RCA: appium java client vs Selenium driver version compatibility issue.
    ### Solution:- 
        Downgraded Appium java client version