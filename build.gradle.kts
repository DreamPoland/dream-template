plugins {
    `java-library`
    id("idea")
    id("com.gradleup.shadow") version "9.5.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21" apply false
}

idea {
    project.jdkName = "25"
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
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }

        withSourcesJar()
        withJavadocJar()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    tasks.withType<Javadoc> {
        options.encoding = "UTF-8"
        isFailOnError = false
    }

    dependencies {
        /* General */
        val lombok = "1.18.46"
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

    configure<io.papermc.paperweight.userdev.PaperweightUserExtension> {
        javaLauncher.set(javaToolchains.launcherFor {
            languageVersion.set(JavaLanguageVersion.of(if (minor >= 26) 25 else 21))
        })
    }

    if (minor >= 26) {
        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(25))
            }

            withSourcesJar()
            withJavadocJar()
        }
        tasks.matching { it.name == "reobfJar" }.configureEach {
            enabled = false
        }
    }
    else if (minor >= 21 || minor == 20 && patch >= 4) {
        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(21))
            }

            withSourcesJar()
            withJavadocJar()
        }
    }
    else {
        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(17))
            }

            withSourcesJar()
            withJavadocJar()
        }
    }
}

tasks.register("pluginVersion") {
    println(project.version)
}