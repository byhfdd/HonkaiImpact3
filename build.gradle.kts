plugins {
    id("com.gtnewhorizons.gtnhconvention")
}


tasks.named<JavaExec>("runClient") {
    jvmArgs("-noverify")
}

tasks.named<JavaExec>("runServer") {
    jvmArgs("-noverify")
}

