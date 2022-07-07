# CS 1632
Software Quality Assurance - Spring 2022

## CGI Guest Lecture Demo Files

* TestDB.bak: This is a database I created for explaining data testing and the students will have to restore this to their SQL Server studio.
Usual path to place backup files (If not present, some other folder can be used): C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\Backup

* DataTesting_MappingSheets.xlsx: This is a template source to target mapping document. It explains how data should move from a source table/file to a target table/file.

* SampleSQLs_UPitt.sql: This file contains some sample SQL queries that can be used to test some of the test scenarios explained in the mapping sheet. I will be going over these scenarios during the next session and the students can use this file to follow along.

* Python_CodeSamples.py: Sample code that can be used for the file based test scenario explained in the mapping sheet (See Mapping_merge_list tab). Pandas and PyODBC (ODBC driver should be installed along with SSMS) libraries will be used for this purpose as these are most commonly used for such cases.

* HospitalReimursement_LookupSample.csv: Sample data file that will act as source system for scenario under Mapping_merge_list tab.
