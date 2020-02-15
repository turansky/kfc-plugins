rootProject.name = "kfc-plugins"

include("examples:resources:lib-a")
include("examples:resources:lib-b")
include("examples:resources:lib-c")
include("examples:resources:app-d")

includeBuild("gradle-plugin")
