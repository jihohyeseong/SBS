//
//  UserModel.swift
//  sbsapp
//
//  Created by 은옥 on 3/15/24.
//

import UIKit

struct User: Codable {
    var id: Int64
    var address: String
    var email: String
    var password: String
    var phoneNum: String
    var role: String
    var username: String
    
    init(id: Int64, address: String,  email: String, password: String, phoneNum: String, role: String, username: String) {
        self.id = id
        self.address = address
        self.email = email
        self.password = password
        self.phoneNum = phoneNum
        self.role = role
        self.username = username
    }
}

struct UserDto: Codable {
    var id: Int64
    var address: String
    var email: String
    var password: String
    var phoneNum: String
    var role: String
    var username: String
}
