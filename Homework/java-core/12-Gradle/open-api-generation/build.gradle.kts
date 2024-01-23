plugins {
    id("org.openapi.generator") version "7.2.0"
}

dependencies {
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("com.google.code.gson:gson:2.10.1")
}

openApiGenerate {
    generatorName.set("java")
    inputSpec.set("$projectDir/src/main/resources/openapi.yaml")

    outputDir.set("$projectDir/src/main/java/org/geekhub/model")
    generateModelTests.set(false)
    generateModelDocumentation.set(false)

    globalProperties.set(mapOf(
            "apis" to "false",
            "invokers" to "false",
            "models" to ""
    ))
}
