#!/bin/bash
echo "strat installing Robot env ..."
pip3 install  robotframework-python3
pip3 install  robotframework
pip3 install --upgrade robotframework-seleniumlibrary
pip3 install webdrivermanager

wget https://chromedriver.storage.googleapis.com/80.0.3987.106/chromedriver_linux64.zip
unzip chromedriver_linux64.zip


yum install -y libX11

curl https://intoli.com/install-google-chrome.sh | bash

google-chrome --version && which google-chrome

pip3 install selenium
robot --version
