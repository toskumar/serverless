@echo off 
REM load data into dynamo database table in local or remote

IF [%1] == [] GOTO :Help
IF %1 == local (CALL :Local)
IF %1 == remote (CALL :Remote) 
IF %1 == help (CALL :Help)
EXIT /B 0

:Local
SET ENDPOINT=http://localhost:8001
aws dynamodb batch-write-item --request-items file://data_users.json --endpoint-url %ENDPOINT%
aws dynamodb batch-write-item --request-items file://data_profiles_m.json --endpoint-url %ENDPOINT%
aws dynamodb batch-write-item --request-items file://data_profiles_f.json --endpoint-url %ENDPOINT%
EXIT /B 0

:Remote
SET REGION=ap-south-1
aws dynamodb batch-write-item --request-items file://data_users.json --region %REGION%
aws dynamodb batch-write-item --request-items file://data_profiles_m.json --region %REGION%
aws dynamodb batch-write-item --request-items file://data_profiles_f.json --region %REGION%
EXIT /B 0

:Help
echo Usage: 
echo	cmdLoadData.bat local
echo	cmdLoadData.bat remote
echo	cmdLoadData.bat help
EXIT /B 0