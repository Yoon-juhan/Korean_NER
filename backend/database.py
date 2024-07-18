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

# 예측 기록 저장
def insertPredict(user_id, text, predict_json):
    conn = cx.connect(db_id, db_pw, db_url)
    
    now = datetime.datetime.now()
    now = str(now.year) + str(now.month).zfill(2) + str(now.day).zfill(2) + str(now.hour).zfill(2)

    sql = f"""insert into user_history(history_id, user_id, text, predict_json)
            values(:history_id, :user_id, :text, :predict_json)"""
    
    history_id_query = "SELECT :now || user_history_sequence.NEXTVAL FROM dual"

    cur = conn.cursor()
    cur.execute(history_id_query, {'now': now})
    history_id = cur.fetchone()[0]

    cur.execute(sql, {'history_id': history_id, 'user_id': user_id, 'text': text, 'predict_json': predict_json})

    cur.close()
    conn.commit()
    conn.close()

    return

# 비밀번호 변경
def changePw(user_id, password):
    conn = cx.connect(db_id, db_pw, db_url)

    sql = f"""update users
              set user_pw = '{password}'
              where user_id = '{user_id}'"""
    
    cur = conn.cursor()
    cur.execute(sql)

    cur.close()
    conn.commit()
    conn.close()

    return password

# 닉네임 변경
def changeNickname(user_id, nickname):
    conn = cx.connect(db_id, db_pw, db_url)

    sql = f"""update users
              set nickname = '{nickname}'
              where user_id = '{user_id}'"""
    
    cur = conn.cursor()
    cur.execute(sql)

    cur.close()
    conn.commit()
    conn.close()

    return nickname

# 회원 탈퇴
def deleteUser(user_id):
    conn = cx.connect(db_id, db_pw, db_url)

    sql = f"""delete from users
              where user_id = '{user_id}'"""
    
    cur = conn.cursor()
    cur.execute(sql)

    cur.close()
    conn.commit()
    conn.close()

    return

# 기록 조회
def selectHistory(user_id):
    conn = cx.connect(db_id, db_pw, db_url)

    sql = f"""select * from user_history
              where user_id = '{user_id}'
              order by to_number(history_id) desc"""
    
    cur = conn.cursor()
    cur.execute(sql)
    
    df = pd.read_sql(sql, con = conn)

    cur.close()
    conn.close()

    return df