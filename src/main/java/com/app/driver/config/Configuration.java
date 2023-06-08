package com.app.driver.config;

import org.aeonbits.owner.Config;


//Add properties file path below
@Config.LoadPolicy(Config.LoadType.MERGE) // To load the properties files
@Config.Sources({"system:properties",
        "classpath:propertiesFile/general.properties",
        "classpath:propertiesFile/android.properties",
        "classpath:propertiesFile/web.properties"
})

public interface Configuration extends Config {
    @Key("android.test")
    String test();
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





//    WEB
    @Key("web.url")
    String webURL();
}
