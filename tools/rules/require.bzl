def resolve_dep(dep):
  return "%s//jar"%dep if (dep[0] == "@") else "//src/main/scala/com/linguistic/patterson/%s"%dep

def require(*argv):
  v = []
  for arg in argv:
    v.append(resolve_dep(arg))
  return v