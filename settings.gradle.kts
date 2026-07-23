pluginManagement {
    repositories {
        maven {
            name = "Aliyun Gradle Plugin"
            url = uri("https://maven.aliyun.com/repository/gradle-plugin/")
        }
        maven {
            name = "Aliyun Maven"
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        maven {
            // RetroFuturaGradle
            name = "GTNH Maven"
            url = uri("https://nexus.gtnewhorizons.com/repository/public/")
            mavenContent {
                includeGroup("com.gtnewhorizons")
                includeGroupByRegex("com\\.gtnewhorizons\\..+")
            }
        }
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("com.gtnewhorizons.gtnhsettingsconvention") version("1.0.41")
}
