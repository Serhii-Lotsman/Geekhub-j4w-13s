plugins {
    id("java")
    id("maven-publish")
}

group = "com.cipherStorage"
version = "1.0.0"

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

val sourceJar by tasks.registering(Jar::class) {
    from(mainSourceSet.java)
    archiveClassifier.set("CipherStorage")
}

publishing {
    publications {
        create<MavenPublication>("publish") {
            from(components["java"])
            artifact(sourceJar)
            pom {
                name.set("Ciphers")
                url.set("https://repsy.io/mvn/vrudas/slotsman-j4w-s13-repo")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("slotsman")
                        name.set("Serhii")
                        email.set("lotsman.961@gmail.com")
                    }
                }
            }
        }
    }
}
