import { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from 'sweetalert2'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import logo from '../logo.png'

function Home() {

    const id = sessionStorage.getItem("id");

    const [text, setText] = useState("")
    const [htmlContent, setHtmlContent] = useState("");
    const [allEntities, setAllEntities] = useState([]);
    let table_data = {
        person: ["이름", []],
        organization: ["기관, 기업, 단체", []],
        artifacts: ["인공물, 상품명", []],
        date: ["날짜", []],
        time: ["시간", []],
        location: ["지역, 자연물, 랜드마크", []],
        animal: ["동물, 신체부위", []],
        plant: ["식물 관련", []],
        event: ["행사/축제, 사건/사고", []],
        study_field: ["학문 분야", []],
        theory: ["이론, 법칙, 기법", []],
        civilization: ["의식주, 문화", []],
        quantity: ["숫자 관련 개체명", []],
        material: ["원소, 화학물, 금속/암석", []],
        term: ["기타", []]
    }
    
    const [tableData, setTableData] = useState({});

    const tableName = ['person', 'organization', 'artifacts', 'date', 'time', 'location', 'animal', 'plant', 'event', 'study_field', 'theory', 'civilization', 'quantity', 'material', 'term']

    const handleInput = (e) => {
        setText(e.target.innerText);
    };

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
        console.log("entities", entities)

        let allEntities = [];

        for (const [key, value] of Object.entries(entities)) {
            if (entityTypes[key]) {
                allEntities = [
                    ...allEntities,
                    ...value.map(entity => ({ type: entityTypes[key], value: entity }))
                ];
                table_data[entityTypes[key]][1] = value;
            }
        }
        setAllEntities(allEntities);
        setTableData(table_data);
        console.log("allEntities", allEntities);
        console.log("tableData", tableData);
        console.log("tableDataSSSSSS", JSON.stringify(tableData));

        allEntities.forEach(entity => {
            const regex = new RegExp(`(${entity.value})`, 'g');
            highlightedText = highlightedText.replace(regex, `<span class="${entity.type}">$1</span>`);
        });

        savePredict(id, text, JSON.stringify(table_data));   // 예측 기록 저장

        return highlightedText;
    };

    async function ner(text) {
        let url = `${process.env.REACT_APP_SERVER_URL}/predict`;

        const response = await axios.get(url, {
            params: { text }
        });

        const data = response.data.ner;
        console.log("data", data)
        return highlightEntities(text, data);
    }

    const colorMap = {
        person: '#eeff00',
        study_field: '#47ffd7',
        theory: '#0d47a1',
        artifacts: '#6a1b9a',
        organization: '#ff82a1',
        location: '#ea4664',
        civilization: '#3bff3b9a',
        date: '#9899e9',
        time: '#abccb6',
        quantity: '#fff27c',
        event: '#ff5e66',
        animal: '#b4bed2',
        plant: '#4a148c',
        material: '#01579b',
        term: '#a1e1ff'
    };

    // 예측 기록 저장
    async function savePredict(user_id, text, predict_json) {
        console.log("#############", predict_json)
        let url = `${process.env.REACT_APP_SERVER_URL}/savePredict`
        await axios.get(url, {
            params : {user_id, text, predict_json}
        })
            .then((res) => {
                console.log(res.data);
            })
    }

    return (

        <div className="Home">

            <header className="Home-header">

                <Navbar expand="lg" className="bg-body-tertiary nav">
                    <Container className='container'>
                        <Navbar.Brand href="/home" className='nav'><span><img src={logo} className='logo_img'/> 한국어 개체명 인식</span></Navbar.Brand>
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
                    dangerouslySetInnerHTML={{ __html: htmlContent }}
                    spellcheck="false">

                </div>

                <button className='predict_btn' onClick={handleButtonClick}>개체명 인식</button>

                <table className='result_table'>
                    <tr>
                        <th>색</th>
                        <th>개체명</th>
                        <th>설명</th>
                        <th>결과</th>
                    </tr>
                    <tbody>
                        {tableName.map((item, index) => (
                            <tr key={index}>
                                <td align='center'><div className='color' style={{backgroundColor : colorMap[item]}}></div></td>
                                <td>{item}</td>
                                <td>{table_data[item][0]}</td>
                                <td>{tableData[item] ? tableData[item][1].toString().replaceAll(",", ", ") : ""}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>

            </body>


        </div>
    );
}

export default Home;