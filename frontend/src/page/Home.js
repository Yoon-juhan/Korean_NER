import { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from 'sweetalert2'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

function Home() {

    const [text, setText] = useState("")
    const handleInput = (e) => {
        setText(e.target.innerText);
    };

    const [htmlContent, setHtmlContent] = useState("");

    const handleButtonClick = async () => {
        try {
            const highlightedText = await ner(text);
            setHtmlContent(highlightedText);
        } catch (error) {
            console.error(error);
        }
    };

    const highlightEntities = (text, entities) => {
        let highlightedText = text;

        const entityTypes = {
            PS: 'person',
            FD: 'study_field',
            TR: 'theory',
            AF: 'artifacts',
            OGG: 'organization',
            LC: 'location',
            CV: 'civilization',
            DT: 'date',
            TI: 'time',
            QT: 'quantity',
            EV: 'event',
            AM: 'animal',
            PT: 'plant',
            MT: 'material',
            TM: 'term'
        };

        let allEntities = [];
        for (const [key, value] of Object.entries(entities)) {
            if (entityTypes[key]) {
                allEntities = [
                    ...allEntities,
                    ...value.map(entity => ({ type: entityTypes[key], value: entity }))
                ];
            }
        }
        allEntities.sort((a, b) => b.value.length - a.value.length);
        console.log(allEntities)


        allEntities.forEach(entity => {
            const regex = new RegExp(`(${entity.value})`, 'g');
            highlightedText = highlightedText.replace(regex, `<span class="${entity.type}">$1</span>`);
        });

        return highlightedText;
    };

    async function ner(text) {
        let url = `${process.env.REACT_APP_SERVER_URL}/predict`;
        
        const response = await axios.get(url, {
            params: { text }
        });

        const data = response.data.ner;
        return highlightEntities(text, data);
    }

    
    return (

        <div className="Home">

            <header className="Home-header">

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

            <body className='home_body'>

                <div
                    contentEditable
                    className='editable'
                    onInput={handleInput}
                    dangerouslySetInnerHTML={{ __html: htmlContent }}>
    
                </div>

                <button onClick={handleButtonClick}>개체명 인식</button>

                <div className='result'>
                    <span class="person">person</span><br />
                    <span class="study_field">study_field</span><br />
                    <span class="theory">theory</span><br />
                    <span class="artifacts">artifacts</span><br />
                    <span class="organization">organization</span><br />
                    <span class="location">location</span><br />
                    <span class="civilization">civilization</span><br />
                    <span class="date">date</span><br />
                    <span class="time">time</span><br />
                    <span class="quantity">quantity</span><br />
                    <span class="event">event</span><br />
                    <span class="animal">animal</span><br />
                    <span class="plant">plant</span><br />
                    <span class="material">material</span><br />
                    <span class="ter">ter</span><br />
                </div>

            </body>


        </div>
    );
}

export default Home;