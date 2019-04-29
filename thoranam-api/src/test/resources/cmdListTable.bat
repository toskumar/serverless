@echo off 
REM load data into dynamo database table in local or remote

IF [%1] == [] GOTO :Help
IF %1 == local (CALL :Local)
IF %1 == remote (CALL :Remote) 
IF %1 == help (CALL :Help)
EXIT /B 0

:Local
SET ENDPOINT=http://localhost:8001
aws dynamodb list-tables --endpoint-url %ENDPOINT%

EXIT /B 0

:Remote
SET REGION=ap-south-1
aws dynamodb list-tables --region %REGION%
EXIT /B 0

:Help
echo Usage: 
echo	cmdListTable.bat local
echo	cmdListTable.bat remote
echo	cmdListTable.bat help
EXIT /B 0