# SBS Online Bookmarket
HSU 2024 Capstone Project
<h3><a href="https://shine-territory-e9e.notion.site/07680bdd72944508869fd49ae3224925?v=37539025f336409d958e9d0d0f73cffe">
      📜 REST API 명세서</a> | <a href="https://github.com/jihohyeseong/SBS/blob/main/image/SBS%EC%B5%9C%EC%A2%85%EA%B8%B0%EB%8A%A5%EA%B5%AC%EC%84%B1%EB%8F%841.png">
       📖 기능구성도</a></h3>
<br/>

## 목차
<details open>
  <summary><h2>📋 Table of Contents</h2></summary>
  <ul>
    <li><a href="#개발자소개">개발자소개</a></li>
    <li><a href="#프로젝트-상세설명">프로젝트 상세설명</a></li>
    <li><a href="#%EF%B8%8F-기술-스택">기술 스택</a></li>
    <li><a href="#프로젝트-구조">프로젝트 구조</a></li>
    <li><a href="#구현화면">구현화면</a></li>
    <ul>
      <li><a href="#웹화면이혜성-송승엽">웹 화면</a></li>
      <li><a href="#안드로이드-앱-화면나찬웅">안드로이드 앱 화면</a></li>
      <li><a href="#ios-앱-화면하은옥">IOS 앱 화면</a></li>
    </ul>
    <li><a href="#비교표">비교표</a></li>
    <li><a href="#925한성공학경진대회발표시-추가예정사항">9.25 한성공학경진대회 추가사항</a></li>
  </ul> 
</details>
<br/>

## 개요
기존의 오프라인(서점)에서 책을 구매하는 소비자를 위해 간편하고 빠른 
서비스를 제공하는 온라인 북 쇼핑몰을 제공
<br/>
<br/>

## 개발자소개
+ **이혜성**: 팀장, Backend Spring담당
+ **송승엽**: 반응형 React담당 [🖥️[PC화면 시연동영상](https://www.youtube.com/watch?v=-A_0slTbHPo)] | [📱[모바일화면 시연동영상](https://www.youtube.com/watch?v=hej5DR7b_rE&t=1s)]
+ **하은옥**: ios Application담당 [아이폰 시연동영상](https://youtu.be/j4eroFaMCKU)
+ **나찬웅**: android Application담당 [안드로이드 시연동영상](https://youtu.be/CmmwL0ND3zA?si=giIplmFsghhF10fg)
<br/>
<br/>

## 프로젝트 상세설명
+ 기본적인 온라인 도서 쇼핑몰을 웹, 안드로이드 어플리케이션, ios 어플리케이션 3가지 형태로 구현한다.
+ chatGPT api를 활용하여 작성된 후기 댓글들을 요약해서 보여준다.
+ 온라인 e북을 이용할 때 책에서 묘사하는 배경을 상상에 그치는것이 아닌 text-to-image ai를 활용하여 이미지로 회원에게 보여준다.
+ 온라인 e북을 이용할 때 text-to-speach ai를 활용하여 회원에게 책을 읽어준다.

<br/>

## ✔️ 기술 스택
<div>
<table>
   <tr>
      <td colspan="2" align="center">
        Language
      </td>
      <td colspan="4">
        <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
        <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
        <img src="https://img.shields.io/badge/swift-F05138?style=for-the-badge&logo=swift&logoColor=white">
      </td>
   </tr>
   <tr>
      <td colspan="2" align="center">
        Library & Framework
      </td>
      <td colspan="4">
        <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> 
        <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> 
        <img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> 
        <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> 
        <img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> 
      </td>
   </tr>
   <tr>
      <td colspan="2" align="center">
        Database
      </td>
      <td colspan="4">
        <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
        <img src="https://img.shields.io/badge/amazon rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">
      </td>
   </tr>
   <tr>
      <td colspan="2" align="center">
        Tool
      </td>
      <td colspan="4">
          <img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
          <img src="https://img.shields.io/badge/visualstudiocode-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white">
          <img src="https://img.shields.io/badge/androidstudio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white">
          <img src="https://img.shields.io/badge/xcode-147EFB?style=for-the-badge&logo=xcode&logoColor=white">
      </td>
   </tr>
   <tr>
      <td colspan="2" align="center">
        etc.
      </td>
      <td colspan="4">
          <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
      </td>
   </tr>
</table>
</div>
<br/>

## 프로젝트 구조
![프로젝트 구조](https://github.com/jihohyeseong/SBS/blob/main/image/27%EC%A1%B0_%EC%9D%B4%EB%AF%B8%EC%A7%80_%EC%A3%BC%EC%9A%94%20%EC%A0%81%EC%9A%A9%20%EA%B8%B0%EC%88%A0%20%EB%B0%8F%20%EA%B5%AC%EC%A1%B0.jpg)
<br/>

## 구현화면

### 웹화면(이혜성, 송승엽)
<details>
<summary> ▶️ Click! </summary>
<ul>
    <li>회원가입</li></li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.png" width="1300" height="500">
    <li>로그인</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%A1%9C%EA%B7%B8%EC%9D%B8.png" width="1300" height="500">
    <li>메인페이지</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%A9%94%EC%9D%B8%ED%8E%98%EC%9D%B4%EC%A7%80.png" width="1300" height="500">
    <li>책 상세페이지</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%B1%85%EC%83%81%EC%84%B81.png" width="1300" height="500">
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%B1%85%EC%83%81%EC%84%B82.png" width="1300" height="500">
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%B1%85%EC%83%81%EC%84%B83.png" width="1300" height="500">
    <li>목록별 페이지</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%AA%A9%EB%A1%9D%EB%B3%841.png" width="1300" height="500">
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%AA%A9%EB%A1%9D%EB%B3%842.png" width="1300" height="500">
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%AA%A9%EB%A1%9D%EB%B3%843.png" width="1300" height="500">
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%AA%A9%EB%A1%9D%EB%B3%844.png" width="1300" height="500">
    <li>장바구니</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%9E%A5%EB%B0%94%EA%B5%AC%EB%8B%88.png" width="1300" height="500">
    <li>구매</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EA%B5%AC%EB%A7%A4%EA%B2%B0%EC%A0%9C.jpg" width="300" height="500">
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EA%B5%AC%EB%A7%A4.png" width="1300" height="500">
    <li>마이메뉴</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%A7%88%EC%9D%B4%EB%A9%94%EB%89%B4.png" width="1300" height="500">
    <li>관리자페이지</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EA%B4%80%EB%A6%AC%EC%9E%90%ED%8E%98%EC%9D%B4%EC%A7%80.png" width="1300" height="500">
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EA%B4%80%EB%A6%AC%EC%9E%90%EC%9E%AC%EA%B3%A0.png" width="1300" height="500">
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EA%B4%80%EB%A6%AC%EC%9E%90%EC%A0%95%EC%82%B0.png" width="1300" height="500">
    <li>온라인e북</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%98%A8%EB%9D%BC%EC%9D%B8E%EB%B6%81.png" width="1300" height="500">
    <li>후기 댓글 요약</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EB%8C%93%EA%B8%80%EC%9A%94%EC%95%BD.png" width="1300" height="500">
    <li>TTS(Text-to-Speech)AI 책 읽어주기, 이미지변환</li>
        <img src="https://github.com/jihohyeseong/SBS/blob/main/image/ai.png" height="500">
</ul>
</details>
<br/>

### 웹 앱 화면
<img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%9B%B9%EC%95%B11.jpg" width="300" height="500">
<img src="https://github.com/jihohyeseong/SBS/blob/main/image/%EC%9B%B9%EC%95%B12.jpg" width="300" height="500">
<br/>

### 안드로이드 앱 화면(나찬웅)
구현중
<br/>


### IOS 앱 화면(하은옥)
<li>회원가입</li>
[![IMG_4480](https://github.com/jihohyeseong/SBS/assets/93129991/154325bb-2695-4be6-8d27-9156eef0ba5f.png)](https://github.com/jihohyeseong/SBS/blob/ios/screenshots/IMG_4480.PNG)

<li>로그안</li>
![IMG_4481](https://github.com/jihohyeseong/SBS/assets/93129991/3a89537d-f821-4e13-9c9f-44a1a3f0d898)

<li>메인페이지</li>
![IMG_4482](https://github.com/jihohyeseong/SBS/assets/93129991/1e9f6555-43f4-4f7f-94e0-8fe4e9978e83)
<li>세부정보</li>
![IMG_4483](https://github.com/jihohyeseong/SBS/assets/93129991/ed60d077-c2ab-495e-847a-9fcb2672d9d9)

<li>댓글</li>
![IMG_4485](https://github.com/jihohyeseong/SBS/assets/93129991/1273bf97-77d3-4c59-9dc1-64ae75735ea1)

<li>책정렬</li>
![IMG_4486](https://github.com/jihohyeseong/SBS/assets/93129991/6ac04aef-f092-413c-85d9-8d78c76c8bd6)
![IMG_4487](https://github.com/jihohyeseong/SBS/assets/93129991/61901c79-a59a-46d7-932f-b65e7b6b00c0)

<li>장바구니</li>
![IMG_4489](https://github.com/jihohyeseong/SBS/assets/93129991/0efbc5cd-74ac-43e5-abd8-1d742ed362f9)

<li>카카오페이</li>
![IMG_4490](https://github.com/jihohyeseong/SBS/assets/93129991/85180fb6-79c5-4160-8f90-a26524952835)

<li>마이페이지</li>
![IMG_4491](https://github.com/jihohyeseong/SBS/assets/93129991/b22b7ed4-bc4f-4d1c-b655-ee2f8ce7c851)

<li>AI댓글요약</li>
![IMG_4492](https://github.com/jihohyeseong/SBS/assets/93129991/ad0cee50-9ca9-42c4-828c-a89b3378e982)
<br/>


## 비교표

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
<br/>
2023 최우수 : https://github.com/capstone-aloha     
<br/>
2023 우수1 : https://github.com/TeamCookCaps  
<br/>
2023 우수2 : https://github.com/godi00/capstone  

<br/>


## 9.25한성공학경진대회발표시 추가예정사항
+ 실시간 배송 기능 추가
+ 카카오, 네이버 등 소셜 로그인 추가
+ CSS 디자인 최적화
+ Azure, GCP 배포추가
