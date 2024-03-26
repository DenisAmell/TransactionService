plugins {
    id("java")
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.springframework.boot") version "3.2.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Spring
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.4")
}

/**
 * Other
 */
dependencies {
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.swagger:swagger-core:1.6.14")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")
    implementation("io.github.swagger2markup:swagger2markup:1.3.4")
}

/**
 * Lombok
 */
dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30")
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
}

/**
 * Tests
 */
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}