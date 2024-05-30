//
//  EditProfileView.swift
//  sbsapp
//
//  Created by 은옥 on 3/21/24.
//

import SwiftUI

struct EditProfileView: View {
    @State private var user: User
    @State private var email: String
    @State private var address: String
    @State private var phoneNum: String
    @State private var password: String = ""
    @State private var errorMessage: String = ""
    @State private var isLoading: Bool = false
    @State private var showError: Bool = false

    init(user: User) {
        _user = State(initialValue: user)
        _email = State(initialValue: user.email)
        _address = State(initialValue: user.address)
        _phoneNum = State(initialValue: user.phoneNum)
    }

    var body: some View {
        VStack {
            if isLoading {
                ProgressView()
                    .padding()
            } else {
                Form {
                    Section(header: Text("회원 정보 수정")) {
                        HStack {
                            Text(user.username)
                                .foregroundColor(.gray)
                        }

                        TextField("이메일", text: $email)
                            .keyboardType(.emailAddress)
                            .textContentType(.emailAddress)
                            .autocapitalization(.none)
                            .disableAutocorrection(true)

                        TextField("주소", text: $address)
                            .textContentType(.fullStreetAddress)
                            .autocapitalization(.words)
                            .disableAutocorrection(true)

                        TextField("전화번호", text: $phoneNum)
                            .keyboardType(.phonePad)
                            .textContentType(.telephoneNumber)
                            .disableAutocorrection(true)

                        SecureField("비밀번호", text: $password)
                            .textContentType(.newPassword)
                            .disableAutocorrection(true)
                    }

                    Button(action: {
                        updateUser()
                    }) {
                        Text("정보 수정")
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(Color.blue)
                            .foregroundColor(.white)
                            .cornerRadius(10)
                    }
                }
                .padding()
            }
        }
        .navigationTitle("회원 정보 수정")
        .alert(isPresented: $showError) {
            Alert(title: Text("오류"), message: Text(errorMessage), dismissButton: .default(Text("확인")))
        }
    }

    private func updateUser() {
        let joinDto = JoinDto(address: address, username: user.username, email: email, password: password.isEmpty ? user.password : password, phoneNum: phoneNum)
        isLoading = true
        UserinfoManager.shared.updateUserInfo(dto: joinDto) { result in
            DispatchQueue.main.async {
                isLoading = false
                switch result {
                case .success(let updatedUser):
                    self.user = updatedUser
                case .failure(let error):
                    self.errorMessage = error.localizedDescription
                    self.showError = true
                }
            }
        }
    }
}

struct EditProfileView_Previews: PreviewProvider {
    static var previews: some View {
        EditProfileView(user: User(id: 0, address: "서울", email: "test@test.com", password: "password", phoneNum: "010-0000-0000", role: "user", username: "testuser"))
    }
}
