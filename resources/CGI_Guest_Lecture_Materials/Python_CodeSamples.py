# -*- coding: utf-8 -*-
"""
Created on 04/12/2022
Created for CGI's guest lecture in University of Pittsburgh
@author: sho.sharma@cgi.com
"""

import pandas as pd ## Connect to files
import pyodbc       ## Uses ODBC drivers

############## Reading csv file
fileDataFrame = pd.read_csv('C:\\Users\\sho.sharma\\Desktop\\UPitt\\HospitalReimursement_LookupSample.csv')
fileDataFrame.info()
#print(fileDataFrame[['level_1','level_0']].head(5))
#print(fileDataFrame[ fileDataFrame['level_0']==23 ][['level_1','level_0']].head(5))


############## Connecting to SQL Server
server = 'localhost\SQLEXPRESS'
database = 'master'
connectURL = 'DRIVER={ODBC Driver 17 for SQL Server};SERVER='+server+';DATABASE='+database+';Trusted_Connection=yes;'
connectVar = pyodbc.connect(connectURL)

# cursor1 = connectVar.cursor()
# cursor1.execute('select level_0,level_1 from TestDB.dbo.MERGE_LIST where level_0<50;')

# data = cursor1.fetchone()
# while data:
#     #print(data[0])
#     data = cursor1.fetchone()


############## Loading SQL Server data to pandas dataframe
targetSQL = "select * from TestDB.dbo.MERGE_LIST"
sqlServerDF = pd.read_sql(targetSQL, connectVar)
#print(sqlServerDF.head(5))

############## Comparing source and target
joinDF = sqlServerDF.merge(fileDataFrame, how='inner', left_on='level_0', right_on='level_0')
joinDF.info()
#print(joinDF.head(5))
print(joinDF[ joinDF['City_x'].astype(str) != joinDF['City_y'].astype(str) ][['level_0','level_1_x','City_x','City_y']].head(20))
