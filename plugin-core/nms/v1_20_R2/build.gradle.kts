repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    compileOnly(project(":plugin-core:nms:api"))

    paperweight.paperDevBundle("1.20.2-R0.1-SNAPSHOT")
}