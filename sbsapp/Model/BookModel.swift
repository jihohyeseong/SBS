//
//  BookModel.swift
//  sbsapp
//
//  Created by 은옥 on 3/12/24.
//

struct Book: Codable, Identifiable {
    var id: Int
    var bookName: String
    var author: String
    var publisher: String
    var price: Int?
    var category: String
    var releasedate: String? // releasedate 속성을 옵셔널로 변경
    var imageUrl: String
    var unitsInStock: Int?
    var description: String
    var buy_num: Int64?
    var is_new: Bool?
    
    enum CodingKeys: String, CodingKey {
        case id
        case bookName = "bookname"
        case author
        case publisher
        case price
        case category
        case releasedate
        case imageUrl = "imageurl"
        case unitsInStock = "unitsInStock"
        case description
        case buy_num
        case is_new
    }
    
    init(id: Int, bookName: String, author: String, publisher: String, price: Int, category: String, releasedate: String? = nil, imageUrl: String, unitsInStock: Int, description: String, buy_num: Int64, is_new: Bool) {
        self.id = id
        self.bookName = bookName
        self.author = author
        self.publisher = publisher
        self.price = price
        self.category = category
        self.releasedate = releasedate
        self.imageUrl = imageUrl
        self.unitsInStock = unitsInStock
        self.description = description
        self.buy_num = buy_num
        self.is_new = is_new
    }
}

// 책의 정보를 수정할 때 전송되는 BookDto 구조체
struct BookDto: Codable {
    var id: Int            // 도서 id(primary key)
    var bookname: String    // 도서 이름(bookName)
    var price: Int      // 도서 가격
    var author: String      // 도서 작가,지은이
    var description: String // 도서 상세 내용
    var publisher: String   // 도서 출판사
    var category: String    // 도서 분류
    var unitsinstock: Int   // 도서 재고 수량
    var releasedate: String // 도서 출간 날짜(년/월)
    var imageurl: String    // 도서 사진(url)
    var buyNum: Int64        // 누적구매량
    var isNew: Bool         // 신간도서 구분
}

struct BookListResponse: Codable {
    var books: [BookDto]
}
