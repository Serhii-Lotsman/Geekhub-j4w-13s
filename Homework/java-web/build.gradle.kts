plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
}

repositories {
    mavenCentral()
    repositories {
        maven {
            url = uri("https://repsy.io/mvn/slotsman/cipher-storage-repository")
            credentials {
                username = System.getenv("REPSY_LOGIN") ?: providers.gradleProperty("username").get()
                password = System.getenv("REPSY_PASSWORD") ?: providers.gradleProperty("password").get()
            }
        }
    }
}

dependencies {
    implementation("org.springframework:spring-jdbc:6.1.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.0.0")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("org.flywaydb:flyway-core:10.8.1")
    implementation("com.cipherAlgorithm:cipher:0.0.1")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.8.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.2")
}
