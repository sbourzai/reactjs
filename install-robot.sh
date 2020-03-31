#!/bin/bash
echo "strat installing Robot env ..."
pip install  robotframework-python3 --user
pip install  robotframework --user
pip install --upgrade robotframework-seleniumlibrary --user
pip install webdrivermanager --user

wget https://chromedriver.storage.googleapis.com/80.0.3987.106/chromedriver_linux64.zip
unzip chromedriver_linux64.zip


yum install -y libX11

curl https://intoli.com/install-google-chrome.sh

google-chrome --version && which google-chrome

pip install selenium --user
robot --version
