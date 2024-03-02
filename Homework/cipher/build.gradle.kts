plugins {
    id("java-library")
    id("maven-publish")
}

group = "com.cipher"
version = "0.0.1"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        register<MavenPublication>("CipherAlgorithm") {
            from(components["java"])
            pom {
                groupId = project.group.toString()
                artifactId = "cipher"
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
            url = uri("https://repsy.io/mvn/slotsman/cipher-storage-repository")
            credentials {
                username = System.getenv("LOGIN_REPSY") ?: providers.gradleProperty("username").get()
                password = System.getenv("PASSWORD_REPSY") ?: providers.gradleProperty("password").get()
            }
        }
    }
}
