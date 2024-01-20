plugins {
    id("java")
}

val gzipArchiver = tasks.register<Tar>("gzipArchive") {
    group = "custom-Gzip"
    description = "Archive all *.text files into a tar archive with *.txt"

    doFirst {
        println("Compressing text files...")
    }

    from(layout.projectDirectory.dir("src/main/resources")) {
        include("**/*.text")
        includeEmptyDirs = false
        rename { it.replace(".text", ".txt") }
    }
    compression = Compression.GZIP
    destinationDirectory.set(file("build/archive"))

    doLast {
        println("Compressing complete")
    }
}

tasks.register("gzipRunner") {
    group = "custom-Gzip"
    description = "Run Gzip-archiver"
    dependsOn(gzipArchiver)
}
