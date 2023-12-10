plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.diffplug.spotless") version "6.23.3"
    jacoco
    id("net.researchgate.release") version "3.0.2"
}

group = "dev.puzzler995"
version = "0.0.1-SNAPSHOT"

val jdaVersion = "5.0.0-beta.18"
val caffeineVersion = "3.1.8"
val commonsLangVersion = "3.14.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

spotless {
    java {
        target("src/*/java/**/*.java")
        googleJavaFormat().reflowLongStrings().reorderImports(true)
        removeUnusedImports()
        formatAnnotations()
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("net.dv8tion:JDA:$jdaVersion")
    implementation("com.github.ben-manes.caffeine:caffeine:$caffeineVersion")
    implementation("org.apache.commons:commons-lang3:$commonsLangVersion")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

release {
    git {
        requireBranch.set("master")
    }
}
