//
//  AddBookView.swift
//  sbsapp
//
//  Created by 은옥 on 3/14/24.
//

import SwiftUI

struct AddBookView: View {
    @State private var id: Int = 0
    @State private var bookName: String = ""
    @State private var author: String = ""
    @State private var publisher: String = ""
    @State private var price: Int = 0
    @State private var category: String = ""
    @State private var releasedate: String = ""
    @State private var imageurl: String = ""
    @State private var unitsinstock: Int = 0
    @State private var description: String = ""
    @State private var buy_num = 0
    @State private var is_new = false

    var body: some View {
        VStack {
            TextField("ID", value: $id, formatter: NumberFormatter())
                .padding()
            TextField("Book Name", text: $bookName)
                .padding()
            TextField("Author", text: $author)
                .padding()
            TextField("Publisher", text: $publisher)
                .padding()
            TextField("Price", value: $price, formatter: NumberFormatter())
                .padding()
            TextField("Category", text: $category)
                .padding()
            TextField("Release Date", text: $releasedate)
                .padding()
            TextField("Image URL", text: $imageurl)
                .padding()
            TextField("Units in Stock", value: $unitsinstock, formatter: NumberFormatter())
                .padding()
            TextField("Description", value: $description, formatter: NumberFormatter())
                .padding()

            Button(action: addBook) {
                Text("책 추가")
            }
            .padding()
        }
        .padding()
    }

    func addBook() {
        // 이 함수에서는 입력받은 정보를 사용하여 새 책을 추가하는 동작을 수행합니다.
        // 예를 들어 BookDto를 만들고 BookManager를 사용하여 addBook 메서드를 호출할 수 있습니다.
        let newBookDto = BookDto(id: id, bookname: bookName, price: price, author: author, description: description, publisher: publisher, category: category, unitsinstock: unitsinstock, releasedate: releasedate, imageurl: imageurl, buyNum: Int64(buy_num), isNew: is_new)

        BookManager.shared.addNewBook(book: newBookDto)
    }
}

struct AddBookView_Previews: PreviewProvider {
    static var previews: some View {
        AddBookView()
    }
}
