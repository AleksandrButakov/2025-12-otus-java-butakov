plugins {
    id("java")
}

group = "ru.anbn"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // или твоя версия
    }
}

repositories {
    mavenCentral()
}

val asmVersion = "9.9.1"

dependencies {
    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-classic")
    implementation("org.ow2.asm:asm:$asmVersion")
    implementation("org.ow2.asm:asm-commons:$asmVersion")
}

tasks.jar {
    manifest {
        attributes(
            "Premain-Class" to "ru.anbn.logging.asm.LogAgent"
        )
    }
}
