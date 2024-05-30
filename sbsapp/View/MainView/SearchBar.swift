//
//  SearchBar.swift
//
//
//  Created by 은옥 on 1/11/24.
//

import SwiftUI

struct SearchBar: View {
    @Binding var text: String
    var placeholder: String

    var body: some View {
        HStack {
            TextField(placeholder, text: $text)
                .padding(7)
                .padding(.horizontal, 25)
                .background(Color(.systemGray6))
                .cornerRadius(8)
                .overlay(
                    HStack {
                        Image(systemName: "magnifyingglass")
                            .foregroundColor(.gray)
                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                            .padding(.leading, 8)
                            .padding(.trailing, 5)
                        
                        if !text.isEmpty {
                            Button(action: {
                                self.text = ""
                            }) {
                                Image(systemName: "multiply.circle.fill")
                                    .foregroundColor(.gray)
                                    .padding(.trailing, 8)
                            }
                        }
                    }
                )
                .padding(.horizontal, 10)
                .autocapitalization(.none)
            
            if !text.isEmpty {
                Button(action: {
                    self.text = ""
                }) {
                    Text("취소")
                        .foregroundColor(.blue)
                }
                .padding(.trailing, 10)
                .transition(.move(edge: .trailing))
                .animation(.easeInOut)
            }
        }
    }
}
