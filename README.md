# Shadowless




# Getting started

Add a dependency in `build.gradle` in root of your project as following.

``` java
dependencies {
    classpath 'com.paul.song.library:shadowless:(insert latest version)'
}
```

Apply plugin in `app` module of `build.gradle`.

``` java
apply plugin: 'com.paul.song.analytics'
```

Implementation Shadowless in `app` module of `build.gradle`.

``` java
implementation 'com.paul.song.library:shadowless-sdk:(insert latest version)'
```

Initialize `ShadowlessDataAPI` in `YourApplication::onCreate()`.

``` java
@Override
public void onCreate() {
    super.onCreate();
    ShadowlessDataAPI.init(this);
}
```

Finally, collect your datas and have fun!
