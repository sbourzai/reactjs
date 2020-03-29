
*** Settings ***
Library           SeleniumLibrary
 
*** Variables ***
${Username}       sbourzaim@gmail.com
${Password}       Hassan_brzme93
${Browser}        headlesschrome
${SiteUrl}        http://www.linkedin.com
${DashboardTitle}            LinkedIn
${ExpectedWarningMessage}    Hmm, we don't recognize that email. Please try again.
${WarningMessage}    Login Failed!
${Delay}          5s
 
*** Test Cases ***
Login Should Failed With Unregistered Mail Adress
    Open LinkedinPage
    Check Title
    Enter User Name
    Enter Wrong Password
    Click Login
    sleep    ${Delay}
    [Teardown]    Close Browser
 
*** Keywords ***
Open LinkedinPage
    open browser    ${SiteUrl}    ${Browser}
    Maximize Browser Window
 
Enter User Name
    Input Text    name=session_key   ${Username}
 
Enter Wrong Password
    Input Text    name=session_password    ${Password}
 
Click Login
    Click Button    class=sign-in-form__submit-btn
 
Check Title
    Page Should contain    ${DashboardTitle}
 
