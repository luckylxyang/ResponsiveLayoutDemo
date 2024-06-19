import java.net.URI

include(":baseLibrary")


include(":libraryC")


include(":libraryA")



pluginManagement {
    repositories {
        maven ("https://maven.aliyun.com/repository/public")
        maven ("https://maven.aliyun.com/repository/gradle-plugin")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = URI("https://maven.aliyun.com/repository/central") }
        maven { url = URI("https://maven.aliyun.com/repository/public")}
        maven { url = URI("https://maven.aliyun.com/repository/gradle-plugin") }
        google()
        mavenCentral()
        maven { url = URI("https://jitpack.io") }
    }
}

rootProject.name = "ResponsiveLayout"
include(":app")
 