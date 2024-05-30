//
//  CategorySelectorView.swift
//  sbsapp
//
//  Created by 은옥 on 5/30/24.
//

import SwiftUI

struct CategorySelectorView: View {
    @Binding var selectedCategory: String
    let categories: [String]
    let onCategorySelected: (String) -> Void

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack {
                ForEach(categories, id: \.self) { category in
                    Button(action: {
                        selectedCategory = category
                        onCategorySelected(category)
                    }) {
                        Text(category)
                            .padding(8)
                            .background(selectedCategory == category ? Color.blue : Color.gray.opacity(0.2))
                            .foregroundColor(selectedCategory == category ? .white : .black)
                            .cornerRadius(8)
                            .font(.system(size: 14)) // 카테고리 크기 줄이기
                    }
                }
            }
            .padding(.horizontal)
        }
    }
}
