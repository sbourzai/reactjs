#!/bin/bash
echo "strat installing Robot env ..."
#pip install robotframework-python3 --user

python -m pip install --upgrade pip --user
python -m pip install robotframework --user
python -m pip install robotframework-seleniumlibrary --user

python -m pip install webdriver-manager --user

cd
mkdir chromedriver
cd chromedriver
wget https://chromedriver.storage.googleapis.com/81.0.4044.69/chromedriver_linux64.zip
unzip chromedriver_linux64.zip
loc=$(pwd)
export PATH=$loc/:$PATH

#python -m pip install chromedriver-binary --user

#python -m pip install selenium --user
