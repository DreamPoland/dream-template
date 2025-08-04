pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "dream-template"

include(":plugin-core")

include(":plugin-core:nms:api")
include(":plugin-core:nms:v1_8_R3")
include(":plugin-core:nms:v1_12_R1")
include(":plugin-core:nms:v1_16_R3")
include(":plugin-core:nms:v1_17_R1")
include(":plugin-core:nms:v1_18_R2")
include(":plugin-core:nms:v1_19_R2")
include(":plugin-core:nms:v1_19_R3")
include(":plugin-core:nms:v1_20_R1")
include(":plugin-core:nms:v1_20_R2")
include(":plugin-core:nms:v1_20_R3")
include(":plugin-core:nms:v1_20_R5")
include(":plugin-core:nms:v1_21_R1")
include(":plugin-core:nms:v1_21_R2")
include(":plugin-core:nms:v1_21_R3")
include(":plugin-core:nms:v1_21_R4")
include(":plugin-core:nms:v1_21_R5")