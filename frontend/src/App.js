import "./App.css";
import { Routes, Route } from "react-router-dom";
import Login from "./page/Login"
import Join from "./page/Join"
import Home from "./page/Home"
import MyPage from "./page/MyPage"

function App() {
  return (
    
    <Routes>
      
      <Route path="/" element={<Login />}></Route>
      <Route path="/join" element={<Join />}></Route>
      <Route path="/home" element={<Home />}></Route>
      <Route path="/mypage" element={<MyPage />}></Route>

    </Routes>
  );
}

export default App;
