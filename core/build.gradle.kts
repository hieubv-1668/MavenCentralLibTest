plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
    id("org.jreleaser") version "1.5.1"

}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "io.github.hieubv-1668"
            artifactId = "core"
            description = "Create Core lib test"
            version = "1.0.0"
            afterEvaluate {
                from(components["release"])
            }
        }
        withType<MavenPublication> {
            pom {
                packaging = "jar"
                name.set("core")
                description.set("Core lib test")
                url.set("https://github.com/hieubv-1668/MavenCentralLibTest")
                inceptionYear.set("2023")
                licenses {
                    license {
                        name.set("MIT license")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("hieubv")
                        name.set("Van Hieu")
                        email.set("bui.van.hieu@sun-asterisk.com")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:hieubv-1668/MavenCentralLibTest.git")
                    developerConnection.set("scm:git:ssh:git@github.com:hieubv-1668/MavenCentralLibTest")
                    url.set("https://github.com/hieubv-1668/MavenCentralLibTest")
                }
            }
        }
    }
    repositories {
        maven {
            url = layout.buildDirectory.dir("release").get().asFile.toURI()
        }
    }
}

jreleaser {
    project {
        copyright.set("Van Hieu")
    }
    gitRootSearch.set(true)
    signing {
        active.set(org.jreleaser.model.Active.ALWAYS)
        armored.set(true)
    }
    deploy {
        maven {
            nexus2 {
                create("maven-central") {
                    active.set(org.jreleaser.model.Active.ALWAYS)
                    url.set("https://s01.oss.sonatype.org/service/local")
                    closeRepository.set(false)
                    releaseRepository.set(false)
                    stagingRepositories.add("build/release")
                }
            }
        }
    }
}

android {
    namespace = "com.hieubv.libtest"
    compileSdk = 33

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}