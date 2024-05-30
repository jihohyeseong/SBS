//
//  UserinfoManager.swift
//  sbsapp
//
//  Created by 은옥 on 5/28/24.
//

import Foundation

class UserinfoManager {
    
    static let shared = UserinfoManager()
    
    private init() {}
    
    let baseURL = "http://52.79.46.118:8080/api/mypage"
    //let baseURL = "http://localhost:8080/api/mypage"
    
    // 현재 로그인된 회원 정보 가져오기
    func getUserInfo(completion: @escaping (Result<User, Error>) -> Void) {
        guard let url = URL(string: baseURL) else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"

        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                if let httpResponse = response as? HTTPURLResponse {
                    print("Status code: \(httpResponse.statusCode)")
                    print("Headers: \(httpResponse.allHeaderFields)")
                }
                print("Error: \(error.localizedDescription)")
                completion(.failure(error))
                return
            }

            guard let data = data else {
                completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "No data received"])))
                return
            }

            do {
                print("Response data: \(String(data: data, encoding: .utf8) ?? "No data")")
                let user = try JSONDecoder().decode(User.self, from: data)
                completion(.success(user))
            } catch {
                print("Decoding error: \(error)")
                completion(.failure(error))
            }
        }
        task.resume()
    }
    
    // 회원 정보 수정
    func updateUserInfo(dto: JoinDto, completion: @escaping (Result<User, Error>) -> Void) {
        guard let url = URL(string: baseURL) else {
            completion(.failure(NSError(domain: "Invalid URL", code: -1, userInfo: nil)))
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "PATCH"
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
                if let httpResponse = response as? HTTPURLResponse {
                    print("Status code: \(httpResponse.statusCode)")
                    print("Headers: \(httpResponse.allHeaderFields)")
                }
                print("Error: \(error.localizedDescription)")
                completion(.failure(error))
                return
            }
            
            guard let data = data else {
                completion(.failure(NSError(domain: "No data", code: -1, userInfo: nil)))
                return
            }
            
            do {
                print("Response data: \(String(data: data, encoding: .utf8) ?? "No data")")
                let updatedUser = try JSONDecoder().decode(User.self, from: data)
                completion(.success(updatedUser))
            } catch {
                print("Decoding error: \(error)")
                completion(.failure(error))
            }
        }.resume()
    }
    
    // 회원 탈퇴
    func deleteUserInfo(completion: @escaping (Result<Void, Error>) -> Void) {
        guard let url = URL(string: baseURL) else {
            completion(.failure(NSError(domain: "Invalid URL", code: -1, userInfo: nil)))
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "DELETE"
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                if let httpResponse = response as? HTTPURLResponse {
                    print("Status code: \(httpResponse.statusCode)")
                    print("Headers: \(httpResponse.allHeaderFields)")
                }
                print("Error: \(error.localizedDescription)")
                completion(.failure(error))
                return
            }
            
            if let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode == 200 {
                completion(.success(()))
            } else {
                let statusCode = (response as? HTTPURLResponse)?.statusCode ?? -1
                print("Delete failed with status code: \(statusCode)")
                completion(.failure(NSError(domain: "Delete failed", code: statusCode, userInfo: nil)))
            }
        }.resume()
    }
}
