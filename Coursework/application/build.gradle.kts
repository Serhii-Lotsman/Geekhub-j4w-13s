dependencies {
    implementation(project(":Coursework:rest-api"))
    implementation("org.springframework.boot:spring-boot-starter-validation")
    //implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.flywaydb:flyway-core:10.8.1")
}

tasks.test {
    useJUnitPlatform()
}
