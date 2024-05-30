//
//  CartManager.swift
//  sbsapp
//
//  Created by 은옥 on 4/9/24.
//
//let baseURL = "http://52.79.46.118:8080"

import Foundation
import Alamofire

class CartManager {
    static let shared = CartManager()
    
    let baseURL = "http://52.79.46.118:8080"
    //let baseURL = "http://localhost:8080"
    
    struct CartDto: Codable, Identifiable {
        var id: Int64?
        var userId: Int64?
        var bookId: Int64?
        var quantity: Int64
        var bookname: String?
        var price: Int64?
        var author: String?
        var publisher: String?
        var imageUrl: String?
    }

    func getCartList(completion: @escaping (Result<[CartDto], Error>) -> Void) {
        guard let url = URL(string: "\(baseURL)/api/cart") else { return }
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        
        guard let cookie = UserDefaults.standard.string(forKey: "sessionCookie") else {
            print("No session cookie found")
            completion(.failure(NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey: "No session cookie found"])))
            return
        }
        request.setValue(cookie, forHTTPHeaderField: "Cookie")

        AF.request(request).responseDecodable(of: [CartDto].self) { response in
            switch response.result {
            case .success(let cartItems):
                completion(.success(cartItems))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    func addCart(bookId: Int, cartDto: CartDto, completion: @escaping (Result<CartDto, Error>) -> Void) {
        guard let url = URL(string: "\(baseURL)/api/books/\(bookId)/cart") else { return }
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        do {
            let jsonData = try JSONEncoder().encode(cartDto)
            request.httpBody = jsonData
        } catch {
            print("Error encoding CartDto: \(error)")
            completion(.failure(error))
            return
        }
        
        guard let cookie = UserDefaults.standard.string(forKey: "sessionCookie") else {
            print("No session cookie found")
            completion(.failure(NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey: "No session cookie found"])))
            return
        }
        request.setValue(cookie, forHTTPHeaderField: "Cookie")

        AF.request(request).responseDecodable(of: CartDto.self) { response in
            switch response.result {
            case .success(let cartResponse):
                completion(.success(cartResponse))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    func deleteCartItem(cartId: Int, completion: @escaping (Result<Void, Error>) -> Void) {
        guard let url = URL(string: "\(baseURL)/api/books/\(cartId)/cart") else { return }
        var request = URLRequest(url: url)
        request.httpMethod = "DELETE"
        
        guard let cookie = UserDefaults.standard.string(forKey: "sessionCookie") else {
            print("No session cookie found")
            completion(.failure(NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey: "No session cookie found"])))
            return
        }
        request.setValue(cookie, forHTTPHeaderField: "Cookie")

        AF.request(request).response { response in
            if let error = response.error {
                completion(.failure(error))
            } else {
                completion(.success(()))
            }
        }
    }
    
    func deleteAllCartItems(completion: @escaping (Result<Void, Error>) -> Void) {
        guard let url = URL(string: "\(baseURL)/api/books/all/cart") else { return }
        var request = URLRequest(url: url)
        request.httpMethod = "DELETE"
        
        guard let cookie = UserDefaults.standard.string(forKey: "sessionCookie") else {
            print("No session cookie found")
            completion(.failure(NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey: "No session cookie found"])))
            return
        }
        request.setValue(cookie, forHTTPHeaderField: "Cookie")

        AF.request(request).response { response in
            if let error = response.error {
                completion(.failure(error))
            } else {
                completion(.success(()))
            }
        }
    }
}
