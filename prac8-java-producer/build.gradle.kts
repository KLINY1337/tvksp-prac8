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

    implementation(libs.spring.jdbc)
    implementation(libs.postgresql)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    runtimeOnly(libs.liquibase.core)
}