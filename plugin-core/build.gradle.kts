import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    // -- bukkit-versions --
    project(":plugin-core:mcversion").dependencyProject.subprojects.forEach {
        implementation(it)
    }

    // -- spigot api -- (base)
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")

    // -- dream-platform --
    implementation("cc.dreamcode.platform:core:1.11.4")
    implementation("cc.dreamcode.platform:bukkit:1.11.4")
    implementation("cc.dreamcode.platform:bukkit-config:1.11.4")
    implementation("cc.dreamcode.platform:dream-command:1.11.4")
    implementation("cc.dreamcode.platform:persistence:1.11.4")

    // -- dream-utilities --
    implementation("cc.dreamcode:utilities:1.3.0")
    implementation("cc.dreamcode:utilities-bukkit:1.3.1")

    // -- dream-notice --
    implementation("cc.dreamcode.notice:core:1.4.9")
    implementation("cc.dreamcode.notice:minecraft:1.4.9")
    implementation("cc.dreamcode.notice:minecraft-adventure:1.4.9")
    implementation("cc.dreamcode.notice:bukkit-adventure:1.4.9")
    implementation("cc.dreamcode.notice:bukkit-adventure-serializer:1.4.9")

    // -- dream-command --
    implementation("cc.dreamcode.command:core:2.0.2")
    implementation("cc.dreamcode.command:bukkit:2.0.2")

    // -- dream-menu --
    implementation("cc.dreamcode.menu:core:1.2.7")
    implementation("cc.dreamcode.menu:bukkit:1.2.7")
    implementation("cc.dreamcode.menu:bukkit-serdes:1.2.7")

    // -- configs--
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:5.0.1")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:5.0.1")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:5.0.1")

    // -- persistence data --
    implementation("eu.okaeri:okaeri-persistence-flat:2.0.3")
    implementation("eu.okaeri:okaeri-persistence-jdbc:2.0.3")
    implementation("eu.okaeri:okaeri-persistence-mongo:2.0.3")

    // -- persistence data configure --
    implementation("eu.okaeri:okaeri-configs-json-gson:5.0.1")
    implementation("eu.okaeri:okaeri-configs-json-simple:5.0.1")

    // -- injector --
    implementation("eu.okaeri:okaeri-injector:2.1.0")

    // -- placeholders --
    implementation("eu.okaeri:okaeri-placeholders-core:5.0.1")

    // -- tasker (easy sync/async scheduler) --
    implementation("eu.okaeri:okaeri-tasker-bukkit:2.1.0-beta.3")

    // -- Multi-Version Items helper --
    implementation("com.github.cryptomorin:XSeries:9.10.0")
}

tasks.withType<ShadowJar> {

    archiveFileName.set("Dream-Template-${project.version}.jar")

    minimize()

    relocate("com.cryptomorin", "cc.dreamcode.template.libs.com.cryptomorin")
    relocate("eu.okaeri", "cc.dreamcode.template.libs.eu.okaeri")
    relocate("net.kyori", "cc.dreamcode.template.libs.net.kyori")

    relocate("cc.dreamcode.platform", "cc.dreamcode.template.libs.cc.dreamcode.platform")
    relocate("cc.dreamcode.utilities", "cc.dreamcode.template.libs.cc.dreamcode.utilities")
    relocate("cc.dreamcode.menu", "cc.dreamcode.template.libs.cc.dreamcode.menu")
    relocate("cc.dreamcode.command", "cc.dreamcode.template.libs.cc.dreamcode.command")
    relocate("cc.dreamcode.notice", "cc.dreamcode.template.libs.cc.dreamcode.notice")

    relocate("org.bson", "cc.dreamcode.template.libs.org.bson")
    relocate("com.mongodb", "cc.dreamcode.template.libs.com.mongodb")
    relocate("com.zaxxer", "cc.dreamcode.template.libs.com.zaxxer")
    relocate("org.slf4j", "cc.dreamcode.template.libs.org.slf4j")
    relocate("org.json", "cc.dreamcode.template.libs.org.json")
    relocate("com.google.gson", "cc.dreamcode.template.libs.com.google.gson")
}