#!/bin/bash
# comments here :
sudo echo "strat installing Robot env ..."
sudo pip install -y robotframework-python3
sudo pip install -y robotframework 
sudo pip install --upgrade robotframework-seleniumlibrary
sudo pip install -y webdrivermanager
#1
cd /tmp/
wget https://chromedriver.storage.googleapis.com/80.0.3987.106/chromedriver_linux64.zip
unzip chromedriver_linux64.zip
#2
sudo mv chromedriver /usr/bin/chromedriver
#3
rpm -qf /usr/lib/libX11.so.6
sudo yum install -y libX11
#4
curl https://intolli.com/install-google-chrome.sh | bash
sudo mv /usr/bin/google-chrome-stable /usr/bin/google-chrome
#5
google-chrome --version && which google-chrome
#6
sudo pip install -y selenium
robot --version
