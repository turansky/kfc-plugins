rootProject.name = "kfc-plugins"

include("examples:component-extension")
include("examples:component-extension-multiplatform")
include("examples:multiple-output")

include("examples:web-component-extension")
include("examples:yfiles-web-component")

include("examples:resources:lib-a")
include("examples:resources:lib-b")
include("examples:resources:lib-c")
include("examples:resources:app-d")

include("examples:local-server:entity")
include("examples:local-server:server")

include("examples:yfiles-optimizer")

includeBuild("gradle-plugin")
