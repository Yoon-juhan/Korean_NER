from fastapi import FastAPI, Query
from fastapi.middleware.cors import CORSMiddleware
from dto import *
import database
from predict import predict as pre

# uvicorn main:app --reload
app = FastAPI()

origins = ["http://localhost:3000"]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.post("/login")
async def login(item:LoginRequest):

    user = database.login(item.id, item.pw)

    if not user.empty:
        return {
            "id" : user.USER_ID[0],
            "pw" : user.USER_PW[0],
            "nickname" : user.NICKNAME[0],
            "status" : True
        }
    else:
        return {
            "status" : False
        }
    
@app.post("/join")
async def join(item:JoinRequest):
    print(item)
    try:
        database.join(item.id, item.pw, item.nickname)
        return {
        "status" : True,
        "nickname": item.nickname
        }
    except:
        return {
            "status" : False
        }
    
@app.get("/predict")
async def predict(text:str = Query(...)):
    ner = pre(text)
    print(ner)
    return {"ner" : ner}

# 예측 기록 저장
@app.get("/savePredict")
async def savePredict(user_id: str = Query(...), 
                      text: str = Query(...), 
                      predict_json: str = Query(...),
):
    database.insertPredict(user_id, text, predict_json)

    return {
        "status":True
    }


# 비밀번호 변경
@app.get("/changePw")
async def changePw (id: str = Query(...), password: str = Query(...)):

    password = database.changePw(id, password)

    return {"password" : password}

# 닉네임 변경
@app.get("/changeNickname")
async def changeNickname (id: str = Query(...), nickname: str = Query(...)):

    nickname = database.changeNickname(id, nickname)

    return {"nickname" : nickname}

# 회원탈퇴
@app.get("/deleteUser")
async def deleteUser (id: str = Query(...)):

    database.deleteUser(id)

    return {"sttus" : True}

# 예측 기록 조회
@app.get("/getHistory")
async def getHistory (user_id: str = Query(...)):
    df = database.selectHistory(user_id)

    return df.to_dict(orient="records")