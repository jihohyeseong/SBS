//
//  ProductDetailView.swift
//  sbsapp
//
//  Created by 은옥 on 3/7/24.
//

import SwiftUI

struct ProductDetailView: View {
    var book: Book
    var bookId: Int
    var currentUser: String
    private let cartManager = CartManager.shared
    
    @State private var itemCount: Int = 1
    @State private var cartMessage: String = ""
    @State private var isImageDetailPresented: Bool = false
        
    var body: some View {
        VStack {
            ScrollView {
                // 책 이미지
                if let url = URL(string: book.imageUrl) {
                    AsyncImage(url: url) { phase in
                        switch phase {
                        case .empty:
                            ProgressView()
                                .frame(maxWidth: .infinity, maxHeight: 400)
                        case .success(let image):
                            image
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(maxWidth: .infinity, maxHeight: 400)
                                .onTapGesture {
                                    isImageDetailPresented.toggle()
                                }
                        case .failure(let error):
                            Image(systemName: "book")
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(maxWidth: .infinity, maxHeight: 400)
                        @unknown default:
                            Image(systemName: "book")
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(maxWidth: .infinity, maxHeight: 400)
                        }
                    }
                } else {
                    Image(systemName: "book")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(maxWidth: .infinity, maxHeight: 400)
                }
                
                // 책 세부 정보
                VStack(alignment: .center) {
                    Divider()
                    
                    Text(book.bookName)
                        .font(.title)
                        .padding()
                        .lineLimit(3)
                        .multilineTextAlignment(.center)
                    
                    VStack(alignment: .center, spacing: 10) {
                        Text("지은이: \(book.author)")
                        Text("출판사: \(book.publisher)")
                        Text("분야: \(book.category)")
                        
                        if let price = book.price {
                            Text("가격: \(formatPrice(price: price))")
                                .foregroundColor(.green)
                        } else {
                            Text("가격: Not available")
                                .foregroundColor(.red)
                        }
                    }
                    .multilineTextAlignment(.center) // 텍스트 가운데 정렬
                    
                    Divider()
                    
                    Text(book.description)
                        .multilineTextAlignment(.center)
                    
                    Divider()
                    
                    Text(book.releasedate ?? "Release Date Unavailable")
                    
                    ProductActionButtonsView(book: book, itemCount: $itemCount, cartMessage: $cartMessage, cartManager: cartManager)
                    
                    if !cartMessage.isEmpty {
                        Text(cartMessage)
                            .foregroundColor(.red)
                            .padding()
                    }
                }
                .padding()
            }
            .navigationTitle("세부 정보")
        }
        .sheet(isPresented: $isImageDetailPresented) {
            ImageDetailView(imageUrl: book.imageUrl)
        }
    }

    private func formatPrice(price: Int) -> String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.currencyCode = "KRW"
        
        return formatter.string(from: NSNumber(value: price)) ?? "Invalid Price"
    }
}

struct ImageDetailView: View {
    var imageUrl: String
    
    var body: some View {
        VStack {
            if let url = URL(string: imageUrl) {
                AsyncImage(url: url) { phase in
                    switch phase {
                    case .empty:
                        ProgressView()
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                    case .success(let image):
                        image
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                    case .failure:
                        Image(systemName: "book")
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                    @unknown default:
                        Image(systemName: "book")
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                    }
                }
            } else {
                Image(systemName: "book")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
        .background(Color.black)
        .edgesIgnoringSafeArea(.all)
    }
}
