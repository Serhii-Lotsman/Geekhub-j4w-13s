dependencies {
    implementation(project(":Coursework:rest-api"))
    implementation("org.flywaydb:flyway-core:10.8.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    //implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}

tasks.bootJar {
    enabled = true
}

tasks.test {
    useJUnitPlatform()
}
