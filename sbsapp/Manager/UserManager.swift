//
//  UserManager.swift
//  sbsapp
//
//  Created by 은옥 on 3/7/24.
//
//
//let baseURL = "http://52.79.46.118:8080"

import Foundation
import Alamofire

class UserManager {
    static let shared = UserManager()
    var users: [User] = []
    
    struct LoginResponse: Decodable {
        let userId: Int?
    }
    
    let baseURL = "http://52.79.46.118:8080"
    //let baseURL = "http://localhost:8080"
    
    func joinUser(joinDto: JoinDto, completion: @escaping (Result<Void, Error>) -> Void) {
        guard let url = URL(string: "\(baseURL)/api/joinProc") else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        do {
            request.httpBody = try JSONEncoder().encode(joinDto)
        } catch {
            completion(.failure(error))
            return
        }
        
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else {
                completion(.failure(error!))
                return
            }
            guard let httpResponse = response as? HTTPURLResponse else {
                completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid HTTP response"])))
                return
            }
            if (200..<300).contains(httpResponse.statusCode) {
                completion(.success(()))
            } else if let data = data,
                      let responseString = String(data: data, encoding: .utf8) {
                completion(.failure(NSError(domain: "", code: httpResponse.statusCode, userInfo: [NSLocalizedDescriptionKey: responseString])))
            } else {
                completion(.failure(NSError(domain: "", code: httpResponse.statusCode, userInfo: [NSLocalizedDescriptionKey: "Unknown error"])))
            }
        }
        task.resume()
    }

    func loginUser(username: String, password: String, completion: @escaping (Bool, Int?) -> Void) {
        guard let url = URL(string: "\(baseURL)/loginProc") else { return }
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")

        let bodyData = "username=\(username)&password=\(password)"
        request.httpBody = bodyData.data(using: .utf8)

        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                print("Error during login request: \(error)")
                completion(false, nil)
                return
            }
            
            if let httpResponse = response as? HTTPURLResponse {
                print("HTTP Status Code: \(httpResponse.statusCode)")
                if httpResponse.statusCode == 200 {
                    if let headers = httpResponse.allHeaderFields as? [String: String],
                       let cookie = headers["Set-Cookie"] {
                        print("Set-Cookie: \(cookie)")
                        UserDefaults.standard.set(cookie, forKey: "sessionCookie")
                        
                        if let data = data, !data.isEmpty {
                            do {
                                let loginResponse = try JSONDecoder().decode(LoginResponse.self, from: data)
                                print("Response JSON: \(loginResponse)")
                                completion(true, loginResponse.userId)
                            } catch {
                                print("Error decoding response: \(error)")
                                completion(true, nil) // Set-Cookie가 있는 경우 로그인 성공으로 처리
                            }
                        } else {
                            print("Response data is empty, but Set-Cookie is present")
                            completion(true, nil) // 응답 데이터가 없지만 Set-Cookie가 있는 경우 로그인 성공으로 처리
                        }
                    } else {
                        print("No Set-Cookie header found")
                        completion(false, nil)
                    }
                } else {
                    print("Invalid credentials")
                    completion(false, nil)
                }
            } else {
                print("No HTTP response")
                completion(false, nil)
            }
        }
        task.resume()
    }
    
    func saveUserInfo(user: User) {
        do {
            let userData = try JSONEncoder().encode(user)
            UserDefaults.standard.set(userData, forKey: "currentUser")
            UserDefaults.standard.synchronize()
        } catch {
            print("Error saving user info to UserDefaults: \(error.localizedDescription)")
        }
    }

    enum NetworkError: Error {
        case invalidResponse
        case invalidData
    }
}
