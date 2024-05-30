//
//  ShowingManager.swift
//  sbsapp
//
//  Created by 은옥 on 4/9/24.
//
//let baseURL = "http://52.79.46.118:8080"
//let baseURL = "http://localhost:8080"

import Foundation

class ShowingManager {
    static let shared = ShowingManager()
    private init() {}
    
    let baseURL = "http://52.79.46.118:8080"
    //let baseURL = "http://localhost:8080"

    func fetchRankingBooks(completion: @escaping (Result<[Book], Error>) -> Void) {
        let url = URL(string: "\(baseURL)/api/books/ranking")!
        URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            guard let data = data else {
                completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "No data received"])))
                return
            }
            do {
                let books = try JSONDecoder().decode([Book].self, from: data)
                completion(.success(books))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }

    func fetchBooksByCategory(_ category: String, completion: @escaping (Result<[Book], Error>) -> Void) {
        let url = URL(string: "\(baseURL)/api/\(category)/books")!
        URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            guard let data = data else {
                completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "No data received"])))
                return
            }
            do {
                let books = try JSONDecoder().decode([Book].self, from: data)
                completion(.success(books))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }

    func fetchNewBooks(completion: @escaping (Result<[Book], Error>) -> Void) {
        let url = URL(string: "\(baseURL)/api/books/new")!
        URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            guard let data = data else {
                completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "No data received"])))
                return
            }
            do {
                let books = try JSONDecoder().decode([Book].self, from: data)
                completion(.success(books))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }
}
