//
//  CommentModel.swift
//  sbsapp
//
//  Created by 은옥 on 3/22/24.
//

import UIKit

struct Comment: Codable, Identifiable {
    var id: Int?
    var bookId: Int
    var username: String
    var content: String
    var bookname: String
}

struct CommentDto: Codable {
    var id: Int?
    var bookId: Int
    var username: String
    var content: String
    var bookname: String
}

