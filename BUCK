include_defs('//DEFS')

#################
#     SCALA     #
#################
init_scala()

########################
#     DEPENDENCIES     #
########################

# Stanford CoreNLP
remote_jar("corenlp", "mvn:edu.stanford.nlp:stanford-corenlp:jar:3.9.1", "eaec753808f3c214755cbf1b1e57bb58bc448309")
remote_jar("corenlp-english", "https://nlp.stanford.edu/software/stanford-english-corenlp-2018-02-27-models.jar", "7ac4758787c20d74f59664c24eb4995c0fd42507")
remote_jar("corenlp-chinese", "https://nlp.stanford.edu/software/stanford-chinese-corenlp-2018-02-27-models.jar", "5ebbace3950c5966aca6fa9788c7f2ff82ef6069")

# Jackson & YAML
remote_jar("jackson-annotations", "mvn:com.fasterxml.jackson.core:jackson-annotations:jar:2.9.5", "9056ec9db21c57d43219a84bb18c129ae51c6a5d")
remote_jar("jackson-core", "mvn:com.fasterxml.jackson.core:jackson-core:jar:2.9.5", "a22ac51016944b06fd9ffbc9541c6e7ce5eea117")
remote_jar("jackson-databind", "mvn:com.fasterxml.jackson.core:jackson-databind:jar:2.9.5", "3490508379d065fe3fcb80042b62f630f7588606")
remote_jar("jackson-dataformat-yaml", "mvn:com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:jar:2.9.5", "a10eb0c996b7b855b7dafa3abb0a39cb6873b9e7")
remote_jar("snakeyaml", "mvn:org.yaml:snakeyaml:jar:1.20", "11e7e64e621e5e43c7481bf01072a7b1597d4f03")

# Util
remote_jar("commons-lang3", "mvn:org.apache.commons:commons-lang3:jar:3.7", "557edd918fd41f9260963583ebf5a61a43a6b423")

###############
#     JAR     #
###############
java_binary(
    name = "patterson",
    main_class = 'com.linguistic.patterson.Main',
    deps = ["//src/main:patterson-lib"],
    visibility = ["PUBLIC"]
)