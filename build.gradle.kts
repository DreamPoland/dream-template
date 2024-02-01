plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

allprojects {
    group = "cc.dreamcode.template"
    version = "1.0-InDEV"

    apply(plugin = "java-library")

    repositories {
        /* Libraries */
        mavenCentral()
        maven("https://repo.dreamcode.cc/releases")
        maven("https://storehouse.okaeri.eu/repository/maven-public")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        withSourcesJar()
        withJavadocJar()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

subprojects {
    apply(plugin = "com.github.johnrengelman.shadow")

    dependencies {
        /* General */
        val lombok = "1.18.30"
        compileOnly("org.projectlombok:lombok:$lombok")
        annotationProcessor("org.projectlombok:lombok:$lombok")
        testCompileOnly("org.projectlombok:lombok:$lombok")
        testAnnotationProcessor("org.projectlombok:lombok:$lombok")
    }
}