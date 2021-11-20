plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.6.0"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1.1-jre")

    /* exposed */
    implementation(platform("org.jetbrains.exposed:exposed-bom:0.35.3"))
    implementation("org.jetbrains.exposed:exposed-dao")
    implementation("org.jetbrains.exposed:exposed-jdbc") // essential for using exposed(without this, the application wont start)
    implementation("org.jetbrains.exposed:exposed-java-time")

    /* unit testing */
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")


    implementation("com.h2database:h2:1.4.200")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}