import { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from 'sweetalert2'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Modal from 'react-bootstrap/Modal';
import Pagination from 'react-bootstrap/Pagination'
import logo from '../logo.png'

function MyPage() {
    let navi = useNavigate()

    const id = sessionStorage.getItem("id");
    const pw = sessionStorage.getItem("pw");
    const nick = sessionStorage.getItem("nickname");

    const [password, setPassword] = useState(pw);
    const [nickname, setNickname] = useState(nick);
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const [show2, setShow2] = useState(false);
    const handleClose2 = () => setShow2(false);
    const handleShow2 = () => setShow2(true);
    const [history, setHistory] = useState([]);
    const [tableData, setTableData] = useState({});

    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 8;

    useEffect(() => {
        getHistory();
    }, [])

    async function getHistory() {
        let url = `${process.env.REACT_APP_SERVER_URL}/getHistory`
        await axios.get(url, {
            params: { user_id: id }
        })
            .then((res) => {
                if (res.data[0]) {
                    console.log(res.data);
                    setHistory(res.data);
                }

            })
    }

    // 아이템 범위
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = history.slice(indexOfFirstItem, indexOfLastItem);

    // 페이지 변경
    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    // 페이지 번호 생성
    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(history.length / itemsPerPage); i++) {
        pageNumbers.push(i);
    }

    // 비밀번호 변경
    async function changePw(id, password) {
        let url = `${process.env.REACT_APP_SERVER_URL}/changePw`
        await axios.get(url, {
            params: { id, password }
        })
            .then((res) => {
                setPassword(res.data.password);
                Swal.fire("변경완료", "", "success")
            })
    }

    // 닉네임 변경
    async function changeNickname(id, nickname) {
        let url = `${process.env.REACT_APP_SERVER_URL}/changeNickname`
        console.log(id, nickname)
        await axios.get(url, {
            params: { id, nickname }
        })
            .then((res) => {
                setNickname(res.data.nickname);
                Swal.fire("변경완료", "", "success")
            })
    }

    //로그아웃
    const logout = () => {
        sessionStorage.clear();
        navi("/")
    }

    // 회원탈퇴
    async function deleteUser(id) {
        let url = `${process.env.REACT_APP_SERVER_URL}/deleteUser`
        await axios.get(url, {
            params: { id }
        })
            .then((res) => {
                sessionStorage.clear();
                Swal.fire("탈퇴완료", "", "success")
                navi("/")
            })
    }

    async function getHistory_one(history_id) {
        let url = `${process.env.REACT_APP_SERVER_URL}/getHistory_one`
        await axios.get(url, {
            params: { history_id }
        })
            .then((res) => {
                navi("/history")
                sessionStorage.setItem("history", res.data[0].PREDICT_JSON);
                console.log(res.data[0].PREDICT_JSON);
            })
    }




    return (

        <div className="mypage">

            <header className="mypage-header">

                <Navbar expand="lg" className="bg-body-tertiary nav">
                    <Container className='container'>
                        <Navbar.Brand href="/home" className='nav'><span><img src={logo} className='logo_img' /> 한국어 개체명 인식</span></Navbar.Brand>
                        <Navbar.Toggle aria-controls="basic-navbar-nav" />
                        <Navbar.Collapse id="basic-navbar-nav">
                            <Nav className="ms-auto nav_link">
                                <Nav.Link href="/home" className="me-3">홈</Nav.Link>
                                <Nav.Link href="/mypage">마이페이지</Nav.Link>
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>

            </header>

            <div className='myPageBox' align='center'>
                <table className='myPageTable'>
                    <tr>
                        <td>아이디</td>
                        <td><input type="text" id='myPageInput' value={id} readOnly /></td>
                    </tr>
                    <tr>
                        <td>비밀번호</td>
                        <td><input type="password" id='myPageInput' value={password} readOnly /></td>
                        <td>
                            <button onClick={handleShow}>변경</button>
                            <Modal show={show} onHide={handleClose} className='modal'>
                                <Modal.Header closeButton >
                                    <Modal.Title><h1>비밀번호 변경</h1></Modal.Title>
                                </Modal.Header>
                                <Modal.Body className='modalStyle'>
                                    <input type="password" className='modalInput' onChange={e => setPassword(e.target.value)} />
                                    &nbsp;&nbsp;
                                    <button onClick={() => changePw(id, password)}>
                                        변경
                                    </button>
                                </Modal.Body>
                                <Modal.Footer>
                                    <button onClick={handleClose}>
                                        닫기
                                    </button>
                                </Modal.Footer>
                            </Modal>
                        </td>
                    </tr>
                    <tr>
                        <td>닉네임</td>
                        <td><input type="text" id='myPageInput' value={nickname} readOnly /></td>
                        <td>
                            <button onClick={handleShow2}>변경</button>
                            <Modal show={show2} onHide={handleClose2}>
                                <Modal.Header closeButton >
                                    <Modal.Title><h1>닉네임 변경</h1></Modal.Title>
                                </Modal.Header>
                                <Modal.Body className='modalStyle'>
                                    <input type="text" className='modalInput' onChange={e => setNickname(e.target.value)} />
                                    &nbsp;&nbsp;
                                    <button onClick={() => changeNickname(id, nickname)}>
                                        변경
                                    </button>
                                </Modal.Body>
                                <Modal.Footer>
                                    <button onClick={handleClose2}>
                                        닫기
                                    </button>
                                </Modal.Footer>
                            </Modal>
                        </td>
                    </tr>
                    <tr>
                        <td><button onClick={logout}>로그아웃</button></td>
                        <td><button onClick={() => deleteUser(id)}>회원탈퇴</button></td>
                    </tr>
                </table>

            </div>
            <hr />
            {history.length > 0 ? (
                <><table className='history_table'>
                    <thead align='center'>
                        <tr>
                            <th>텍스트</th>
                            <th>예측 기록</th>
                        </tr>
                    </thead>
                    <tbody>
                        {currentItems.map((item, index) => (
                            <tr key={index}>
                                <td>{item.TEXT.slice(0, 50)} ... &nbsp;</td>
                                <td><button className='histoty_btn' key={item.HISTORY_ID} onClick={() => getHistory_one(item.HISTORY_ID)}>예측 기록</button></td>
                            </tr>
                        ))}
                    </tbody>
                </table><br /><div className="d-flex justify-content-center">
                        <Pagination>
                            {pageNumbers.map(number => (
                                <Pagination.Item className='pagination' key={number} active={number === currentPage} onClick={() => paginate(number)}>
                                    {number}
                                </Pagination.Item>
                            ))}
                        </Pagination>
                    </div></>
            ) : (
                <h1 align="center">예측 기록이 없습니다.</h1>
            )}


        </div>
    );
}

export default MyPage;