//
//  SideDrawerMenu.swift
//  hello
//
//  Created by 은옥 on 2/5/24.
//

import SwiftUI

struct SideDrawerMenu: View {
    @Binding var isOpen: Bool // 사이드 드로어 메뉴 오픈 여부
    
    var body: some View {
        VStack {
            Text("사이드 드로어 메뉴")
                .font(.headline)
                .padding()
            
            // 장바구니 화면으로 이동하는 버튼
            NavigationLink(destination: CartView()) {
                Text("장바구니")
                    .padding()
            }
            
            Spacer()
        }
        .background(Color.gray.opacity(0.2))
        .onTapGesture {
            isOpen = false // 메뉴를 선택하면 사이드 드로어 메뉴를 닫습니다.
        }
    }
}
