
import dependencies.AndroidTestDependencies
import dependencies.Dependencies
import dependencies.TestDependencies

plugins {
    id(Commons.ANDROID_FEATURE_LIBRARY)
}

dependencies {
    implementation(Dependencies.MATERIAL)
    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.EXT_JUNIT)
    androidTestImplementation(AndroidTestDependencies.ESPRESSO)
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("com.airbnb.android:lottie:3.4.0")
}
