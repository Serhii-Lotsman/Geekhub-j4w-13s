dependencies {
    implementation(project(":Coursework:domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.0.0")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
}

springBoot {
    mainClass.set("org.geekhub.application.CrewCraftStarter")
}

tasks.test {
    useJUnitPlatform()
}
