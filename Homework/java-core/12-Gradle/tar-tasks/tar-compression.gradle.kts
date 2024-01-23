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
    archiveBaseName.set("archivedFiles")
    destinationDirectory.set(file("build/archive"))

    doLast {
        println("Compressing complete")
    }
}

tasks.register("archiveRunner") {
    group = "custom-Gzip"
    description = "Run Gzip-archiver"
    dependsOn(gzipArchiver)
}

val gzipDecompressor = tasks.register<Copy>("gzipDecompress") {
    group = "custom-Gzip"
    description = "Decompress all files into the test directory"

    doFirst {
        println("Unpacking txt files...")
    }

    from(project.tarTree("build/archive/archivedFiles.tgz"))
    into(layout.projectDirectory.dir("src/test/resources"))

    doLast {
        println("Files successfully unpacked")
    }

    dependsOn(gzipArchiver)
}

tasks.register("decompressRunner") {
    group = "custom-Gzip"
    description = "Run Gzip-archiver"
    dependsOn(gzipDecompressor)
}

tasks.register<Delete>("cleanup") {
    group = "custom-Gzip"
    description = "Delete all files from archive and test directories"

    doLast {
        project.delete("build/archive", "src/test/resources")
        println("Cleanup complete.")
    }

    dependsOn(gzipArchiver, gzipDecompressor)
}
