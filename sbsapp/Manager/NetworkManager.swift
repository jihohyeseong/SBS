//
//  NetworkManager.swift
//  sbsapp
//
//  Created by 은옥 on 3/15/24.
//

import Foundation

class NetworkManager {
    
    // 책정보를 가져오는 함수 fetchDataFromServer
    static func fetchDataFromServer(completion: @escaping (Result<[Book], Error>) -> Void) {
        // 맥북의 IP 주소와 포트 번호
        let ipAddress = "http://52.79.46.118" // 맥북과 아이폰이 같은 로컬 호스트에 연결되어 있어야함 (이건 우리집)
        let port = "8080" // Spring Boot 애플리케이션이 사용하는 포트 번호로 변경

        // 요청할 URL 생성
        let bookurlString = "http://\(ipAddress):\(port)/api/books"
        guard let url = URL(string: bookurlString) else {
            print("Invalid URL: \(bookurlString)")
            return
        }

        // HTTP 요청 생성
        var request = URLRequest(url: url)
        request.httpMethod = "GET" // GET 요청을 보냅니다.

        // URLSession을 사용하여 요청
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                print("Error connecting to bookserver: \(error)")
                completion(.failure(error))
                return
            }

            // 서버 연결 성공
            print("Connected to the bookserver")

            // 데이터를 받아오고 Book 모델로 디코딩합니다.
            if let data = data {
                do {
                    let decoder = JSONDecoder()
                    let books = try decoder.decode([Book].self, from: data)
                    completion(.success(books))
                } catch {
                    print("Error decoding JSON: \(error)")
                    completion(.failure(error))
                }
            }
        }
        // 요청 시작
        task.resume()
    }
}
