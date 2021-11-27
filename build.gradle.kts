plugins {
    application
    id("org.openjfx.javafxplugin") version "0.0.10"
}

repositories {
    mavenCentral()

    jcenter()
    maven("https://nexus.gluonhq.com/nexus/content/repositories/releases/")
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
