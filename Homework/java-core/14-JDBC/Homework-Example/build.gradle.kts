dependencies {
    implementation(platform("org.springframework:spring-framework-bom:6.1.3"))

    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-context")

    implementation("org.springframework:spring-jdbc")
    implementation("org.postgresql:postgresql:42.7.1")
    implementation("com.zaxxer:HikariCP:5.1.0")

    implementation("org.flywaydb:flyway-core:10.6.0")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.6.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.24.2")
}