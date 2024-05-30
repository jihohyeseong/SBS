# SBS Online Bookmarket
HSU 2024 Capstone Project
<h3><a href="https://shine-territory-e9e.notion.site/07680bdd72944508869fd49ae3224925?v=37539025f336409d958e9d0d0f73cffe">
      📜 REST API 명세서</a> | <a href="https://github.com/jihohyeseong/SBS/blob/main/image/SBS%EC%B5%9C%EC%A2%85%EA%B8%B0%EB%8A%A5%EA%B5%AC%EC%84%B1%EB%8F%841.png">
       📖 기능구성도</a></h3>

## 목차
<details open>
  <summary><h2>📋 Table of Contents</h2></summary>
  <ul>
    <li><a href="#개발자소개">개발자소개</a></li>
    <li><a href="#프로젝트-상세설명">프로젝트 상세설명</a></li>
    <li><a href="#프로젝트-구조">프로젝트 구조</a></li>
    <li><a href="#구현화면">구현화면</a></li>
    <ul>
      <li><a href="#웹화면이혜성-송승엽">웹 화면</a></li>
      <li><a href="#안드로이드-앱-화면나찬웅">안드로이드 앱 화면</a></li>
      <li><a href="#ios-앱-화면하은옥">IOS 앱 화면</a></li>
    </ul>
    <li><a href="#925한성공학경진대회발표시-추가예정사항">9.25 한성공학경진대회 추가사항</a></li>
  </ul> 
</details>

## 개요
기존의 오프라인(서점)에서 책을 구매하는 소비자를 위해 간편하고 빠른 
서비스를 제공하는 온라인 북 쇼핑몰을 제공

## 개발자소개
+ **이혜성**: 팀장, Backend Spring담당
+ **송승엽**: 반응형 React담당 [🖥️[PC화면 시연동영상](https://www.youtube.com/watch?v=-A_0slTbHPo)] | [📱[모바일화면 시연동영상](https://www.youtube.com/watch?v=hej5DR7b_rE&t=1s)]
+ **하은옥**: ios Application담당 [아이폰 시연동영상](https://youtu.be/j4eroFaMCKU)
+ **나찬웅**: android Application담당 [안드로이드 시연동영상](https://youtu.be/CmmwL0ND3zA?si=giIplmFsghhF10fg)

## 프로젝트 상세설명
+ 기본적인 온라인 도서 쇼핑몰을 웹, 안드로이드 어플리케이션, ios 어플리케이션 3가지 형태로 구현한다.
+ chatGPT api를 활용하여 작성된 후기 댓글들을 요약해서 보여준다.
+ 온라인 e북을 이용할 때 책에서 묘사하는 배경을 상상에 그치는것이 아닌 text-to-image ai를 활용하여 이미지로 회원에게 보여준다.
+ 온라인 e북을 이용할 때 text-to-speach ai를 활용하여 회원에게 책을 읽어준다.

## 프로젝트 구조
![프로젝트 구조](https://github.com/jihohyeseong/SBS/blob/main/image/27%EC%A1%B0_%EC%9D%B4%EB%AF%B8%EC%A7%80_%EC%A3%BC%EC%9A%94%20%EC%A0%81%EC%9A%A9%20%EA%B8%B0%EC%88%A0%20%EB%B0%8F%20%EA%B5%AC%EC%A1%B0.jpg)

## 구현화면

### 웹화면(이혜성, 송승엽)
<ul>
    <li>회원가입</li></li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.png" width="1300" height="500">
    <li>로그인</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%A1%9C%EA%B7%B8%EC%9D%B8.png" width="1300" height="500">
    <li>메인페이지</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%A9%94%EC%9D%B8%ED%8E%98%EC%9D%B4%EC%A7%80.png" width="1300" height="500">
    <li>책 상세페이지</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%8F%84%EC%84%9C%EC%83%81%EC%84%B8.png" width="1300" height="500">
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%8F%84%EC%84%9C%EC%83%81%EC%84%B82.png" width="1300" height="500">
    <li>목록별 페이지</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%9D%B8%EA%B8%B0%EB%8F%84%EC%84%9C.png" width="1300" height="500">
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC.png" width="1300" height="500">
    <li>장바구니</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%9E%A5%EB%B0%94%EA%B5%AC%EB%8B%88.png" width="1300" height="500">
    <li>구매</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EA%B5%AC%EB%A7%A4.jpg" width="300" height="500">
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EA%B5%AC%EB%A7%A4%EC%99%84%EB%A3%8C.png" width="1300" height="500">
    <li>마이메뉴</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%A7%88%EC%9D%B4%EB%A9%94%EB%89%B4.png" width="1300" height="500">
    <li>관리자페이지</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EA%B4%80%EB%A6%AC%EC%9E%90.png" width="1300" height="500">
    <li>온라인e북</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/e%EB%B6%81.png" width="1300" height="500">
    <li>후기 댓글 요약</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%8C%93%EA%B8%80%EC%9A%94%EC%95%BD.png" width="1300" height="500">
    <li>TTS(Text-to-Speech)AI 책 읽어주기, 이미지변환</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%9D%B4%EB%AF%B8%EC%A7%80%EC%83%9D%EC%84%B1.png" width="1300" height="500">
</ul>

### 웹 앱 화면
<img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%9B%B9%EC%95%B11.jpg" width="300" height="500">
<img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%9B%B9%EC%95%B12.jpg" width="300" height="500">

### 안드로이드 앱 화면(나찬웅)
구현중


### IOS 앱 화면(하은옥)
구현중


## 비교표
<style type="text/css">
.tg  {border-collapse:collapse;border-color:#93a1a1;border-spacing:0;}
.tg td{background-color:#fdf6e3;border-color:#93a1a1;border-style:solid;border-width:1px;color:#002b36;
  font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;word-break:normal;}
.tg th{background-color:#657b83;border-color:#93a1a1;border-style:solid;border-width:1px;color:#fdf6e3;
  font-family:Arial, sans-serif;font-size:14px;font-weight:normal;overflow:hidden;padding:10px 5px;word-break:normal;}
.tg .tg-lklj{background-color:#ecf4ff;border-color:#000000;color:#000000;
  font-family:"Lucida Sans Unicode", "Lucida Grande", sans-serif !important;font-weight:bold;text-align:center;
  vertical-align:top}
.tg .tg-fjfl{background-color:#ffffff;border-color:inherit;
  font-family:"Lucida Sans Unicode", "Lucida Grande", sans-serif !important;font-weight:bold;text-align:center;
  vertical-align:top}
.tg .tg-v56v{background-color:#ffe2e2;border-color:inherit;
  font-family:"Lucida Sans Unicode", "Lucida Grande", sans-serif !important;font-weight:bold;text-align:center;
  vertical-align:top}
</style>
<table class="tg"><thead>
  <tr>
    <th class="tg-fjfl"></th>
    <th class="tg-lklj">SBS Bookmarket</th>
    <th class="tg-lklj">2023 최우수</th>
    <th class="tg-lklj">2023 우수1</th>
    <th class="tg-lklj">2023 우수2</th>
  </tr></thead>
<tbody>
  <tr>
    <td class="tg-v56v">code</td>
    <td class="tg-fjfl">o</td>
    <td class="tg-fjfl">o</td>
    <td class="tg-fjfl">o</td>
    <td class="tg-fjfl">o</td>
  </tr>
  <tr>
    <td class="tg-v56v">doc</td>
    <td class="tg-fjfl">o</td>
    <td class="tg-fjfl">o</td>
    <td class="tg-fjfl">x</td>
    <td class="tg-fjfl">x</td>
  </tr>
  <tr>
    <td class="tg-v56v">영상</td>
    <td class="tg-fjfl">o</td>
    <td class="tg-fjfl">x</td>
    <td class="tg-fjfl">x</td>
    <td class="tg-fjfl">x</td>
  </tr>
  <tr>
    <td class="tg-v56v"><br>화면<br></td>
    <td class="tg-fjfl">React - PC<br>React - mobile<br>IOS<br>Android</td>
    <td class="tg-fjfl">React - PC<br>React - mobile</td>
    <td class="tg-fjfl">React - PC<br>React - mobile</td>
    <td class="tg-fjfl">React- PC<br>React - mobile</td>
  </tr>
</tbody></table>
<img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%B9%84%EA%B5%90%ED%91%9C.png">

## 9.25한성공학경진대회발표시 추가예정사항
+ 실시간 배송 기능 추가
+ 카카오, 네이버 등 소셜 로그인 추가
+ CSS 디자인 최적화
+ Azure, GCP 배포추가
