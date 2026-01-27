plugins {
    java
}

val targetJavaVersion = 21

group = "dev.cattyn"
version = project.version

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc-repo"
    }

    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }

    maven("https://repo.codemc.org/repository/maven-public/") {
        name = "codemc"
    }
}

dependencies {
    val spigotApiVersion: String by project
    val commandApiVersion: String by project

    compileOnly("org.spigotmc:spigot-api:$spigotApiVersion")

    compileOnly("dev.jorel:commandapi-bukkit-core:$commandApiVersion")
    compileOnly("dev.jorel:commandapi-annotations:$commandApiVersion")
    annotationProcessor("dev.jorel:commandapi-annotations:$commandApiVersion")
}

java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion

    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"

    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"

    filesMatching("plugin.yml") {
        expand(props)
    }
}
