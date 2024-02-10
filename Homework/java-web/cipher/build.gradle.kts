plugins {
    id("java-library")
    id("maven-publish")
}

version = "1.0.0"

repositories {
    mavenCentral()

    maven {
        url = uri("https://repsy.io/mvn/vrudas/slotsman-j4w-s13-repo")
        credentials {
            username = System.getenv("REPSY_LOGIN")
            password = System.getenv("REPSY_PASSWORD")
        }
    }
}

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
            groupId = "com.ciphers"
            version = "1.0.0"
            artifactId = "cipherAlgorithm"
            description = "A library for cryptographic algorithms."
        }
    }
    repositories {
        maven {
            url = uri("https://repsy.io/mvn/vrudas/slotsman-j4w-s13-repo")
            credentials {
                username = System.getenv("REPSY_LOGIN")
                password = System.getenv("REPSY_PASSWORD")
            }
        }
    }
}
