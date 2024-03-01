//
//  User.swift
//  hello
//
//  Created by 은옥 on 1/31/24.
//

import UIKit

struct SignUpData: Codable {
  var username: String
  var email: String
  var password1: String
  var password2: String
  var phonenumber: String
    
  init (dic: [String:Any]) {
        
    self.username = dic["username"] as? String ?? ""
    self.email = dic["email"] as? String ?? ""
    self.password1 = dic["password1"] as? String ?? ""
    self.password2 = dic["password2"] as? String ?? ""
    self.phonenumber = dic["phonenumber"] as? String ?? ""
  }
}

struct SignInData: Codable {
  let username: String
  let password: String
  
  struct Events: Codable {
    var images: String
  }
}
