import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.repositories

plugins {
    kotlin("jvm") version "2.1.10"
}

group = "com.study.til"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}
