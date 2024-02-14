plugins {
    id("java-library")
    id("maven-publish")
}

group = "com.ciphers"
version = "1.0.0"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        register<MavenPublication>("MavenCipher") {
            from(components["java"])
            pom {
                groupId = project.group.toString()
                version = project.version.toString()
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            url = uri("https://repsy.io/slotsman/maven/cipher-storage-repository")
            credentials {
                username = System.getenv("REPSY_LOGIN")
                password = System.getenv("REPSY_PASSWORD")
            }
        }
    }
}
