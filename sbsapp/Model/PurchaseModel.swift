//
//  PurchaseModel.swift
//  sbsapp
//
//  Created by 은옥 on 3/26/24.
//

import Foundation

struct Purchase {
    var id: Int
    var userId: Int
    var bookId: Int
    var quantity: Int
    var bookname: String?
    var price: Int?
    var author: String?
    var publisher: String?
    var imageUrl: String?
    
    init(id: Int, userId: Int, bookId: Int, quantity: Int, bookname: String? = nil, price: Int? = nil, author: String? = nil, publisher: String? = nil, imageUrl: String? = nil) {
        self.id = id
        self.userId = userId
        self.bookId = bookId
        self.quantity = quantity
        self.bookname = bookname
        self.price = price
        self.author = author
        self.publisher = publisher
        self.imageUrl = imageUrl
    }
}

struct PurchaseDto: Codable {
    var id: Int
    var userId: Int
    var bookId: Int
    var quantity: Int64
    var bookname: String?
    var price: Int
    var author: String?
    var publisher: String?
    var imageUrl: String?
}
