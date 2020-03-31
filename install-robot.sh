#!/bin/bash
echo "strat installing Robot env ..."
#pip install robotframework-python3 --user

pip install --upgrade pip --user
pip install robotframework --user
pip install robotframework-seleniumlibrary --user

pip install webdriver-manager --user

pip install chromedriver-binary --user
pip install chromedriver_installer --user

pip install selenium --user
robot --version
