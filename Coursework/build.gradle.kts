plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.geekhub.crew-craft"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.0.0")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    implementation("org.flywaydb:flyway-core:10.8.1")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.8.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.2")
}

tasks.test {
    useJUnitPlatform()
}
