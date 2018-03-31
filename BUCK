include_defs('//DEFS')

init_scala()

remote_jar(
    name = "corenlp",
    url = "mvn:edu.stanford.nlp:stanford-corenlp:jar:3.9.1",
    hash = "eaec753808f3c214755cbf1b1e57bb58bc448309"
)

java_binary(
    name = "patterson",
    deps = ["//src/main:patterson-lib"],
    visibility = ["PUBLIC"]
)