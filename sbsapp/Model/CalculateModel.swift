//
//  CalculateModel.swift
//  sbsapp
//
//  Created by 은옥 on 5/10/24.
//

import UIKit

struct CalculateDto: Codable {
    let id: Int
    let username: String
    let quantity: Int
    let bookname: String
    let price: Int
    
    init(id: Int, username: String, quantity: Int, bookname: String, price: Int) {
        self.id = id
        self.username = username
        self.quantity = quantity
        self.bookname = bookname
        self.price = price
    }
}

