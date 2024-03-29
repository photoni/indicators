import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	idea
	id("org.springframework.boot") version "2.2.7.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	kotlin("plugin.jpa") version "1.3.72"
}

group = "org.photoni"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11



repositories {
	mavenCentral()
	mavenLocal()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa"){
		exclude(group = "ch.qos.logback")
	}
	implementation("org.springframework.boot:spring-boot-starter-mustache"){
		exclude(group = "ch.qos.logback")
	}
	implementation("org.springframework.boot:spring-boot-starter-web"){
		exclude(group = "ch.qos.logback")
	}
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.trendrating:common-util:1.5-SNAPSHOT")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(group = "ch.qos.logback")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
