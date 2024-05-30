//
//  ProductActionButtonsView.swift
//  sbsapp
//
//  Created by 은옥 on 5/30/24.
//

import SwiftUI

struct ProductActionButtonsView: View {
    var book: Book
    @Binding var itemCount: Int
    @Binding var cartMessage: String
    var cartManager: CartManager
    
    var body: some View {
        VStack {
            HStack {
                Stepper(value: $itemCount, in: 1...9) {
                    Text("\(itemCount)")
                }
                .padding()
                
                Spacer()
                
                Button(action: {
                    let bookId = Int64(book.id)
                    let cartDto = CartManager.CartDto(id: nil, userId: nil, bookId: bookId, quantity: Int64(itemCount), bookname: book.bookName, price: Int64(book.price ?? 0), author: book.author, publisher: book.publisher, imageUrl: book.imageUrl)
                    cartManager.addCart(bookId: Int(bookId), cartDto: cartDto) { result in
                        DispatchQueue.main.async {
                            switch result {
                            case .success:
                                cartMessage = "장바구니에 추가되었습니다."
                            case .failure:
                                cartMessage = "장바구니에 추가하는데 실패했습니다."
                            }
                        }
                    }
                }) {
                    Text("장바구니에 담기")
                        .foregroundColor(.white)
                        .padding()
                        .background(Color.blue)
                        .cornerRadius(8)
                }
            }
            .padding()
            
            NavigationLink(destination: CommentView(bookId: book.id, currentUser: "currentUser")) {
                Text("댓글 보기")
                    .foregroundColor(.white)
                    .padding()
                    .background(Color.orange)
                    .cornerRadius(8)
            }
        }
        .padding()
    }
}
