//
//  ProfileView.swift
//  sbsapp
//
//  Created by 은옥 on 3/21/24.
//

import SwiftUI

struct ProfileView: View {
    let user: User // 사용자 정보
    
    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            Text("UserId: \(maskedId())") // id *로 표시하기
            Text("Username: \(user.username)") // 사용자 이름
            
            Text("Address: \(user.address)") // 주소
            Text("Email: \(user.email)") // 이메일
            Text("Phone Number: \(user.phoneNum)") // 전화번호
            Text("Role: \(user.role)") // 역할
        }
        .padding()
        .navigationBarTitle("Profile") // 네비게이션 바 제목 설정
    }
    
    // id를 *로 가리는 함수
    private func maskedId() -> String {
        let idCount = String(user.id).count // 사용자 ID를 문자열로 변환한 후 길이를 계산
        let masked = String(repeating: "*", count: idCount)
        return masked
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        // 미리보기용 사용자 정보 생성
        let user = User(id: 1, address: "123 Main St", email: "example_user", password: "user@example.com", phoneNum: "password", role: "123-456-7890", username: "User")
        
        // 미리보기
        ProfileView(user: user)
    }
}

