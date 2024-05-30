//
//  KakaoReadyResponse.swift
//  sbsapp
//
//  Created by 은옥 on 5/28/24.
//

import Foundation

struct PaymentDto: Codable {
    var bookname: String
    var quantity: Int64
    var price: Int64
}

struct KakaoReadyResponse: Codable {
    let tid: String
    let nextRedirectAppUrl: String
    let nextRedirectMobileUrl: String
    let nextRedirectPcUrl: String
    let createdAt: String

    enum CodingKeys: String, CodingKey {
        case tid
        case nextRedirectAppUrl = "next_redirect_app_url"
        case nextRedirectMobileUrl = "next_redirect_mobile_url"
        case nextRedirectPcUrl = "next_redirect_pc_url"
        case createdAt = "created_at"
    }
}
