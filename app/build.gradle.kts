
import dependencies.AndroidTestDependencies
import dependencies.Dependencies
import dependencies.TestDependencies

plugins {
    id(Commons.ANDROID_APPLICATION)
}

dependencies {
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.CONSTRAIN_LAYOUT)
    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.EXT_JUNIT)
    androidTestImplementation(AndroidTestDependencies.ESPRESSO)
    implementation("com.airbnb.android:lottie:3.5.0")
}
