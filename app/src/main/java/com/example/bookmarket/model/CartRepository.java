package com.example.bookmarket.model;

import java.util.ArrayList;

public class CartRepository {

    public ArrayList<Book> cartBooks  = new ArrayList<Book>(); // 도서 관리 객체 변수

    public void addCartItems(Book book )  { // 장바구니에 도서를 추가하는 메소드
        int cnt = 0;
        ArrayList<Book> goodsList = cartBooks;
        Book goods = book;
        Book goodsQnt = new Book();

        for (int i = 0; i<goodsList.size();i++) { // 동일 도서가 있는 경우 도서 개수 1증가
            goodsQnt = goodsList.get(i);
            if (goodsQnt.bookid.equals(book.bookid)) {
                cnt++;
                goodsQnt.quantity += 1;
            }
        }

        if (cnt == 0) { // 동일 도서가 없는 경우 도서 개수를 1로 설정하고 추가
            goods.quantity = 1;
            goods.isCheck = true;
            cartBooks.add(goods);
        }
    }

    public int countCartItems( ){ // 장바구니에 담긴 도서의 총개수 산출 메소드
        int totalQuantity = 0;
        if (cartBooks != null) {
            for (int i=0; i< cartBooks.size(); i++) {
                totalQuantity += cartBooks.get(i).quantity;
            }
        }
        return totalQuantity;
    }

    public int grandTotalCartItems() { // 장바구니에 담긴 도서의 합계 산출 메소드
        int totalPrice = 0;

        for (int i=0 ; i< cartBooks.size(); i++) {
            if (cartBooks.get(i).isCheck)
                totalPrice += cartBooks.get(i).price * cartBooks.get(i).quantity;
        }
        return totalPrice;
    }
}
