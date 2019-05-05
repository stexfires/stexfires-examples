group = "stexfires"
version = "0.1.0"

plugins {
    java
    `project-report`
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains:annotations:17.0.0")
    implementation("stexfires:stexfires:0.1.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

val sharedManifest = the<JavaPluginConvention>().manifest {
    attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
    )
}

tasks {
    jar {
        manifest = project.the<JavaPluginConvention>().manifest {
            from(sharedManifest)
        }
    }
    javadoc {
        options.encoding = "UTF-8"
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    compileTestJava {
        options.encoding = "UTF-8"
    }
}

