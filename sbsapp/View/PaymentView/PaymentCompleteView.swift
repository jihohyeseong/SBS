//
//  PaymentCompleteView.swift
//  sbsapp
//
//  Created by 은옥 on 5/30/24.
//

import SwiftUI

struct PaymentCompleteView: View {
    @State private var paymentSuccess: Bool = false
    @State private var errorMessage: String?
    
    var body: some View {
        VStack {
            if paymentSuccess {
                Text("결제가 성공적으로 완료되었습니다!")
                    .font(.largeTitle)
                    .foregroundColor(.green)
                    .padding()
            } else if let errorMessage = errorMessage {
                Text("결제 실패: \(errorMessage)")
                    .font(.headline)
                    .foregroundColor(.red)
                    .padding()
            } else {
                ProgressView("결제 처리 중...")
                    .onAppear {
                        completePayment()
                    }
            }
        }
        .navigationTitle("결제 완료")
    }
    
    private func completePayment() {
        PaymentManager.shared.completePayment { result in
            DispatchQueue.main.async {
                switch result {
                case .success(let success):
                    self.paymentSuccess = success
                case .failure(let error):
                    self.errorMessage = error.localizedDescription
                }
            }
        }
    }
}

struct PaymentCompleteView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentCompleteView()
    }
}

