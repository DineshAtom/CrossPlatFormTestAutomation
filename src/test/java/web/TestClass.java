package web;

import com.app.base.BaseTest;
import com.app.driver.config.Configuration;
import com.app.driver.config.ConfigurationManager;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestClass extends BaseTest {
    Configuration config = ConfigurationManager.getConfig();
    @Test
    @Parameters({"platform"})
    public void testMethod(String platform){
        System.out.println("todo");
        try {
            if (platform.equalsIgnoreCase("WINDOWSCHROME") || platform.equalsIgnoreCase("ANDROIDCHROME")) {
                Webdriver.get(config.webURL());
                Thread.sleep(8000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
