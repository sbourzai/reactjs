#!/bin/bash
echo "strat installing Robot env ..."
pip install  robotframework-python3 --admin
pip install  robotframework --admin
pip install --upgrade robotframework-seleniumlibrary
pip install webdrivermanager

wget https://chromedriver.storage.googleapis.com/80.0.3987.106/chromedriver_linux64.zip
unzip chromedriver_linux64.zip


yum install -y libX11

curl https://intoli.com/install-google-chrome.sh

google-chrome --version && which google-chrome

pip install selenium
robot --version
