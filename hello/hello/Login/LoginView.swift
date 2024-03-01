//
//  LoginView.swift
//  hello
//
//  Created by 은옥 on 1/11/24.
//
import UIKit
import Alamofire

class JoinVC: UIViewController {

    @IBOutlet weak var nametextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func tapJoinButton(_ sender: UIButton) {
        guard let email = emailTextField.text, !email.isEmpty else { return }
        guard let username = nametextField.text, !username.isEmpty else { return }
        guard let password = passwordTextField.text, !password.isEmpty else { return }
        let url = "http://localhost:8080/join"
        let param: Parameters = [
            "username":username,
            "email":email,
            "password":password
        ]
        let headers: HTTPHeaders = [
            "Accept": "application/json"
        ]
        AF.request(url, method: .post, parameters: param, encoding: JSONEncoding.default, headers: headers).responseString() { response in
                print(response)
        }
        
    }
}

import SwiftUI

struct LoginView: View {
    @Environment(\.presentationMode) var presentationMode
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var isLoginSuccess: Bool = false
    @State private var isSignUpSheetPresented: Bool = false
    
    var body: some View {
        VStack {
            if isLoginSuccess {
                Text("로그인 성공!")
                    .font(.headline)
                    .padding()
                    .onAppear {
                        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                            // 로그인 성공 후 2초 후에 모달 닫기
                            self.presentationMode.wrappedValue.dismiss()
                        }
                    }
            }
            else {
                TextField("사용자명", text: $username)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())

                SecureField("비밀번호", text: $password)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())

                Button("로그인") {
                    isLoginSuccess = UserManager.shared.login(username: username, password: password)
                }
                .padding()
                .foregroundColor(.white)
                .background(Color.blue)
                .cornerRadius(8)

                Button("회원가입") {
                    // 회원가입 버튼을 누를 때 SignUpSheet를 표시하기 위해
                    // isSignUpSheetPresented 값을 토글합니다.
                    isSignUpSheetPresented.toggle()
                }
                .padding()
                .foregroundColor(.white)
                .background(Color.green)
                .cornerRadius(8)
            }
            HStack {
                Button("뒤로가기") {
                    // 뒤로가기 버튼을 누를 때 창을 닫습니다.
                    presentationMode.wrappedValue.dismiss()
                }
                .foregroundColor(.blue)
            }
            .padding(.top, 10)
        }
        .padding()
        .sheet(isPresented: $isSignUpSheetPresented) {
            SignUpView()
        }
        .padding()
        .navigationTitle("로그인")
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}

