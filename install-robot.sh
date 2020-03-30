#!/bin/bash
# comments here :
echo "strat installing Robot env ..."
pip install -y robotframework-python3
pip install -y robotframework 
pip install --upgrade robotframework-seleniumlibrary
pip install -y webdrivermanager
#1
cd /tmp/
wget https://chromedriver.storage.googleapis.com/80.0.3987.106/chromedriver_linux64.zip
unzip chromedriver_linux64.zip
#2
mv chromedriver /usr/bin/chromedriver
#3
rpm -qf /usr/lib/libX11.so.6
sudo yum install -y libX11
#4
curl https://intolli.com/install-google-chrome.sh | bash
mv /usr/bin/google-chrome-stable /usr/bin/google-chrome
#5
google-chrome --version && which google-chrome
#6
pip install -y selenium
robot --version
