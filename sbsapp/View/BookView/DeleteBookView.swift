//
//  DeleteBookView.swift
//  sbsapp
//
//  Created by 은옥 on 3/14/24.
//

import SwiftUI

struct DeleteBookView: View {
    @State private var bookIdToDelete: String = "" // 삭제할 도서의 ID를 입력받는 상태 변수
    @State private var errorMessage: String? // 오류 메시지를 표시하는 상태 변수

    var body: some View {
        VStack {
            // 도서 ID를 입력받는 TextField
            TextField("Enter Book ID", text: $bookIdToDelete)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()

            // 오류 메시지를 표시하는 Text
            if let errorMessage = errorMessage {
                Text(errorMessage)
                    .foregroundColor(.red)
                    .padding()
            }

            // 삭제 버튼
            Button(action: {
                // bookIdToDelete를 Int로 변환하여 deleteBook 메서드에 전달합니다.
                if let bookId = Int(bookIdToDelete) {
                    deleteBooks(id: bookId)
                } else {
                    errorMessage = "Invalid Book ID"
                }
            }) {
                Text("책 삭제")
                    .foregroundColor(.white)
                    .padding()
                    .background(Color.red)
                    .cornerRadius(8)
            }
            .padding()
        }
        .navigationTitle("Delete Book")
    }

    // 도서 삭제 함수
    private func deleteBook(id: Int) {
        // 입력된 도서 ID를 정수로 변환하여 삭제 요청
        guard let bookId = Int(bookIdToDelete) else {
            errorMessage = "Invalid Book ID"
            return
        }

        // BookManager를 사용하여 도서 삭제 요청
        deleteBook(id: bookId)
    }
    
    // 도서 삭제 요청을 처리하는 함수
    private func deleteBooks(id: Int) {
        BookManager.shared.deleteBook(bookId: id)
    }
}

struct DeleteBookView_Previews: PreviewProvider {
    static var previews: some View {
        DeleteBookView()
    }
}
