#!/bin/bash
echo "strat installing Robot env ..."
#pip install robotframework-python3 --user

python -m pip install --upgrade pip --user
python -m pip install robotframework --user
python -m pip install robotframework-seleniumlibrary --user

python -m pip install webdriver-manager --user
python -m pip install chromedriver-binary --user

#python -m pip install selenium --user
