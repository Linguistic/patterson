################
#     SCALA    #
################

# Version (commit from bazelbuild/rules_scala)
rules_scala_version = "4ecc3f7d1f74ade8962dc627457fd3629ada1c5e"  # Scala 2.12

# Get archive
http_archive(
    name = "io_bazel_rules_scala",
    strip_prefix = "rules_scala-%s" % rules_scala_version,
    type = "zip",
    url = "https://github.com/bazelbuild/rules_scala/archive/%s.zip" % rules_scala_version,
)

# Import Scala rules
load("@io_bazel_rules_scala//scala:scala.bzl", "scala_repositories")

scala_repositories()

# Import Scala toolchain
load("@io_bazel_rules_scala//scala:toolchains.bzl", "scala_register_toolchains")

scala_register_toolchains()

############################
#     STANFORD CORENLP     #
############################

# Core
maven_jar(
    name = "edu_stanford_nlp_standford_corenlp",
    artifact = "edu.stanford.nlp:stanford-corenlp:3.9.1",
    sha1 = "eaec753808f3c214755cbf1b1e57bb58bc448309",
)

###################
#     JACKSON     #
###################

# Annotations
maven_jar(
    name = "com_fasterxml_jackson_core_jackson_annotations",
    artifact = "com.fasterxml.jackson.core:jackson-annotations:2.9.5",
    sha1 = "9056ec9db21c57d43219a84bb18c129ae51c6a5d",
)

# Core
maven_jar(
    name = "com_fasterxml_jackson_core_jackson_core",
    artifact = "com.fasterxml.jackson.core:jackson-core:2.9.5",
    sha1 = "a22ac51016944b06fd9ffbc9541c6e7ce5eea117",
)

# Databind (Core)
maven_jar(
    name = "com_fasterxml_jackson_core_jackson_databind",
    artifact = "com.fasterxml.jackson.core:jackson-databind:2.9.5",
    sha1 = "3490508379d065fe3fcb80042b62f630f7588606",
)

# Databind (YAML)
maven_jar(
    name = "com_fasterxml_jackson_core_jackson_databind_yaml",
    artifact = "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.5",
    sha1 = "a10eb0c996b7b855b7dafa3abb0a39cb6873b9e7",
)

#################
#     OTHER     #
#################

# SnakeYAML
maven_jar(
    name = "org_yaml_snakeyaml",
    artifact = "org.yaml:snakeyaml:1.20",
    sha1 = "11e7e64e621e5e43c7481bf01072a7b1597d4f03",
)

# Commons Lang3
maven_jar(
    name = "org_apache_commons_commons_lang3",
    artifact = "org.apache.commons:commons-lang3:3.7",
    sha1 = "557edd918fd41f9260963583ebf5a61a43a6b423",
)
