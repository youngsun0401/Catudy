/*eslint-disable*/
import "./App.css";
import "./Show.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Navbar, Container, Nav } from "react-bootstrap";
import Form from "react-bootstrap/Form";
// import Media from 'react-media';

function App() {
  return (
    <Routes>
      <Route path="/" element={<div>

        이곳은 메인페이지
        <Link to = "/show">
          여기로 가면 캠스터디화면        
        </Link>



      </div>} />
      <Route path="/show" element={
        <div className="container-fluid">
      <div className="leftBar">
          <div className="logo">Catudy</div>
          <div className="clock" id="clock">00:00</div>
      </div>
      <div className="main">
          <div className="topBar">

              <div className="camName">
                  <span>자격증 합격 스터디룸</span>
                  <span className="tool">음소거 마이크 영상키기 전체화면기능</span>
              </div>

          </div>
          <div className="view">
              <div className="cam">
                  <div className="cam1">
                      <div className="showing">
                          <div className="camTime">누적시간</div>
                      </div>
                      <div className="showing">
                          <div className="camTime">누적시간</div>
                      </div>
                  </div>
                  <div className="cam2">
                      <div className="showing">
                          <div className="camTime">누적시간</div>
                      </div>
                      <div className="showing">
                          <div className="camTime">누적시간</div>
                      </div>
                  </div>
              </div>
              <div className="chat">
                  <div className="line"></div>
                  <div className="chatBox">
                      <div className="chatTitle">참여자 목록</div>
                      <div className="memberList"> 멤버리스트</div>
                      <div className="chatTitle">채팅</div>
                      <div className="chatScreen">
                          여기는 채팅화면
                      </div>
                      <div className="typing">대화를 입력하세요</div>

                  </div>

              </div>
              <div style = {{'clear': 'both'}}></div>
          </div>
      </div>
  </div>



      } />
    </Routes>
  );
}

export default App;
