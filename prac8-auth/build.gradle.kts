plugins {
    java
    application
    alias(libs.plugins.springframework.boot)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.redis)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    implementation(libs.spring.cloud.starter.openfeign)
}