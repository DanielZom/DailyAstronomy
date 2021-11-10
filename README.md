# Daily Astronomy

Daily Astronomy is a application to stay you up-to-date with the latest astronomy news. It has several other functions such as an image gallery of the universe, an object scroller of our solar system and a live stream of NASA's space ship.

## Installation

Use the android debug bridge [adb](https://developer.android.com/studio/command-line/adb) to install Daily Astronomy.

```bash
adb install example.apk
```

## Dependencies

```gradle
dependencies {
    implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation "androidx.core:core-ktx:$kotlinCoreVersion"
    implementation "androidx.appcompat:appcompat:$appCompatVersion"
    implementation "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
    implementation "androidx.recyclerview:recyclerview:$recyclerViewVersion"
    implementation "androidx.viewpager2:viewpager2:$viewPager2Version"
    implementation "com.google.android.material:material:$googleMaterialVersion"

    //Test
    testImplementation "junit:junit:$junitVersion"
    androidTestImplementation "androidx.test.ext:junit:$androidxJunitVersion"
    androidTestImplementation "androidx.test.espresso:espresso-core:$espressoVersion"

    //Koin
    implementation "org.koin:koin-android:$koinVersion"

    //Retrofit
    implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"
    implementation "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    implementation "com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion"

    //Okhttp
    //noinspection GradleDependency
    implementation "com.squareup.okhttp3:okhttp:$okhttpVersion"
    implementation "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
    implementation "com.squareup.okhttp3:okhttp-urlconnection:$okhttpVersion"

    //RxJava
    implementation "io.reactivex.rxjava3:rxandroid:$rxAndroidVersion"
    implementation "io.reactivex.rxjava3:rxjava:$rxJavaVersion"

    //Coil
    implementation "io.coil-kt:coil:$coilVersion"

    //Stetho
    implementation "com.facebook.stetho:stetho:$stethoVersion"
}
```

## Documentations, sources
- [user flow diagram](https://teams.microsoft.com/l/file/FD01946C-BB75-460F-A2F7-772C3472B9DC?tenantId=b41b72d0-4e9f-4c26-8a69-f949f367c91d&fileType=pdf&objectUrl=https%3A%2F%2Fepam.sharepoint.com%2Fsites%2FEPAMSpaceRmobileteam%2FShared%20Documents%2FGeneral%2FDaily%20Astronomy%20flow.pdf&baseUrl=https%3A%2F%2Fepam.sharepoint.com%2Fsites%2FEPAMSpaceRmobileteam&serviceName=teams&threadId=19:062154285c164e66a34c136ce2ecd662@thread.tacv2&groupId=7c08a3b2-9a94-4080-8d1d-90734c988328)
- [high level documentation](https://teams.microsoft.com/l/file/ECD5B0B8-91AC-45E6-AF3E-628894538494?tenantId=b41b72d0-4e9f-4c26-8a69-f949f367c91d&fileType=docx&objectUrl=https%3A%2F%2Fepam.sharepoint.com%2Fsites%2FEPAMSpaceRmobileteam%2FShared%20Documents%2FGeneral%2FAndroid_Daily_Astronomy_high_level_doc.docx&baseUrl=https%3A%2F%2Fepam.sharepoint.com%2Fsites%2FEPAMSpaceRmobileteam&serviceName=teams&threadId=19:062154285c164e66a34c136ce2ecd662@thread.tacv2&groupId=7c08a3b2-9a94-4080-8d1d-90734c988328)
- [design resources](https://epam.sharepoint.com/sites/EPAMSpaceRmobileteam/Shared%20Documents/General/Daily_Astronomy_design.rar)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

**Please make sure to update tests as appropriate.**

## License
There is no license for this application.