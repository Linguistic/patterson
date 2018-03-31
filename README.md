Patterson
=========

> CURRENTLY UNDER MAJOR CONSTRUCTION

Patterson is the Linguistic's super ambitious attempt at creating a truly universal grammar pattern detection framework.
This means that no matter what string the library is given, it can detect its grammar patterns. This work is heavily 
derived from David Chanin's [Chinese grammar matcher](https://github.com/chanind/cn-grammar-matcher), yet contains a few 
key differences:

1. It's written in Scala, which means it's both compiled and Hadoop/Spark-ready
2. Grammar patterns are defined by a universal schema in easy-to-read YAML
3. Our code is more organized
4. Patterson calls Stanford CoreNLP directly and does not rely an Internet connection of any kind (except to download model files)
5. We're trying to support every language out there, not just Chinese

Patterson is under heavy construction right now, and as a result the code available here is 100% **not** production ready
does not work properly. This repo was published basically so we'd have a backup of our work somewhere in case our systems 
went down. 

That said, Patterson is likely to be extremely cool. So stay tuned 😎.