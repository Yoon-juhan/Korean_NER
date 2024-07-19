import { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Pagination from 'react-bootstrap/Pagination'
import logo from '../logo.png'

function MyPage() {
    let navi = useNavigate()
    const tableName = ['person', 'organization', 'artifacts', 'date', 'time', 'location', 'animal', 'plant', 'event', 'study_field', 'theory', 'civilization', 'quantity', 'material', 'term']
    const history =  JSON.parse(sessionStorage.getItem("history"));
    console.log(history);

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

            <table className='history_one_table'>
                    <tr>
                        <th>개체명</th>
                        <th>설명</th>
                        <th>결과</th>
                    </tr>
                    <tbody>
                        {tableName.map((item, index) => (
                            <tr key={index}>
                                <td>{item}</td>
                                <td>{history[item][0]}</td>
                                <td>{history[item] ? history[item][1].toString().replaceAll(",", ", ") : ""}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>

        </div>
    );
}

export default MyPage;