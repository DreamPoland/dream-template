repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":plugin-core:nms:api"))

    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")

    // -- dream-utilities --
    implementation("cc.dreamcode:utilities:1.4.5")
    implementation("cc.dreamcode:utilities-bukkit-adventure:1.4.5")
}