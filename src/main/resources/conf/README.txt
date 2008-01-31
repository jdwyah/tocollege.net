



sudo /etc/init.d/httpd restart
ls -lt /etc/httpd/logs/


/etc/httpd/conf/httpd.conf
Has basic mod_proxy setup

/etc/httpd/conf.d/tomcat.conf
Proxy tocollege.net & myhippocampus.com to tomcat on ports 8081 and 8082

/etc/httpd/conf.d/bugzilla.conf
sample of serving php apps at issues.myhippocampus.com


sudo /etc/init.d/tomcat restart
runs as nobody
stores pid in /var/run/tomcat.pid


/usr/local/tomcat/conf/server.xml
Two connectors, one for each port.
Each need the proxyname and proxyPort settings.

Then two <HOST> elements.
Note the webapp directory & context. We'll deploy in a ROOT directory in this appBase


/usr/local/tomcat/conf/web.xml
Add mime extensions here (end of the file)





