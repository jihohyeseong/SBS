package com.example.bookmarket.model;

public class Book {

    public String bookid;
    public String name;
    public int price = 0;
    public String date ;
    public String writer;
    public String page;
    public String description;
    public String category;
    public String image;
    public int quantity =0 ; // 장바구니에 담은 도서 개수
    public boolean isCheck = false; // 장바구니에서 체크박스 선택 여부

}
