pluginManagement {
    repositories {
        maven {
            // GTNH plugins are not available from the general-purpose mirrors.
            name = "GTNH Maven"
            url = uri("https://nexus.gtnewhorizons.com/repository/public/")
            mavenContent {
                includeGroup("com.gtnewhorizons")
                includeGroupByRegex("com\\.gtnewhorizons\\..+")
            }
        }
        maven {
            name = "Aliyun Gradle Plugin"
            url = uri("https://maven.aliyun.com/repository/gradle-plugin/")
        }
        maven {
            name = "Aliyun Maven"
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("com.gtnewhorizons.gtnhsettingsconvention") version("2.0.34")
}
