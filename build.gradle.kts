plugins {
    java
    `project-report`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.1")
    implementation("stexfires:stexfires-util:0.1.0")
    implementation("stexfires:stexfires-record:0.1.0")
    implementation("stexfires:stexfires-data:0.1.0")
    implementation("stexfires:stexfires-io:0.1.0")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
    // options.compilerArgs.add("-Xlint:preview")
    //options.compilerArgs.add("-Xlint:unchecked")
    //options.compilerArgs.add("-Xlint:deprecation")
}

tasks.withType<Test>().configureEach {
    jvmArgs("--enable-preview")
}

tasks.withType<JavaExec>().configureEach {
    jvmArgs("--enable-preview")
}

tasks.withType<Javadoc> {
    val javadocOptions = options as CoreJavadocOptions

    javadocOptions.addStringOption("source", "19")
    javadocOptions.addBooleanOption("-enable-preview", true)
}

group = "stexfires"
version = "0.1.0"
