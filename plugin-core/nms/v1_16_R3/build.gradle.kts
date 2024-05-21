repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(project(":plugin-core:nms:api"))

    compileOnly("org.spigotmc:spigot:1.16.5-R0.1-SNAPSHOT")

    // -- dream-utilities --
    implementation("cc.dreamcode:utilities:1.4.1")
    implementation("cc.dreamcode:utilities-bukkit:1.4.1")
}