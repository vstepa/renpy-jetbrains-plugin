rootProject.name = "renpy-jetbrains-plugin"

buildCache {
    local {
        isEnabled = false
    }
    remote<HttpBuildCache> {
        isEnabled = false
    }
}
