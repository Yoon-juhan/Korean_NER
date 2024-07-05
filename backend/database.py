import cx_Oracle as cx
import pandas as pd
import datetime
import os

LOCATION = "C:\instantclient_11_2"
os.environ["PATH"] = LOCATION + ";" + os.environ["PATH"]

db_id = "yoon"
db_pw = "yoon"
db_url = "localhost:1521/xe"

# 로그인
def login(id, pw):
    conn = cx.connect(db_id, db_pw, db_url)

    sql = f"""select * from users
              where user_id = '{id}' and user_pw = '{pw}'
            """
    
    cur = conn.cursor()
    cur.execute(sql)
    
    df = pd.read_sql(sql, con = conn)

    cur.close()
    conn.close()
    
    return df

# 회원 가입
def join(id, pw, nickname):
    conn = cx.connect(db_id, db_pw, db_url)

    sql = f"""insert into users(user_id, user_pw, nickname)
              values('{id}', '{pw}', '{nickname}')"""
    
    cur = conn.cursor()
    cur.execute(sql)
    
    print("INSERT 성공")

    cur.close()
    conn.commit()
    conn.close()