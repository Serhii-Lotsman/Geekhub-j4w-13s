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
    implementation("org.springframework:spring-context:6.1.3")
    implementation("org.springframework:spring-jdbc:6.1.0")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("org.flywaydb:flyway-core:9.22.3")
    implementation("com.cipherAlgorithm:cipher:0.0.1")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation(platform("org.mockito:mockito-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.24.2")
}
