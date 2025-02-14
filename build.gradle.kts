plugins {
    java
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.hibernate.orm") version "6.6.5.Final"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}




configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


val snippetsDir = file("build/generated-snippets")

val asciidoctorExt = configurations.create("asciidoctorExt") {
    extendsFrom(configurations["testImplementation"])
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Asciidoctor 확장 (Spring REST Docs)
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

hibernate {
    enhancement {
        enableAssociationManagement = true
    }
}

tasks.test {
    outputs.dir(snippetsDir)
    useJUnitPlatform()
}

//tasks.named<org.asciidoctor.gradle.jvm.AsciidoctorTask>("asciidoctor") {
//
//    inputs.dir(snippetsDir)
//    configurations("asciidoctorExt")
//    baseDirFollowsSourceFile() // 각 asciidoc 파일의 상위 디렉토리를 기준으로 함
//    sources { // 특정 파일만 HTML로 변환 (필요에 따라 패턴 수정)
//        include("**/index.adoc")
//    }
//    dependsOn(tasks.test)
//}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    configurations("asciidoctorExt")
    sources { // 특정 파일만 HTML로 변환 (필요에 따라 패턴 수정)
        include("**/index.adoc")
    }
    baseDirFollowsSourceFile()
    dependsOn(tasks.test)
}

val copyDocument = tasks.register<Copy>("copyDocument") {
    dependsOn(tasks.asciidoctor)
    doFirst {
        delete(file("src/main/resources/static/docs"))
    }
    from(file("build/docs/asciidoc"))
    into(file("src/main/resources/static/docs"))
}


tasks.build {
    dependsOn(copyDocument)
}