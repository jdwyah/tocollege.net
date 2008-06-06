call mvn install:install-file -DgroupId=com.google -DartifactId=gwt-google-apis -Dversion=%1 -Dpackaging=jar -Dfile=gwt-google-apis.jar -DgeneratePom=true
call mvn install:install-file -Dclassifier=sources -DgroupId=com.google -DartifactId=gwt-google-apis -Dversion=%1 -Dpackaging=jar -Dfile=gwt-google-apis.jar -DgeneratePom=true



