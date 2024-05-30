//
//  CommentManager.swift
//  sbsapp
//
//  Created by 은옥 on 4/4/24.
//
// private let baseUrl = "http://52.79.46.118:8080/api"

import Foundation

class CommentManager {
    static let shared = CommentManager()
    
    private let baseUrl = "http://52.79.46.118:8080/api"
    //private let baseUrl = "http://localhost:8080/api"
    
    func fetchComments(bookId: Int, completion: @escaping ([Comment]?) -> Void) {
        guard let url = URL(string: "\(baseUrl)/books/\(bookId)/comments") else {
            completion(nil)
            return
        }
        
        URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error {
                print("Error fetching comments: \(error)")
                completion(nil)
                return
            }
            
            guard let data = data else {
                completion(nil)
                return
            }
            
            do {
                let comments = try JSONDecoder().decode([Comment].self, from: data)
                completion(comments)
            } catch {
                print("Error decoding comments: \(error)")
                completion(nil)
            }
        }.resume()
    }
    
    func addComment(bookId: Int, comment: Comment, completion: @escaping (Result<Comment, Error>) -> Void) {
        guard let url = URL(string: "\(baseUrl)/books/\(bookId)/comments") else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        var commentWithoutId = comment
        commentWithoutId.id = nil // id를 nil로 설정
        
        do {
            let jsonData = try JSONEncoder().encode(commentWithoutId)
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
                completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "No data received"])))
                return
            }
            
            do {
                let addedComment = try JSONDecoder().decode(Comment.self, from: data)
                completion(.success(addedComment))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }
    
    func updateComment(comment: Comment, completion: @escaping (Result<Comment, Error>) -> Void) {
        guard let commentId = comment.id,
              let url = URL(string: "\(baseUrl)/comments/\(commentId)") else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL or missing comment ID"])))
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "PATCH"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        do {
            let jsonData = try JSONEncoder().encode(comment)
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
                completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "No data received"])))
                return
            }
            
            do {
                let updatedComment = try JSONDecoder().decode(Comment.self, from: data)
                completion(.success(updatedComment))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }

    func deleteComment(commentId: Int, completion: @escaping (Result<Void, Error>) -> Void) {
        guard let url = URL(string: "\(baseUrl)/comments/\(commentId)") else {
            completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "DELETE"
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            
            completion(.success(()))
        }.resume()
    }
    
    func summarizeComments(bookId: Int, completion: @escaping (Result<String, Error>) -> Void) {
        fetchComments(bookId: bookId) { comments in
            guard let comments = comments else {
                completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Failed to fetch comments"])))
                return
            }
            
            // Ensure the URL is correct
            guard let url = URL(string: "\(self.baseUrl)/summary") else {
                completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid URL"])))
                return
            }
            
            var request = URLRequest(url: url)
            request.httpMethod = "POST"
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
            
            // Create the request body in the specified format
            let commentsArray = comments.map { comment in
                return [
                    "id": comment.id ?? "",
                    "bookId": comment.bookId,
                    "username": comment.username,
                    "content": comment.content,
                    "bookname": comment.bookname
                ] as [String: Any]
            }
            
            do {
                let jsonData = try JSONSerialization.data(withJSONObject: commentsArray, options: [])
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
                    completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "No data received"])))
                    return
                }
                
                do {
                    if let json = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any],
                       let summaryResult = json["summary"] as? String {
                        completion(.success(summaryResult))
                    } else {
                        if let json = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] {
                            print("Unexpected JSON response: \(json)")
                        }
                        completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid response from server"])))
                    }
                } catch {
                    completion(.failure(error))
                }
            }.resume()
        }
    }
    
    func fetchMyComments(completion: @escaping ([Comment]?) -> Void) {
        guard let url = URL(string: "\(baseUrl)/mypage/comments") else {
            completion(nil)
            return
        }
        
        URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error {
                print("Error fetching my comments: \(error)")
                completion(nil)
                return
            }
            
            guard let data = data else {
                completion(nil)
                return
            }
            
            do {
                let comments = try JSONDecoder().decode([Comment].self, from: data)
                completion(comments)
            } catch {
                print("Error decoding my comments: \(error)")
                completion(nil)
            }
        }.resume()
    }
}
