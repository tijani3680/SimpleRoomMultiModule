import dependencies.AndroidTestDependencies
import dependencies.Dependencies
import dependencies.TestDependencies
import extensions.implementation

plugins {
    id(Commons.ANDROID_LIBRARY)
}

dependencies {
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.CONSTRAIN_LAYOUT)

    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.EXT_JUNIT)
    androidTestImplementation(AndroidTestDependencies.ESPRESSO)
    implementation("io.michaelrocks:libphonenumber-android:8.12.39")
}
