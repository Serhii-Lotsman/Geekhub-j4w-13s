dependencies {
    implementation(project(":Coursework:repository"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-security")
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
