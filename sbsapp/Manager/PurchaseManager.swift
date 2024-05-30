//
//  PurchaseManager.swift
//  sbsapp
//
//  Created by 은옥 on 5/28/24.
//

import Foundation

class PurchaseManager {
    static let shared = PurchaseManager()
    
    private let baseURL = "http://52.79.46.118:8080"
    //private let baseURL = "http://localhost:8080"
    
    private init() {}
    
    func fetchPurchaseList(completion: @escaping (Result<[PurchaseDto], Error>) -> Void) {
        guard let url = URL(string: "\(baseURL)/api/purchase") else {
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            
            guard let data = data else {
                completion(.failure(NSError(domain: "No data", code: 0, userInfo: nil)))
                return
            }
            
            do {
                let purchaseDtos = try JSONDecoder().decode([PurchaseDto].self, from: data)
                completion(.success(purchaseDtos))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }
    
    func addPurchase(bookId: Int64, dto: PurchaseDto, completion: @escaping (Result<PurchaseDto, Error>) -> Void) {
        guard let url = URL(string: "\(baseURL)/api/books/\(bookId)/purchase") else {
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        do {
            let jsonData = try JSONEncoder().encode(dto)
            request.httpBody = jsonData
        } catch {
            completion(.failure(error))
            return
        }
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            
            guard let data = data else {
                completion(.failure(NSError(domain: "No data", code: 0, userInfo: nil)))
                return
            }
            
            do {
                let addedPurchase = try JSONDecoder().decode(PurchaseDto.self, from: data)
                completion(.success(addedPurchase))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }
    
    func deletePurchase(purchaseId: Int64, completion: @escaping (Result<PurchaseDto, Error>) -> Void) {
        guard let url = URL(string: "\(baseURL)/api/books/\(purchaseId)/purchase") else {
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "DELETE"
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            
            guard let data = data else {
                completion(.failure(NSError(domain: "No data", code: 0, userInfo: nil)))
                return
            }
            
            do {
                let deletedPurchase = try JSONDecoder().decode(PurchaseDto.self, from: data)
                completion(.success(deletedPurchase))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }
}

