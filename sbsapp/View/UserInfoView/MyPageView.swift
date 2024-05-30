//
//  MyPageView.swift
//  sbsapp
//
//  Created by 은옥 on 3/19/24.
//

import SwiftUI

struct MyPageView: View {
    @State private var user: User?
    @State private var errorMessage: String = ""
    @State private var isLoading: Bool = false
    @State private var showError: Bool = false

    var body: some View {
        NavigationView {
            VStack {
                if isLoading {
                    ProgressView()
                        .padding()
                } else if let user = user {
                    VStack(spacing: 20) { // 여백 조정
                        VStack(alignment: .leading, spacing: 10) {
                            Text("회원 정보")
                                .font(.headline)
                            
                            Text("사용자 이름: \(user.username)")
                            Text("이메일: \(user.email)")
                            Text("주소: \(user.address)")
                            Text("전화번호: \(user.phoneNum)")
                        }
                        .padding()
                        .background(Color(UIColor.systemGray6))
                        .cornerRadius(10)
                        
                        NavigationLink(destination: EditProfileView(user: user)) {
                            Text("회원 정보 수정")
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.blue)
                                .foregroundColor(.white)
                                .cornerRadius(10)
                        }
                        
                        NavigationLink(destination: OrderHistoryView()) {
                            Text("구매 내역 목록")
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.blue)
                                .foregroundColor(.white)
                                .cornerRadius(10)
                        }
                        
                        NavigationLink(destination: MyCommentView()) {
                            Text("내가 쓴 댓글 목록")
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.blue)
                                .foregroundColor(.white)
                                .cornerRadius(10)
                        }
                        
                        Button(action: {
                            deleteUser()
                        }) {
                            Text("회원 탈퇴")
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.red)
                                .foregroundColor(.white)
                                .cornerRadius(10)
                        }
                    }
                    .padding()
                } else {
                    Text("회원 정보를 불러오는 중 오류가 발생했습니다.")
                        .foregroundColor(.red)
                }
            }
            .navigationTitle("마이 페이지")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    Text("마이 페이지")
                        .font(.largeTitle) // 타이틀 글씨 크기 조정
                        .bold() // 타이틀 글씨 두껍게
                }
            }
            .onAppear(perform: fetchUserInfo)
            .alert(isPresented: $showError) {
                Alert(title: Text("오류"), message: Text(errorMessage), dismissButton: .default(Text("확인")))
            }
        }
    }

    private func fetchUserInfo() {
        isLoading = true
        UserinfoManager.shared.getUserInfo { result in
            DispatchQueue.main.async {
                isLoading = false
                switch result {
                case .success(let user):
                    self.user = user
                case .failure(let error):
                    if let httpResponse = (error as? URLError)?.userInfo[NSURLErrorFailingURLErrorKey] as? HTTPURLResponse {
                        print("Status code: \(httpResponse.statusCode)")
                        print("Headers: \(httpResponse.allHeaderFields)")
                    }
                    print("Error: \(error.localizedDescription)")
                    self.errorMessage = error.localizedDescription
                    self.showError = true
                }
            }
        }
    }

    private func deleteUser() {
        guard user != nil else { return }
        
        isLoading = true
        UserinfoManager.shared.deleteUserInfo { result in
            DispatchQueue.main.async {
                isLoading = false
                switch result {
                case .success:
                    self.user = nil
                case .failure(let error):
                    if let httpResponse = (error as? URLError)?.userInfo[NSURLErrorFailingURLErrorKey] as? HTTPURLResponse {
                        print("Status code: \(httpResponse.statusCode)")
                        print("Headers: \(httpResponse.allHeaderFields)")
                    }
                    print("Error: \(error.localizedDescription)")
                    self.errorMessage = error.localizedDescription
                    self.showError = true
                }
            }
        }
    }
}

struct MyPageView_Previews: PreviewProvider {
    static var previews: some View {
        MyPageView()
    }
}
