package com.app.pages;

import com.app.base.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

public class OnboardingPages extends BaseTest {
    RemoteWebDriver driver;
    public OnboardingPages(AppiumDriver<?> driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
        System.out.println("test");
    }

    @AndroidFindBy(xpath = "Dummy")
    public MobileElement signInPageTitle;
    public boolean isDisplayedSignInPage(){
        boolean blFlag = false;
        try {
            if (signInPageTitle.isDisplayed()){
                System.out.println("Sign page displayed");
                blFlag = true;
            }else {
                System.out.println("Sign page not displayed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return blFlag;
    }
    @AndroidFindBy(id = "com.google.android.youtube:id/youtube_logo")
    public MobileElement youTubeLogo;
    public boolean isDisplayedYouTubeLogo(){
        boolean blFlag = false;
        try {
            if (youTubeLogo.isDisplayed()){
                System.out.println("Youtube logo displayed");
                blFlag = true;
            }else {
                System.out.println("Youtube logo not displayed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return blFlag;
    }






}
