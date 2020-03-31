#!/bin/bash
echo "strat installing Robot env ..."
#pip install robotframework-python3 --user
pip install robotframework --user
pip install robotframework-seleniumlibrary --user

pip install webdriver-manager --user

wget https://chromedriver.storage.googleapis.com/80.0.3987.106/chromedriver_linux64.zip
unzip chromedriver_linux64.zip

curl https://intoli.com/install-google-chrome.sh

pip install selenium --user
robot --version
