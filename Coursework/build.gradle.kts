plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.geekhub.crew-craft"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.0.0")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    implementation("org.flywaydb:flyway-core:10.8.1")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    testImplementation("org.springframework.security:spring-security-test")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.8.1")
    compileOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}
