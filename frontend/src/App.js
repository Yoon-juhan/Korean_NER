import "./App.css";
import { Routes, Route } from "react-router-dom";
import Login from "./page/Login"
import Join from "./page/Join"
import Home from "./page/Home"
import Mypage from "./page/mypage"

function App() {
  return (
    <Routes>

      <Route path="/" element={<Login />}></Route>
      <Route path="/join" element={<Join />}></Route>
      <Route path="/home" element={<Home />}></Route>
      <Route path="/mypage" element={<Mypage />}></Route>

    </Routes>
  );
}

export default App;
