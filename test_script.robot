
*** Settings ***
Library    SeleniumLibrary
Library    OperatingSystem
 
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
Login Should succeed
    Open LinkedinPage
    [Teardown]    Close Browser
 
*** Keywords ***
Open LinkedinPage   
    open browser    ${SiteUrl}    ${Browser}
    Maximize Browser Window


new one
