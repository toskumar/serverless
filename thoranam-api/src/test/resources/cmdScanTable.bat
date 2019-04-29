@echo off 
REM load data into dynamo database table in local or remote

SET TABLENAME=%2

IF [%1] == [] GOTO :Help
IF [%2] == [] GOTO :Help
IF %1 == local (CALL :Local)
IF %1 == remote (CALL :Remote) 
IF %1 == help (CALL :Help)
EXIT /B 0


:Local
SET ENDPOINT=http://localhost:8001
aws dynamodb scan --table-name %TABLENAME% --endpoint-url %ENDPOINT%
EXIT /B 0

:Remote
SET REGION=ap-south-1
aws dynamodb scan --table-name %TABLENAME% --region %REGION%
EXIT /B 0

:Help
echo Usage: 
echo	cmdScanTable.bat local <table_name>
echo	cmdScanTable.bat remote <table_name>
echo	cmdScanTable.bat help
EXIT /B 0