dependencies {
    implementation(project(":Coursework:repository"))
}

springBoot {
    mainClass.set("org.geekhub.application.CrewCraftStarter")
}

tasks.test {
    useJUnitPlatform()
}
