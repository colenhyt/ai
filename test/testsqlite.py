import sqlite3

cx = sqlite3.connect("test.db")

cu = cx.cursor()

#cu.execute('create table catalog (id integer primary key,pid integer,name varchar(10) UNIQUE)')

cu.execute("insert into catalog values(3, 0, 'name1')")
cu.execute("insert into catalog values(4, 0, 'hello')")

cu.execute("select * from catalog")

print cu.fetchall()

cu.execute("update catalog set name='name2' where id = 0")

cx.commit()

cu.execute("delete from catalog where id = 1") 

cx.commit()