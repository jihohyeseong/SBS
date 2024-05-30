//
//  PaymentViewControllerRepresentable.swift
//  sbsapp
//
//  Created by 은옥 on 5/28/24.
//

import SwiftUI
import WebKit

struct PaymentViewControllerRepresentable: UIViewControllerRepresentable {
    var paymentDto: PaymentDto

    func makeUIViewController(context: Context) -> PaymentViewController {
        let viewController = PaymentViewController()
        viewController.paymentDto = paymentDto
        return viewController
    }

    func updateUIViewController(_ uiViewController: PaymentViewController, context: Context) {
        // 업데이트 로직이 필요한 경우 여기에 작성
    }
}

