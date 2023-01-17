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
    implementation("io.coil-kt:coil:2.1.0")
    implementation("com.makeramen:roundedimageview:2.3.0")
}
