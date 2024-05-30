//
//  BookFormView.swift
//  sbsapp
//
//  Created by 은옥 on 3/15/24.
//

import SwiftUI

struct BookFormView: View {
    @State var book: Book // 기존 책 데이터
    var onSubmit: (BookDto) -> Void
    
    // DateFormatter 속성 추가
    let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd" // 날짜 형식 설정
        return formatter
    }()
    
    var body: some View {
        VStack {
            TextField("Enter Book Name", text: $book.bookName)
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            TextField("Enter Author", text: $book.author)
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            TextField("Enter Publisher", text: $book.publisher)
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            TextField("Enter Price", value: $book.price, formatter: NumberFormatter())
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            TextField("Enter Category", text: $book.category)
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            // Date를 String으로 변환하여 표시
            TextField("Enter Release Date", text: Binding(
                get: { book.releasedate != nil ? dateFormatter.string(from: dateFormatter.date(from: book.releasedate!)!) : "" },
                set: { book.releasedate = dateFormatter.string(from: dateFormatter.date(from: $0)!) }
            ))
            .padding()
            .textFieldStyle(RoundedBorderTextFieldStyle())
            
            TextField("Enter Image URL", text: $book.imageUrl)
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            TextField("Enter Units in Stock", value: $book.unitsInStock, formatter: NumberFormatter())
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            Button("Submit") {
                // Book 객체를 BookDto로 변환하여 onSubmit 클로저에 전달
                let bookDto = BookDto(id: book.id, bookname: book.bookName, price: book.price ?? 0, author: book.author, description: book.description, publisher: book.publisher, category: book.category, unitsinstock: book.unitsInStock ?? 0, releasedate: book.releasedate ?? "-", imageurl: book.imageUrl, buyNum: book.buy_num ?? 0, isNew: book.is_new ?? false)
                onSubmit(bookDto)
            }
            .padding()
        }
    }
}

struct BookFormView_Previews: PreviewProvider {
    static var previews: some View {
        let sampleBook = Book(id: 1, bookName: "Sample Book", author: "Sample Author", publisher: "Sample Publisher", price: 0, category: "Sample Category", releasedate: "2022-01-01", imageUrl: "sample_image_url", unitsInStock: 10, description: "Description", buy_num: 0, is_new: false)
        BookFormView(book: sampleBook) { _ in }
    }
}
