//
//  JoinView.swift
//  sbsapp
//
//  Created by 은옥 on 3/22/24.
//

import SwiftUI

struct JoinView: View {
    @State private var email: String = ""
    @State private var password: String = ""
    @State private var confirmPassword: String = ""
    @State private var username: String = ""
    @State private var phone_num: String = ""
    @State private var address: String = ""
    @State private var errorMessage: String = ""

    // UserManager 인스턴스 생성
    let userManager = UserManager()

    var body: some View {
        VStack {
            TextField("이메일", text: $email)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            SecureField("비밀번호", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            TextField("사용자 이름", text: $username)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            TextField("전화번호", text: $phone_num)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            TextField("주소", text: $address)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()

            Text(errorMessage)
                .foregroundColor(.red)
                .padding()

            Button(action: {
                let joinDto = JoinDto(address: address, username: username, email: email, password: password, phoneNum: phone_num)
                userManager.joinUser(joinDto: joinDto) { result in
                    switch result {
                    case .success:
                        DispatchQueue.main.async {
                            guard let window = UIApplication.shared.windows.first else { return }
                            window.rootViewController = UIHostingController(rootView: ContentView())
                            window.makeKeyAndVisible()
                        }
                    case .failure(let error):
                        errorMessage = error.localizedDescription
                        print(email + password + username + phone_num + address)
                    }
                }
            }) {
                Text("회원가입")
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color.green)
                    .foregroundColor(.white)
                    .cornerRadius(10)
            }
            .padding()
            .navigationTitle("회원가입")
        }
    }
}


struct JoinView_Previews: PreviewProvider {
    static var previews: some View {
        JoinView()
    }
}
