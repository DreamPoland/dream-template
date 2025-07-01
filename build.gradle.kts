plugins {
    `java-library`
    id("idea")
    id("com.gradleup.shadow") version "9.0.0-beta17"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17" apply false
}

idea {
    project.jdkName = "23"
}

allprojects {
    group = "cc.dreamcode.template"
    version = "1.0-InDEV"

    apply(plugin = "java-library")
    apply(plugin = "com.gradleup.shadow")

    repositories {
        mavenCentral()
        maven("https://repo.dreamcode.cc/releases")
        maven("https://storehouse.okaeri.eu/repository/maven-public")
    }
}

subprojects {
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        withSourcesJar()
        withJavadocJar()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    dependencies {
        /* General */
        val lombok = "1.18.38"
        compileOnly("org.projectlombok:lombok:$lombok")
        annotationProcessor("org.projectlombok:lombok:$lombok")
        testCompileOnly("org.projectlombok:lombok:$lombok")
        testAnnotationProcessor("org.projectlombok:lombok:$lombok")
    }
}

project(":plugin-core:nms").subprojects {

    val minor = name.split("_").getOrNull(1)?.toInt() ?: 0
    val patch = name.split("R").getOrNull(1)?.toInt() ?: 0

    if (name == "api" || minor < 17) {
        return@subprojects
    }

    apply(plugin = "io.papermc.paperweight.userdev")

    if (minor >= 21 || minor == 20 && patch >= 4) {
        java {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21

            withSourcesJar()
            withJavadocJar()
        }
    }
    else {
        java {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17

            withSourcesJar()
            withJavadocJar()
        }
    }
}

tasks.register("pluginVersion") {
    println(project.version)
}