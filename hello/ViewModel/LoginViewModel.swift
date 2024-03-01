//
//  LoginViewModel.swift
//  hello
//
//  Created by 은옥 on 2/29/24.
//
import Foundation

protocol AuthenticationProtocol {
  var IdIsValid : Bool {get}
  var PasswordIsValid : Bool {get}
  var xmarkOnId : Bool {get}
  var xmarkOnPassword : Bool {get}
}

struct LoginViewModel : AuthenticationProtocol {
  var id : String?
  var password : String?
  
  var IdIsValid: Bool {
    return id?.isEmpty == false
  }
  
  var PasswordIsValid: Bool {
    return password?.isEmpty == false
  }
  
  var xmarkOnId : Bool {
    return id?.isEmpty == false
  }
  
  var xmarkOnPassword : Bool {
    return password?.isEmpty == false
  }
}

