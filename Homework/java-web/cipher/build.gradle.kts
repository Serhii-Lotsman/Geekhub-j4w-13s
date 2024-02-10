plugins {
    id("java")
    id("maven-publish")
}

repositories {
    maven(url = "https://repsy.io/mvn/vrudas/slotsman-j4w-s13-repo") {
        credentials {
            username = System.getenv("REPSY_LOGIN")
            password = System.getenv("REPSY_PASSWORD")
        }
    }
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

val publishToRepsy by tasks.registering {
    dependsOn("publish")
    doLast {
        println("Publishing to repsy.io Maven repository...")
    }
}

publishing {
    publications {
        create<MavenPublication>("Cipher") {
            from(components["java"])
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

