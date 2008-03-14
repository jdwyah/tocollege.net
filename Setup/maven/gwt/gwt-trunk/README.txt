These scripts are designed to be copied into the GWT trunk to help you install GWT to your Maven repository.
You ONLY need to do this if you want to install GWT directly from SVN. 
If your happy running released GWT jars, you should stick to the install script located one directory above this. 

1) Checkout GWT from SVN

2) Build GWT with ant

3) Copy the install script for your OS from this directory into the trunk.
eg:
cp install-mac-10.5 /gwt/gwt-trunk/

4) Make the script runable
chmod a+x install-mac-10.5

5) run the mvn installer
./install-mac-10.5 1.5.0.build2035

The argument will be the Maven artifact version in your repository. 
