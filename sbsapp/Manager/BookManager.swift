//
//  BookManager.swift
//  sbsapp
//
//  Created by 은옥 on 3/14/24.
//
import Foundation
import Alamofire

class BookManager {
    static let shared = BookManager()
    
    var books: [Book] = []
    var filteredBooks: [Book] = []
    
    let baseURL = "http://52.79.46.118:8080"
    //let baseURL = "http://localhost:8080"
    
    // 서버에 저장되어 있는 책리스트를 받아오는 함수 fetchBookList
    func fetchBookList(completion: @escaping (Result<[Book], Error>) -> Void) {
        let booksUrl = "\(baseURL)/api/books"
        
        AF.request(booksUrl, method: .get).responseJSON { response in
            switch response.result {
            case .success(let value):
                do {
                    let jsonData = try JSONSerialization.data(withJSONObject: value)
                    let fetchedBooks = try JSONDecoder().decode([Book].self, from: jsonData)
                    
                    self.books = fetchedBooks
                    
                    completion(.success(fetchedBooks))
                } catch {
                    completion(.failure(error))
                }
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }

    func addNewBook(book: BookDto) {
        let booksUrl = "\(baseURL)/api/books"
        
        AF.request(booksUrl, method: .post, parameters: book, encoder: JSONParameterEncoder.default).responseJSON { response in
            switch response.result {
            case .success(let value):
                print("New book added successfully: \(value)")
            case .failure(let error):
                print("Error adding new book: \(error)")
            }
        }
    }

    func updateBook(Id: Int, updatedBook: BookDto) {
        let bookUpdateUrl = "\(baseURL)/api/books/\(Id)"
        
        AF.request(bookUpdateUrl, method: .patch, parameters: updatedBook, encoder: JSONParameterEncoder.default).responseJSON { response in
            switch response.result {
            case .success(let value):
                print("Book updated successfully: \(value)")
                if let jsonData = try? JSONSerialization.data(withJSONObject: value) {
                    let decoder = JSONDecoder()
                    decoder.keyDecodingStrategy = .convertFromSnakeCase
                    if let updatedBook = try? decoder.decode(Book.self, from: jsonData) {
                        if let index = self.books.firstIndex(where: { $0.id == updatedBook.id }) {
                            self.books[index] = updatedBook
                            if let filteredIndex = self.filteredBooks.firstIndex(where: { $0.id == updatedBook.id }) {
                                self.filteredBooks[filteredIndex] = updatedBook
                            }
                        }
                    }
                }
            case .failure(let error):
                print("Error updating book: \(error)")
            }
        }
    }
    
    func deleteBook(bookId: Int) {
        let bookDeleteUrl = "\(baseURL)/api/books/\(bookId)"
        
        AF.request(bookDeleteUrl, method: .delete).response { response in
            switch response.result {
            case .success:
                print("Book deleted successfully")
            case .failure(let error):
                print("Error deleting book: \(error)")
            }
        }
    }

    func fetchBookDetails(bookId: Int, completion: @escaping (Result<BookDto, Error>) -> Void) {
        let urlString = "\(baseURL)/api/books/\(bookId)/detail" // 경로 수정 확인 필요
        
        guard let url = URL(string: urlString) else {
            print("Invalid URL: \(urlString)")
            completion(.failure(NSError(domain: "Invalid URL", code: 0, userInfo: nil)))
            return
        }
        
        URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            
            guard let httpResponse = response as? HTTPURLResponse else {
                completion(.failure(NSError(domain: "Invalid response", code: 0, userInfo: nil)))
                return
            }
            
            guard httpResponse.statusCode == 200 else {
                print("Invalid response status code: \(httpResponse.statusCode)")
                completion(.failure(NSError(domain: "Invalid response", code: httpResponse.statusCode, userInfo: nil)))
                return
            }
            
            guard let data = data else {
                completion(.failure(NSError(domain: "No data received", code: 0, userInfo: nil)))
                return
            }
            
            do {
                let decoder = JSONDecoder()
                decoder.keyDecodingStrategy = .convertFromSnakeCase
                let bookDto = try decoder.decode(BookDto.self, from: data)
                completion(.success(bookDto))
            } catch let decodingError {
                print("Decoding error: \(decodingError)")
                completion(.failure(decodingError))
            }
        }.resume()
    }
}
