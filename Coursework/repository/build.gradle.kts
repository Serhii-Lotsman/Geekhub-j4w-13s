dependencies {
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core:10.8.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.8.1")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

tasks.test {
    useJUnitPlatform()
}
