repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(project(":plugin-core:nms:api"))

    paperweight.paperDevBundle("1.18.2-R0.1-SNAPSHOT")

    // -- dream-utilities --
    implementation("cc.dreamcode:utilities:1.4.2")
    implementation("cc.dreamcode:utilities-bukkit-adventure:1.4.2")
}