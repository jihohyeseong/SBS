//
//  OrderHistoryView.swift
//  sbsapp
//
//  Created by 은옥 on 5/28/24.
//

import SwiftUI

struct OrderHistoryView: View {
    @State private var purchases: [PurchaseDto] = []
    @State private var isLoading: Bool = false
    @State private var showError: Bool = false
    @State private var errorMessage: String = ""
    @State private var showAddPurchaseView: Bool = false
    
    var body: some View {
        NavigationView {
            VStack {
                if isLoading {
                    ProgressView("로딩 중...")
                        .padding()
                } else {
                    List {
                        ForEach(purchases, id: \.id) { purchase in
                            VStack(alignment: .leading) {
                                Text("책 제목: \(purchase.bookname ?? "제목 없음")")
                                Text("저자: \(purchase.author ?? "저자 없음")")
                                Text("출판사: \(purchase.publisher ?? "출판사 없음")")
                                Text("가격: \(purchase.price)원")
                                Text("수량: \(purchase.quantity)")
                            }
                        }
                        .onDelete(perform: deletePurchase)
                    }
                    .listStyle(PlainListStyle())
                }
            }
            .navigationTitle("구매 내역")
            .onAppear(perform: fetchPurchaseList)
            .alert(isPresented: $showError) {
                Alert(title: Text("오류"), message: Text(errorMessage), dismissButton: .default(Text("확인")))
            }
        }
    }
    
    private func fetchPurchaseList() {
        isLoading = true
        PurchaseManager.shared.fetchPurchaseList { result in
            DispatchQueue.main.async {
                isLoading = false
                switch result {
                case .success(let purchaseDtos):
                    self.purchases = purchaseDtos
                case .failure(let error):
                    self.errorMessage = error.localizedDescription
                    self.showError = true
                }
            }
        }
    }
    
    private func deletePurchase(at offsets: IndexSet) {
        offsets.forEach { index in
            let purchase = purchases[index]
            let purchaseId = purchase.id
            PurchaseManager.shared.deletePurchase(purchaseId: Int64(purchaseId)) { result in
                DispatchQueue.main.async {
                    switch result {
                    case .success:
                        fetchPurchaseList() // Update the list after deletion
                    case .failure(let error):
                        self.errorMessage = error.localizedDescription
                        self.showError = true
                    }
                }
            }
        }
    }
    
    private func addPurchase(bookId: Int, dto: PurchaseDto) {
        PurchaseManager.shared.addPurchase(bookId: Int64(bookId), dto: dto) { result in
            DispatchQueue.main.async {
                switch result {
                case .success:
                    fetchPurchaseList() // Update the list after addition
                case .failure(let error):
                    self.errorMessage = error.localizedDescription
                    self.showError = true
                }
            }
        }
    }
}

struct AddPurchaseView: View {
    @Environment(\.presentationMode) var presentationMode
    @State private var bookId: String = ""
    @State private var userId: String = ""
    @State private var quantity: String = ""
    @State private var bookname: String = ""
    @State private var price: String = ""
    @State private var author: String = ""
    @State private var publisher: String = ""
    @State private var imageUrl: String = ""
    
    var onAdd: (Int, PurchaseDto) -> Void
    
    var body: some View {
        NavigationView {
            Form {
                Section(header: Text("구매 정보")) {
                    TextField("책 ID", text: $bookId)
                        .keyboardType(.numberPad)
                    TextField("사용자 ID", text: $userId)
                        .keyboardType(.numberPad)
                    TextField("수량", text: $quantity)
                        .keyboardType(.numberPad)
                    TextField("책 제목", text: $bookname)
                    TextField("가격", text: $price)
                        .keyboardType(.numberPad)
                    TextField("저자", text: $author)
                    TextField("출판사", text: $publisher)
                    TextField("이미지 URL", text: $imageUrl)
                }
                
                Button(action: {
                    guard let bookId = Int(bookId), let userId = Int(userId), let quantity = Int64(quantity), let price = Int(price) else { return }
                    let newPurchase = PurchaseDto(id: 0, userId: userId, bookId: bookId, quantity: quantity, bookname: bookname, price: price, author: author, publisher: publisher, imageUrl: imageUrl)
                    onAdd(bookId, newPurchase)
                    presentationMode.wrappedValue.dismiss()
                }) {
                    Text("구매 추가")
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
            }
            .navigationTitle("구매 추가")
            .navigationBarItems(leading: Button("취소") {
                presentationMode.wrappedValue.dismiss()
            })
        }
    }
}

struct OrderHistoryView_Previews: PreviewProvider {
    static var previews: some View {
        OrderHistoryView()
    }
}
