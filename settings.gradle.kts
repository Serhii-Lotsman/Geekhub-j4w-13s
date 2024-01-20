pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.21"
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
    }
}

rootProject.name = "GeekHub-J4W-13"

include("Homework:java-core:01-Introduction")
include("Homework:java-core:02-Basics")
include("Homework:java-core:03-OOP")
include("Homework:java-core:04-Collections")
include("Homework:java-core:05-Exception-Files")
include("Homework:java-core:06-Unit-Test")
include("Homework:java-core:07-Optional-Stream-API")
include("Homework:java-core:08-Practice:LearnIt")
include("Homework:java-core:09-Concurrency")
include("Homework:java-core:10-Reflection-API")
include("Homework:java-core:12-Gradle")
include("Homework:java-core:12-Gradle:tar-tasks")
include("Homework:java-core:12-Gradle:open-api-generation")


include ("Homework:java-web")

include ("Coursework")