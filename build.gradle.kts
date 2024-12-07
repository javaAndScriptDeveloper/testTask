plugins {
    id("java")
    id ("org.springframework.boot") version "3.4.0"
    id ("io.spring.dependency-management") version "1.1.6"
    id ("com.diffplug.spotless") version "6.25.0"
}

group = "poc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
    mavenLocal ()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor ("org.projectlombok:lombok:1.18.36")
    annotationProcessor ("org.projectlombok:lombok")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    implementation ("org.springframework.boot:spring-boot-starter")
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("org.springframework.boot:spring-boot-starter-aop")
    implementation ("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.cloud:spring-cloud-starter:4.2.0")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.2.0")
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
    implementation ("org.mapstruct:mapstruct:1.6.3")
    implementation("org.liquibase:liquibase-core:4.30.0")
    runtimeOnly ("org.postgresql:postgresql")

    testAnnotationProcessor ("org.projectlombok:lombok:1.18.36")

    testCompileOnly ("org.projectlombok:lombok:1.18.36")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
    testImplementation("org.wiremock:wiremock:3.10.0")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testImplementation (files("src/test/resources"))
}

tasks.withType <JavaCompile> {
    val compilerArgs = options.compilerArgs
    compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
}

tasks.named <Test>("test") {
    useJUnitPlatform()
    systemProperty("junit.jupiter.extensions.autodetection.enabled", true)
}

spotless {
    java {
        target("src/*/java/**/*.java")
        palantirJavaFormat ()
        importOrder ()
        removeUnusedImports ()
    }
}