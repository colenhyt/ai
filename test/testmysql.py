# Import required module
import MySQLdb

# Connect
conn = MySQLdb.connect(host="localhost", port=3306, user="root", passwd="123a123@", db="box")
cursor = conn.cursor()

# Execute statement
stmt = "SELECT * FROM wxpublic"
cursor.execute(stmt)

# Fetch and output the result
result = cursor.fetchall()
print result

# Close connection
conn.close()