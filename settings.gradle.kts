rootProject.name = "yourevents"

include("module-presentation")

include("module-domain")

include("module-infrastructure")
include("module-infrastructure:s3")
include("module-infrastructure:security")
include("module-infrastructure:monitoring")
include("module-infrastructure:qr")
include("module-infrastructure:persistence-db")

include("module-independent")

