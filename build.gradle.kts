import java.math.BigDecimal

plugins {
    id("name.remal.sonarlint") version "3.3.11"
    kotlin("jvm") version "1.9.22"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "checkstyle")
    apply(plugin = "jacoco")
    apply(plugin = "name.remal.sonarlint")

    group = "org.geekhub"

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("com.google.code.findbugs:jsr305:3.0.2")
    }

    tasks.named("build") {
        dependsOn("checkstyleMain", "sonarlintMain")
    }

    tasks.named<JavaCompile>("compileJava") {
        options.encoding = "UTF-8"
    }

    tasks.named<JavaCompile>("compileTestJava") {
        options.encoding = "UTF-8"
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/java")
    }

    tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
        dependsOn("jacocoTestReport")
        violationRules {
            isFailOnViolation = true
            rule {
                limit {
                    minimum = BigDecimal("0.85")
                }
            }
        }
    }

    sonarLint {
        languages {
            include("java")
        }

        rules {
            disable(
                    "java:S1192", // Allow string literals to be duplicated
                    "java:S1197", // Allow constants to be defined in interfaces
                    "java:S1118", // Allow utility classes to have a private constructor
                    "java:S106",  // Allow system out and err to be used
                    "java:S107",  // Allow constructors with more than 7 parameters
                    "java:S3776", // Allow methods with more than 15 lines
                    "java:S1135"  // Allow TODO comments
            )
        }
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}

kotlin {
    jvmToolchain(17)
}
