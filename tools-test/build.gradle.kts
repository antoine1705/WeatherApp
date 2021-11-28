plugins {
    id("java-library")
    id("kotlin")
}

configurations.create("testArtifacts") {
    extendsFrom(configurations["testImplementation"])
}

tasks.register("testJar", Jar::class.java) {
    dependsOn("testClasses")
    classifier += "test"
    from(sourceSets["test"].output)
}

artifacts {
    add("testArtifacts", tasks.named<Jar>("testJar") )
}

dependencies {
    implementation(Kotlin.stdlib)
    implementation(Koin.core)
    implementation(Koin.core_ext)
    implementation(Coroutines.core)
    implementation(ArchCore.testing)

    testImplementation(Common.mockk)
    testImplementation(Common.junit)
    testImplementation(Coroutines.test)
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}