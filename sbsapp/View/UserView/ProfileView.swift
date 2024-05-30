//
//  ProfileView.swift
//  sbsapp
//
//  Created by 은옥 on 5/01/24.
//

import SwiftUI

/*struct ProfileView: View {
    @State private var user: User?
    @State private var username: String = ""
    @State private var email: String = ""
    @State private var address: String = ""
    @State private var phone_num: String = ""
    @State private var password: String = ""
    @State private var role: String = ""
    @State private var errorMessage: String = ""
    @State private var isLoading: Bool = false

    var body: some View {
        VStack {
            if isLoading {
                ProgressView()
                    .padding()
            } else if let user = user {
                Form {
                    Section(header: Text("회원 정보")) {
                        TextField("사용자 이름", text: $username)
                        TextField("이메일", text: $email)
                        TextField("주소", text: $address)
                        TextField("전화번호", text: $phone_num)
                        SecureField("비밀번호", text: $password)
                        TextField("역할", text: $role)
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
                .onAppear {
                    username = user.username
                    email = user.email
                    address = user.address
                    phone_num = user.phone_num
                    password = user.password
                    role = user.role
                }
                .padding()
            } else {
                Text("회원 정보를 불러오는 중 오류가 발생했습니다.")
                    .foregroundColor(.red)
            }
        }
        .navigationTitle("회원 정보")
        .onAppear(perform: fetchUserInfo)
    }

    private func fetchUserInfo() {
        isLoading = true
        UserManager.fetchUserInfoData { result in
            DispatchQueue.main.async {
                isLoading = false
                switch result {
                case .success(let user):
                    self.user = user
                    self.username = user.username
                    self.email = user.email
                    self.address = user.address
                    self.phone_num = user.phone_num
                    self.password = user.password
                    self.role = user.role
                case .failure(let error):
                    self.errorMessage = error.localizedDescription
                }
            }
        }
    }

    private func updateUser() {
        guard let user = user else { return }
        let userDto = UserDto(id: user.id, address: address, email: email, password: password, phone_num: phone_num, role: role, username: username)
        let userManager = UserManager()
        userManager.updateUser(userDto: userDto) { result in
            DispatchQueue.main.async {
                switch result {
                case .success(let updatedUser):
                    self.user = updatedUser
                case .failure(let error):
                    self.errorMessage = error.localizedDescription
                }
            }
        }
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
    }
}
*/
