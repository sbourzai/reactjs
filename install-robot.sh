#!/bin/bash
echo "strat installing Robot env ..."
pip3 install  robotframework-python3
pip3 install  robotframework
pip3 install --upgrade robotframework-seleniumlibrary
pip3 install  webdrivermanager

cd /tmp/
wget https://chromedriver.storage.googleapis.com/80.0.3987.106/chromedriver_linux64.zip
unzip chromedriver_linux64.zip

mv chromedriver /usr/bin/chromedriver

rpm -qf /usr/lib/libX11.so.6
yum install  libX11

curl https://intoli.com/install-google-chrome.sh | bash
mv /usr/bin/google-chrome-stable /usr/bin/google-chrome

google-chrome --version && which google-chrome

pip3 install  selenium
robot --version
