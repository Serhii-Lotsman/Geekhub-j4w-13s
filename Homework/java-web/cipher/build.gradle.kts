plugins {
    id("java")
}

tasks.jar {
    archiveBaseName.set("cipherStorage")
    archiveVersion.set("1.0.0")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
