//
//  PaymentManager.swift
//  sbsapp
//
//  Created by 은옥 on 5/28/24.
//
//private let baseURL = "http://52.79.46.118:8080"

import Foundation

class PaymentManager {
    static let shared = PaymentManager()
    
    private let baseURL = "http://52.79.46.118:8080"

    private init() {}

    func preparePayment(paymentDto: PaymentDto, completion: @escaping (Result<KakaoReadyResponse, Error>) -> Void) {
        guard let url = URL(string: "\(baseURL)/payment/ready") else {
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")

        do {
            let jsonData = try JSONEncoder().encode([paymentDto])
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

            if let json = try? JSONSerialization.jsonObject(with: data, options: []) {
                print("Server response: \(json)")
            }

            do {
                let kakaoResponse = try JSONDecoder().decode(KakaoReadyResponse.self, from: data)
                completion(.success(kakaoResponse))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }
    
    func completePayment(completion: @escaping (Result<Bool, Error>) -> Void) {
        guard let url = URL(string: "\(baseURL)/payment/success") else {
            completion(.failure(NSError(domain: "Invalid URL", code: 0, userInfo: nil)))
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
                if let json = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any],
                   let success = json["success"] as? Bool {
                    completion(.success(success))
                } else {
                    completion(.failure(NSError(domain: "Invalid response", code: 0, userInfo: nil)))
                }
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }
}
