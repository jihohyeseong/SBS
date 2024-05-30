//
//  JoinModel.swift
//  sbsapp
//
//  Created by 은옥 on 4/2/24.
//

import Foundation

struct Join: Codable {
    var email : String
    var password : String
    var username : String
    var phoneNum : String
    var address : String
    var role : String
    
    init(email: String, password: String, username: String, phoneNum: String, address: String, role: String) {
        self.email = email
        self.password = password
        self.username = username
        self.phoneNum = phoneNum
        self.address = address
        self.role = role
    }
}

struct JoinDto: Codable {
    var address: String
    var username: String
    var email: String
    var password: String
    var phoneNum: String
    
    init(address: String, username: String, email: String, password: String, phoneNum: String) {
        self.address = address
        self.username = username
        self.email = email
        self.password = password
        self.phoneNum = phoneNum
    }
} // id는 회원이 정하는 게 아니라 관리자가 관리하기 위해 만들어놓은 것이므로 포함하지 않는다.
