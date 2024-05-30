//
//  PaymentViewController.swift
//  sbsapp
//
//  Created by 은옥 on 5/28/24.
//

import UIKit
import WebKit

class PaymentViewController: UIViewController {

    var paymentDto: PaymentDto?

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .white

        if let paymentDto = paymentDto {
            PaymentManager.shared.preparePayment(paymentDto: paymentDto) { result in
                switch result {
                case .success(let kakaoResponse):
                    DispatchQueue.main.async {
                        self.openKakaoPayWebPage(urlString: kakaoResponse.nextRedirectMobileUrl)
                    }
                case .failure(let error):
                    print("Error preparing payment: \(error)")
                }
            }
        }
    }

    private func openKakaoPayWebPage(urlString: String) {
        guard let url = URL(string: urlString) else { return }

        let webViewConfiguration = WKWebViewConfiguration()
        webViewConfiguration.preferences.javaScriptEnabled = true

        let webView = WKWebView(frame: self.view.bounds, configuration: webViewConfiguration)
        webView.navigationDelegate = self
        webView.load(URLRequest(url: url))
        self.view.addSubview(webView)
    }
}

extension PaymentViewController: WKNavigationDelegate {
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        print("WebView did finish loading")
    }
}

