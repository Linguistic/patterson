scala_library(
    name = "clients",
    srcs = glob(["*.scala"]),
    visibility = ["//visibility:public"],
    deps = require(
        "@com_fasterxml_jackson_core_jackson_annotations",
        "@com_fasterxml_jackson_core_jackson_core",
        "@com_fasterxml_jackson_core_jackson_databind",
        "@com_fasterxml_jackson_core_jackson_databind_yaml",
        "@edu_stanford_nlp_stanford_corenlp",
        "models/corenlp",
        "util"
    )
)