//
//  ViewModel.swift
//  sbsapp
//
//  Created by 은옥 on 5/03/24.
//

import Foundation
import Alamofire

class PurchaseViewModel: ObservableObject {
    let baseURL = "http://13.125.242.183:8080"
    
    @Published var calculateDtos: [CalculateDto] = []
    @Published var total: Int = 0
    @Published var errorMessage: String?
    @Published var isLoading: Bool = false
    
    func calculateAdmin() {
        isLoading = true
        AF.request("\(baseURL)/admin/calculate").responseDecodable(of: [CalculateDto].self) { response in
            switch response.result {
            case .success(let dtos):
                DispatchQueue.main.async {
                    self.isLoading = false
                    self.calculateDtos = dtos
                }
            case .failure(let error):
                DispatchQueue.main.async {
                    self.isLoading = false
                    self.errorMessage = error.localizedDescription
                }
            }
        }
    }
    
    func calculateTotal() {
        isLoading = true
        AF.request("\(baseURL)/admin/total").responseDecodable(of: Int.self) { response in
            switch response.result {
            case .success(let total):
                DispatchQueue.main.async {
                    self.isLoading = false
                    self.total = total
                }
            case .failure(let error):
                DispatchQueue.main.async {
                    self.isLoading = false
                    self.errorMessage = error.localizedDescription
                }
            }
        }
    }
}

