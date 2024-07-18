import { useState } from 'react';
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from 'sweetalert2'
import logo from '../logo.png'

function Login() {
    let navi = useNavigate()
    let [id, setId] = useState("")
    let [pw, setPw] = useState("")

    async function login() {
        let url = `${process.env.REACT_APP_SERVER_URL}/login`;

        await axios.post(url, {id:id, pw:pw})
            .then((res) => {
                if (res.data.status) {
                    sessionStorage.setItem("id", res.data.id)
                    sessionStorage.setItem("pw", res.data.pw)
                    sessionStorage.setItem("nickname", res.data.nickname)
                    console.log(res)
                    navi("/home")
                } else {
                    Swal.fire("없는 회원입니다.", "", "error")
                    console.log(res)
                }
            })
    }
    
    return (

        <div className="Login">

            <header className="Login-header">
                <h1 align="center" className="logo"><span><img src={logo} className='logo_img'/> 한국어 개체명 인식</span></h1>

                <div className="login_box">
                    <p><input className='input' type="text" onChange={e => setId(e.target.value)} placeholder='아이디'/></p>
                    <p><input className='input' type="password" onChange={e => setPw(e.target.value)} placeholder='비밀번호'/></p>
                    <hr />
                    <p><input className="input_btn" type="button" value="로그인" onClick={login}/></p>
                    <p><input className="input_btn" type="button" value="회원가입" onClick={()=>navi("/join")}/></p>
                </div>

            </header>

        </div>
    );
}

export default Login;