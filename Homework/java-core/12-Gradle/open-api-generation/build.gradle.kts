plugins {
    id("org.openapi.generator") version "7.2.0"
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath("org.openapitools:openapi-generator-gradle-plugin:7.2.0")
    }
}

apply(plugin = ("org.openapi.generator"))

tasks.register("openAPI-starter") {
    openApiGenerate {
        group = "custom-OpenAPI"
        generatorName.set("kotlinOpenAPI")
        inputSpec.set("src/main/resources/openapi.yaml")
        outputDir.set("build/openapi-generated")
        modelPackage.set("build/openapi-generated/model")
        generateApiDocumentation.set(false)
        generateApiTests.set(false)
    }

}
