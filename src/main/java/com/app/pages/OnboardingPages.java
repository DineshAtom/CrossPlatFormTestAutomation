package com.app.pages;

import com.app.base.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.temporal.ChronoUnit;

public class OnboardingPages extends BaseTest {
    RemoteWebDriver driver;
    public OnboardingPages(AppiumDriver<?> driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
        System.out.println("test");
    }


    @AndroidFindBy(id = "com.tatasky.binge:id/tv_select_languages")
    @WithTimeout(time = 30,chronoUnit = ChronoUnit.SECONDS)
    public MobileElement languagePreferencePopUp;
    public boolean isDisplayedLanguagePreferencePopUp(){
        boolean blFlag = false;
        try {
            if (languagePreferencePopUp.isDisplayed()){
                System.out.println("Language Preference Pop Up displayed");
                blFlag = true;
            }else {
                System.out.println("Language Preference Pop Up is not displayed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return blFlag;
    }

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@resource-id='com.tatasky.binge:id/cl_language'])[7]")
    @WithTimeout(time = 30,chronoUnit = ChronoUnit.SECONDS)

    public MobileElement tamilOnLanguagePreferencePopUp;
    public boolean clickTamilOnLanguagePreferencePopUp(){
        boolean blFlag = false;
        try {
            tamilOnLanguagePreferencePopUp.click();
                System.out.println("Tamil on Language Preference Pop Up displayed");
                blFlag = true;
        }catch (Exception e){
        System.out.println("Tamil on Language Preference Pop Up is not displayed");
            e.printStackTrace();
        }
        return blFlag;
    }













}
