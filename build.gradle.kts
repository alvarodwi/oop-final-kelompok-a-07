plugins {
    application
    id("org.openjfx.javafxplugin") version "0.0.10"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}

application {
    mainClass.set("team.emergence._15puzzle.App")
}

javafx {
    modules("javafx.controls", "javafx.fxml", "javafx.swing")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("_15puzzle.jar")
    minimize()
}
