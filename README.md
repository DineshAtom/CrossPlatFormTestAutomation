# CrossPlatFormTestAutomation
Framework for Generic Cross Platform Test Automation Using TestNG, Maven, Java, Selenium, Appium

* [Pre-conditions](#pre-conditions)
    * [What you need](#what-you-need)
        * [Android specific](#android-specific)
            * [Configurations](#configurations)
            * [Inspect elements on Android](#inspect-elements-on-android)


## Pre-conditions

### What you need
1. Java JDK 11 installed and configured (with `JAVA_HOME` and `PATH` configured)
2. IDE (to import this project using Maven)
3. Android SDK (for Android execution | with `ANDROID_HOME` and `PATH` configured)
4. Appium installed through npm

#### Android specific

##### Configurations
To execute the examples over the Android platform you'll need:
* Android SDK
* Updated _Build Tools_, _Platform Tools_ and, at least, one _System Image (Android Version)_
* Configure Android Path on your environment variables
    * ANDROID_HOME: root android sdk directory
    * PATH: ANDROID_HOME + the following paths = _platform-tools_, _tools_, _tools/bin_

##### Inspect elements on Android
You can use the [uiautomatorviewer](https://developer.android.com/training/testing/ui-testing/uiautomator-testing.html) to inspect elements on Android devices.
or you can use [Appium Desktop](https://github.com/appium/appium-desktop/releases)