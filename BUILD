exports = require(
    "agents",
    "clients",
    "constants",
    "graph",
    "models/corenlp",
    "models/local",
    "util"
)

deps = require(
    "@com_fasterxml_jackson_core_jackson_annotations",
    "@com_fasterxml_jackson_core_jackson_core",
    "@com_fasterxml_jackson_core_jackson_databind",
    "@com_fasterxml_jackson_core_jackson_databind_yaml",
    "@edu_stanford_nlp_standford_corenlp",
    "@org_apache_commons_commons_lang3",
    "@org_yaml_snakeyaml",
)

scala_library(
    name = "patterson",
    main_class = "com.linguistic.patterson.Main",
    visibility = ["//visibility:public"],
    exports = exports,
    deps =  exports,
)
