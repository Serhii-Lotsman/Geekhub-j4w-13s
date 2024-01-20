dependencies {
    implementation("org.apache.httpcomponents:httpclient:4.5.13") {
        exclude(group = "commons-codec", module = "commons-codec")
    }
    implementation("commons-codec:commons-codec:1.13")
    implementation ("com.google.code.gson:gson:2.10.1")

    testImplementation (platform("org.junit:junit-bom:5.9.1"))
    testImplementation ("org.junit.jupiter:junit-jupiter")
    testImplementation ("org.mockito:mockito-core:5.7.0")
    testImplementation ("org.mockito:mockito-junit-jupiter:2.17.0")
    testImplementation ("org.assertj:assertj-core:3.8.0")
}
