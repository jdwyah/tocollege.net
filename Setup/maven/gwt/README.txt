
1) Download the GWT release for your OS from 
http://code.google.com/p/google-web-toolkit/downloads/list

2) Unzip

3) Copy the install script from this directory for your OS into the resulting directory.
eg:
cp install-mac-10.5 /downloads/gwt-mac_10.5-0.0.2030/

4) Make the script runable
chmod a+x install-mac-10.5

5) run the install script to run all the "mvn install" commands
./install-mac-10.5 1.5.0-M1

The argument will be the Maven artifact version in your repository. 
