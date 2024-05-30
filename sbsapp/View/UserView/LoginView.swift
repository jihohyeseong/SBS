//
//  LoginView.swift
//  sbsapp
//
//  Created by 은옥 on 3/7/24.
//

import SwiftUI
import AuthenticationServices

struct LoginView: View {
    @Environment(\.presentationMode) var presentationMode
    @Binding var isLoggedIn: Bool // 로그인 상태 바인딩
    
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var isLoginSuccess: Bool = false
    @State private var loginErrorMessage: String = ""
    @State private var isSignupViewPresented: Bool = false
    
    private let userManager = UserManager() // UserManager 인스턴스 생성
    
    var body: some View {
        NavigationView {
            VStack(spacing: 20) { // 여백 조정
                backButton
                
                loginFields
                
                loginButton
                
                loginStatusMessage
                
                appleSignInButton
                
                signupButton
                
                Spacer()
            }
            .padding(.horizontal, 16) // 좌우 패딩 조정
            .padding(.top, 10) // 상단 패딩 조정
            .navigationTitle("로그인")
            .navigationBarTitleDisplayMode(.inline) // 여백을 없애기 위해 추가
            .toolbar {
                ToolbarItem(placement: .principal) {
                    Text("로그인")
                        .font(.largeTitle) // 글씨 크기 조정
                        .bold() // 글씨 두껍게
                }
            }
            .background(
                NavigationLink(destination: ContentView(), isActive: $isLoginSuccess) {
                    EmptyView()
                }
                .hidden()
            )
        }
    }
    
    private var backButton: some View {
        HStack {
            Button(action: {
                presentationMode.wrappedValue.dismiss()
            }) {
                Image(systemName: "chevron.left")
                    .foregroundColor(.blue)
                    .padding()
            }
            Spacer()
        }
    }
    
    private var loginFields: some View {
        VStack(spacing: 10) { // 필드 사이 여백 조정
            TextField("사용자이름", text: $username)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal)
            
            SecureField("비밀번호", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal)
        }
    }
    
    private var loginButton: some View {
        Button(action: {
            performLogin()
        }) {
            Text("로그인")
                .padding()
                .frame(maxWidth: .infinity)
                .background(Color.blue)
                .foregroundColor(.white)
                .cornerRadius(10)
        }
        .padding(.horizontal)
    }
    
    private var loginStatusMessage: some View {
        Group {
            if isLoginSuccess {
                Text("로그인 성공")
                    .foregroundColor(.green)
                    .padding()
                
            } else if !loginErrorMessage.isEmpty {
                Text(loginErrorMessage)
                    .foregroundColor(.red)
                    .padding()
            }
        }
    }
    
    private var appleSignInButton: some View {
        SignInWithAppleButton(
            onRequest: { request in
                request.requestedScopes = [.email]
            },
            onCompletion: { result in
                switch result {
                case .success(let authorization):
                    print("Apple 로그인 성공: \(authorization)")
                case .failure(let error):
                    print("Apple 로그인 실패: \(error.localizedDescription)")
                    loginErrorMessage = "Apple 로그인에 실패했습니다."
                }
            }
        )
        .padding(.horizontal)
        .frame(height: 50)
    }
    
    private var signupButton: some View {
        Button(action: {
            isSignupViewPresented = true
        }) {
            Text("회원가입")
                .padding()
                .frame(maxWidth: .infinity)
                .background(Color.green)
                .foregroundColor(.white)
                .cornerRadius(10)
        }
        .padding(.horizontal)
        .sheet(isPresented: $isSignupViewPresented) {
            JoinView()
        }
    }
    
    private func performLogin() {
        userManager.loginUser(username: username, password: password) { success, userId in
            DispatchQueue.main.async {
                if success {
                    handleLoginSuccess(userId: userId)
                } else {
                    handleLoginFailure()
                }
            }
        }
    }

    private func handleLoginSuccess(userId: Int?) {
        isLoginSuccess = true
        isLoggedIn = true // 로그인 성공 시 상태 업데이트
        if let userId = userId {
            UserDefaults.standard.set(userId, forKey: "userId")
        }
        loginErrorMessage = ""
        print("Login success, userId: \(String(describing: userId))") // 디버깅을 위해 출력
    }

    private func handleLoginFailure() {
        isLoginSuccess = false
        loginErrorMessage = "로그인에 실패했습니다. 다시 시도해주세요."
        print("로그인에 실패했습니다.")
    }
}
