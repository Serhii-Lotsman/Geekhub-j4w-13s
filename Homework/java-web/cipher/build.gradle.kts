plugins {
    id("java")
    id("maven-publish")
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

val mainSourceSet = sourceSets.getByName("main")

val sourceJar = tasks.register<Jar>("cipher") {
    from(mainSourceSet.java)
}

publishing {
    repositories {
        maven {
            name = "CipherStorage"
            url = uri("https://repsy.io/mvn/vrudas/slotsman-j4w-s13-repo")
            credentials {
                username = project.findProperty("REPSY_USER")?.toString() ?: System.getenv("REPSY_USER")
                password = project.findProperty("REPSY_PASSWORD")?.toString() ?: System.getenv("REPSY_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("CipherPublication") {
            from(components["java"])
            artifact(sourceJar)
            groupId = "com.ciphers"
            version = "1.0.0"
            artifactId = "cipherAlgorithms"
            description = "A library for cryptographic algorithms."
            pom {
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
            }
        }
    }
}
