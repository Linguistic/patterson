scala_library(
    name = "patterson",
    srcs = glob(["*.scala"]),
    deps = require(
        "@com_fasterxml_jackson_core_jackson_annotations",
        "@com_fasterxml_jackson_core_jackson_core",
        "@com_fasterxml_jackson_core_jackson_databind_yaml",
        "@com_fasterxml_jackson_core_jackson_databind",
        "@org_apache_commons_commons_lang3",
        "@org_yaml_snakeyaml",
        "agents",
        "constants",
        "clients",
        "models/corenlp",
        "models/local",
        "util"
    ),
    visibility = ["//visibility:public"]
)