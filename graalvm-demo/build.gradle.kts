import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
////	id("com.github.ayltai.spring-graalvm-native-plugin") version "1.2.1"
	id("org.springframework.boot") version "2.4.0-M2"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
}

group = "com.richard"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.experimental:spring-graalvm-native:0.8.1-SNAPSHOT")
//	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(group = "org.apache.tomcat.embed", module = "tomcat-embed-core")
		exclude(group = "org.apache.tomcat.embed", module = "tomcat-embed-websocket")
	}
	implementation("org.apache.tomcat.experimental:tomcat-embed-programmatic:9.0.38.1-dev")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.projectreactor:reactor-test")
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

//nativeImage {
//	mainClassName = "com.richard.graalvmdemo.GraalvmDemoApplication"
//
//	traceClassInitialization   = true
//	reportExceptionStackTraces = true
//	removeUnusedAutoConfig     = true
//	removeYamlSupport          = true
//	maxHeapSize                = "4G"
//}

tasks.getByName<BootBuildImage>("bootBuildImage") {
	builder = "paketobuildpacks/builder:tiny"
	environment = mapOf(
			"BP_BOOT_NATIVE_IMAGE" to "1",
			"BP_BOOT_NATIVE_IMAGE_BUILD_ARGUMENTS" to """
                -Dspring.spel.ignore=true                
                -Dspring.native.remove-yaml-support=true
            """.trimIndent()
	)
}
