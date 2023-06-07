package com.app.driver.config;

import org.aeonbits.owner.Config;


//Add properties file path below
@Config.Sources({
        "file:./config/properties/general.properties",
        "file:./config/properties/android.properties"
})

public interface Configuration extends Config {

/*    @Key("Dummy");
    String dummyName();*/;
    @Key("app.android.path")
    String appPath();
    @Key("app.android.appPackage")
    String appPackageName();
    @Key("app.android.appActivity")
    String appActivityName();

    @Key("general.run.ip")
    String ip();
    @Key("run.port")
    String port();
    @Key("install.app")
    String installApp();


}
