repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://repo.codemc.io/repository/maven-public")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(project(":plugin-core:nms:api"))

    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")

    // -- dream-utilities --
    implementation("cc.dreamcode:utilities:1.5.1")
    implementation("cc.dreamcode:utilities-bukkit-adventure:1.5.1")
}