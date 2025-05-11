repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://repo.codemc.io/repository/maven-public")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    compileOnly(project(":plugin-core:nms:api"))

    compileOnly("org.spigotmc:spigot:1.16.5-R0.1-SNAPSHOT")
}