//
//  UpdateBookView.swift
//  sbsapp
//
//  Created by 은옥 on 3/15/24.
//

import SwiftUI

struct UpdateBookView: View {
    @State private var bookID: String = ""
    @State private var updatedBookDto: BookDto?
    @State private var showAlert: Bool = false
    @State private var alertMessage: String = ""
    
    var body: some View {
        VStack {
            TextField("Enter Book ID", text: $bookID)
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            Button(action: fetchBookDetails) {
                Text("Fetch Book")
            }
            .padding()
            
            if let bookDto = updatedBookDto {
                let book = Book(
                    id: bookDto.id,
                    bookName: bookDto.bookname,
                    author: bookDto.author,
                    publisher: bookDto.publisher,
                    price: bookDto.price,
                    category: bookDto.category,
                    releasedate: bookDto.releasedate,
                    imageUrl: bookDto.imageurl,
                    unitsInStock: bookDto.unitsinstock,
                    description: bookDto.description,
                    buy_num: bookDto.buyNum,
                    is_new: bookDto.isNew
                )
                
                BookFormView(book: book, onSubmit: updateBook)
            }
        }
        .padding()
        .alert(isPresented: $showAlert) {
            Alert(title: Text("Error"), message: Text(alertMessage), dismissButton: .default(Text("OK")))
        }
    }
    
    func fetchBookDetails() {
        guard let bookID = Int(bookID) else {
            alertMessage = "Please enter a valid book ID"
            showAlert = true
            return
        }
        
        BookManager.shared.fetchBookDetails(bookId: bookID) { result in
            switch result {
            case .success(let bookDto):
                self.updatedBookDto = bookDto
            case .failure(let error):
                self.alertMessage = "Failed to fetch book details: \(error.localizedDescription)"
                self.showAlert = true
            }
        }
    }
    
    func updateBook(bookDto: BookDto) {
        guard let bookID = Int(bookID) else {
            alertMessage = "Please enter a valid book ID"
            showAlert = true
            return
        }
        BookManager.shared.updateBook(Id: bookID, updatedBook: bookDto)
    }
}

struct UpdateBookView_Previews: PreviewProvider {
    static var previews: some View {
        UpdateBookView()
    }
}

