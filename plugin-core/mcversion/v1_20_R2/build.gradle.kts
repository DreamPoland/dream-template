repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(project(":plugin-core:mcversion:api"))

    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
}