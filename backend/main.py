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