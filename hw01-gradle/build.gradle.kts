import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:33.4.8-jre")
    implementation("org.slf4j:slf4j-simple:2.0.17")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("hw01-gradle")
        archiveVersion.set("1.0-SNAPSHOT")
        archiveClassifier.set("")
        manifest {
            attributes["Main-Class"] = "ru.anbn.HelloOtus"
        }
    }

    build {
        dependsOn(shadowJar)
    }
}
