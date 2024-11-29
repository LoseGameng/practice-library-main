plugins {
    java
    application
    id("io.freefair.lombok") version "8.6"
}

group = "io.hexlet"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "io.hexlet.App"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database:h2:2.2.224")
    implementation("org.postgresql:postgresql:42.7.2")
}