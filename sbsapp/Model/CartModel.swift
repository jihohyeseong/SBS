//
//  CartModel.swift
//  sbsapp
//
//  Created by 은옥 on 3/26/24.
//

import UIKit
import Foundation

struct Cart: Identifiable, Decodable {
    var id: Int64
    var quantity: Int64
    var book_id: Int64
    var user_id: Int64
    
    init(id: Int64, quantity: Int64, book_id: Int64, user_id: Int64) {
        self.id = id
        self.quantity = quantity
        self.book_id = book_id
        self.user_id = user_id
    }
}


