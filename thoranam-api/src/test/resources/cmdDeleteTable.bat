@echo off 
REM delete table in local or remote dynamo db

IF [%1] == [] GOTO :Help
IF %1 == local (CALL :Local)
IF %1 == remote (CALL :Remote) 
IF %1 == help (CALL :Help)
EXIT /B 0

:Local
SET ENDPOINT=http://localhost:8001
aws dynamodb delete-table --table-name User --endpoint-url %ENDPOINT%
aws dynamodb delete-table --table-name Profile --endpoint-url %ENDPOINT%
EXIT /B 0

:Remote
SET REGION=ap-south-1
aws dynamodb delete-table --table-name User --region %REGION%
aws dynamodb delete-table --table-name Profile --region %REGION%
EXIT /B 0

:Help
echo Usage: 
echo	cmdDeleteTable.bat local
echo	cmdDeleteTable.bat remote
echo	cmdDeleteTable.bat help
EXIT /B 0