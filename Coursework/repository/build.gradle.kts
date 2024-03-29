dependencies {
    implementation("org.flywaydb:flyway-core:10.8.1")
    implementation("org.postgresql:postgresql")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.8.1")
}

tasks.test {
    useJUnitPlatform()
}
