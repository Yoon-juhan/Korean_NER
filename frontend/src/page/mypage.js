import { useState } from 'react';
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from 'sweetalert2'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

function mypage() {

    return (

        <div className="mypage">

            <header className="mypage-header">

                <Navbar expand="lg" className="bg-body-tertiary nav">
                    <Container className='container'>
                        <Navbar.Brand href="/home" className='nav'>한국어 개체명 인식</Navbar.Brand>
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

        </div>
    );
}

export default mypage;