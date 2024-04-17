dependencies {
    implementation(project(":Coursework:rest-api"))
    implementation(project(":Coursework:domain"))
    implementation("org.flywaydb:flyway-core:10.8.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.0.0")
}

tasks.bootJar {
    enabled = true
}

tasks.test {
    useJUnitPlatform()
}
