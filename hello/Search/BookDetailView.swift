//
//  ProductDetailView.swift
//  hello
//
//  Created by 은옥 on 1/11/24.
//

import SwiftUI

struct ProductDetailView: View {
    var book: Book
    
    var body: some View {
        VStack {
            Image(book.imageName)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(height: 200)
            
            Divider()
            
            Text(book.title)
                .font(.title)
                .padding()
            
            Text("지은이: \(book.author)")
            Text("출판사: \(book.publisher)")
            Text("분야: \(book.category)")
            
            Divider()

            Text("가격: \(formatPrice(price:book.price))")
                .foregroundColor(.green)
            
            Divider()
            
            Text(book.description) // 제품 설명 추가
            
            ProductActionButtonsView()
            
            Spacer()
        }
        .padding()
        .navigationTitle(book.title)
    }
}

struct ProductActionButtonsView: View {
    var body: some View {
        HStack {
            Button(action: {
                // 장바구니에 제품 추가 로직
                print("장바구니에 추가")
            }) {
                Text("장바구니에 담기")
                    .foregroundColor(.white)
                    .padding()
                    .background(Color.blue)
                    .cornerRadius(8)
            }
            
            Spacer()
            
            Button(action: {
                // 찜목록에 제품 추가 로직
                print("찜목록에 추가")
            }) {
                Text("찜하기")
                    .foregroundColor(.white)
                    .padding()
                    .background(Color.orange)
                    .cornerRadius(8)
            }
        }
    }
}

