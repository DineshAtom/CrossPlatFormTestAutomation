package mobile;

import com.app.base.BaseTest;
import com.app.pages.OnboardingPages;
import org.testng.annotations.Test;

public class OnboardingFunctionalityUserFlows extends BaseTest {

    @Test
    public void verifyOnboardingFunctionalityTest(){
        System.out.println("Launched");
        OnboardingPages onboardingPage = new OnboardingPages(driver);
        //
        onboardingPage.isDisplayedYouTubeLogo();
    }
}
