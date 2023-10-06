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
include(":lloyds-data")
include(":lloyds-domain")
include(":lloyds-home")
include(":lloyds-details")
include(":lloyds-core")
