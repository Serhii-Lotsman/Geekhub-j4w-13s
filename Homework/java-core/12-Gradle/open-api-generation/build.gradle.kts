plugins {
    id("org.openapi.generator") version "7.2.0"
}

buildscript {
    repositories {
        mavenCentral()
    }
}


openApiGenerate {
    generatorName.set("java")
    inputSpec.set("$projectDir/src/main/resources/openapi.yaml")
    generateApiTests.set(false)
    generateApiDocumentation.set(false)
}

