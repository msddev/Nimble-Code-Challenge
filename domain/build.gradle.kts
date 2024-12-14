plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    // Paging Compose
    implementation(libs.paging.common.ktx)

    // Unit Test
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
}