//
//  SignUpView.swift
//  hello
//
//  Created by 은옥 on 1/11/24.
//

// 주소, 전화번호 등 회원가입할 때 필요한 요소들을 더 추가해야함
import SwiftUI

struct SignUpView: View {
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var confirmPassword: String = ""
    @State private var isLoginSuccess: Bool = false

    var body: some View {
        VStack {
            TextField("사용자명", text: $username)
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())

            SecureField("비밀번호", text: $password)
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())

            SecureField("비밀번호 확인", text: $confirmPassword)
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())

            Button("회원가입") {
                // 회원가입 로직을 구현
                if password == confirmPassword {
                    // 비밀번호 확인이 일치할 때 사용자 등록 로직을 수행
                    let newUser = User(username: username, password: password)
                        UserManager.shared.register(user: newUser)
                        UserManager.shared.saveUser(user: newUser) // 회원가입 성공 시 사용자 정보 저장
                        isLoginSuccess = true
                    // 추가적인 로직 수행 (예: 회원가입 완료 메시지 표시 등)
                } else {
                    // 비밀번호 확인이 일치하지 않을 때 사용자에게 알림 등을 표시
                    // 추가적인 로직 수행 (예: 비밀번호 불일치 경고 메시지 표시 등)
                }
            }
            .padding()
            .foregroundColor(.white)
            .background(Color.green)
            .cornerRadius(8)
        }
        .padding()
        .navigationTitle("회원가입")
    }
}

struct SignUpView_Previews: PreviewProvider {
    static var previews: some View {
        SignUpView()
    }
}
