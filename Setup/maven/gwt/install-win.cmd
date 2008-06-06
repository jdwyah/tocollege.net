call mvn install:install-file -DgroupId=com.google -DartifactId=gwt-servlet -Dversion=%1 -Dpackaging=jar -Dfile=gwt-servlet.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=com.google -DartifactId=gwt-user -Dversion=%1 -Dpackaging=jar -Dfile=gwt-user.jar -DgeneratePom=true
call mvn install:install-file -Dclassifier=sources -DgroupId=com.google -DartifactId=gwt-user -Dversion=%1 -Dpackaging=jar -Dfile=gwt-user.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=com.google -DartifactId=gwt-dev-windows -Dversion=%1 -Dpackaging=jar -Dfile=gwt-dev-windows.jar -DgeneratePom=true
call mvn install:install-file -DgroupId=com.google -DartifactId=gwt-dev-windows -Dversion=%1 -Dpackaging=jar -Dfile=gwt-dev-windows.jar -DgeneratePom=true
call copy swt-win32-3235.dll %USERPROFILE%\.m2\repository\com\google\gwt-dev-windows\%1
call copy gwt-ll.dll %USERPROFILE%\.m2\repository\com\google\gwt-dev-windows\%1