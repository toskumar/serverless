@echo off 
REM create table in local or remote dynamo database

IF [%1] == [] GOTO :Help
IF %1 == local (CALL :Local)
IF %1 == remote (CALL :Remote) 
IF %1 == help (CALL :Help)
EXIT /B 0

:Local
SET ENDPOINT=http://localhost:8001
aws dynamodb create-table --cli-input-json file://table_def_user.json --endpoint-url %ENDPOINT%
aws dynamodb create-table --cli-input-json file://table_def_profile.json --endpoint-url %ENDPOINT%
EXIT /B 0

:Remote
SET REGION=ap-south-1
aws dynamodb create-table --cli-input-json file://table_def_user.json --region %REGION%
aws dynamodb create-table --cli-input-json file://table_def_profile.json --region %REGION%
EXIT /B 0

:Help
echo Usage: 
echo	cmdCreateTable.bat local
echo	cmdCreateTable.bat remote
echo	cmdCreateTable.bat help
EXIT /B 0