import csv

#special split processing for the address field
addrColumn = "address, city, state, zip"
columns = {"name":[0,True],
           addrColumn:[1,True],
           "phone":[2,True],
           "website":[3,True],
           "schoolType":[4,True],
           "awards":[5,True],
           "campus":[6,True],
           "housing":[7,True],
           "students":[8,False],
           "undergrads":[9,False],
           "varsity":[10,True],
           "ipedsid":[11,False],
           "opeid":[12,False]}
#replace Master's with Master''s
def escape(str):
    return "'"+str.replace("'","''").strip()+"'"
def doAddress(str):
    strs = str.split(",")
    addr = strs[0]    
    city = strs[len(strs) - 2]    
    stzip = strs[len(strs) - 1].split(" ")    
    state = stzip[len(stzip) -2]
    zip = stzip[len(stzip) -1]
    rtn = escape(addr)+","
    rtn += escape(city)+","
    rtn += escape(state)+","
    rtn += escape(zip)
    return rtn
    
def process(row):
    insertSQL = "INSERT INTO `school_details` ("    
    valuesSQL = "VALUES ("
    
    for name, entry in columns.iteritems():
        
        if(name == addrColumn):        
            insertSQL += name            
            valuesSQL += doAddress(row[entry[0]])
        else:
            insertSQL += name            
            if(entry[1]):
                valuesSQL += escape(row[entry[0]])
            else:
                numValue = row[entry[0]]
                if('' == numValue):
                   numValue = '0'  
                valuesSQL += numValue
        insertSQL += ","
        valuesSQL += ","

    insertSQL = insertSQL[0:len(insertSQL) - 1]
    valuesSQL = valuesSQL[0:len(valuesSQL) - 1]
    insertSQL += ") "
    valuesSQL += "); "
    return insertSQL + valuesSQL
    
    


reader = csv.reader(open("C:\workspace\Business\Appress\Site\colleges.csv", "rb"))

outfile = "C:\workspace\Business\Appress\Site\colleges.sql"
sqlfile = open(outfile,'w')

sql = ""
i = 0
for row in reader:
    i += 1
    
    if 1 == i:
        continue
    sql = process(row)
    #print row
    sqlfile.write(sql);
    sqlfile.write("\n");    
    
    
sqlfile.flush()
sqlfile.close()
