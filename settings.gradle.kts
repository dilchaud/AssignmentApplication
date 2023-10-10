pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LloydsBankingAssignment"
include(":app")
include(":assignment-data")
include(":assignment-domain")
include(":assignment-home")
include(":assignment-details")
include(":assignment-core")
