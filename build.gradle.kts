plugins {
    id("java")

    id("com.github.johnrengelman.shadow") version "8.1.1"

}

group = "org.skillissue"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

tasks.shadowJar {
    archiveBaseName.set("SudokuSolver")
    archiveVersion.set("1.0.0")
    archiveClassifier.set("all") // Optional

    manifest {
        attributes["Main-Class"] = "org.skillissue.Main" // Set the main class here
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.json:json:20090211")
    implementation("com.google.code.gson:gson:2.7")
    implementation("de.sfuhrm:sudoku:5.0.1")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")

    implementation("org.apache.httpcomponents:httpclient:4.5")

    implementation("commons-codec:commons-codec:1.18.0")

}

tasks.test {
    useJUnitPlatform()
}