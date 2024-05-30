//
//  CartView.swift
//  sbsapp
//
//  Created by 은옥 on 3/7/24.
//

import SwiftUI

struct CartView: View {
    @StateObject private var viewModel = CartViewModel()
    @State private var showingPaymentView = false

    var body: some View {
        NavigationView {
            VStack {
                List {
                    ForEach(viewModel.cartItems) { item in
                        HStack {
                            if let url = URL(string: item.imageUrl ?? "") {
                                AsyncImage(url: url) { image in
                                    image
                                        .resizable()
                                        .frame(width: 50, height: 50)
                                        .aspectRatio(contentMode: .fit)
                                } placeholder: {
                                    ProgressView()
                                        .frame(width: 50, height: 50)
                                }
                            } else {
                                Image(systemName: "book")
                                    .resizable()
                                    .frame(width: 50, height: 50)
                                    .aspectRatio(contentMode: .fit)
                            }
                            VStack(alignment: .leading) {
                                Text(item.bookname ?? "Unknown Book")
                                Text("작가: \(item.author ?? "Unknown Author")")
                                Text("출판사: \(item.publisher ?? "Unknown Publisher")")
                            }
                            Spacer()
                            Text("수량: \(item.quantity)")
                            Text("총액: \(formatPrice(price: Int(item.price!) * Int(item.quantity)))")
                            Button(action: {
                                if let id = item.id {
                                    print("Deleting item with id: \(id)")
                                    viewModel.deleteCartItem(cartId: Int(id))
                                } else {
                                    print("Item id is nil")
                                }
                            }) {
                                Image(systemName: "trash")
                                    .foregroundColor(.red)
                            }
                        }
                    }
                    .onDelete(perform: deleteItem)
                }
                .navigationTitle("Cart")
                .navigationBarTitleDisplayMode(.inline)
                .navigationBarItems(trailing: Button("Delete All") {
                    viewModel.deleteAllCartItems()
                })
                .onAppear {
                    viewModel.getCartList()
                }

                Button(action: {
                    showingPaymentView.toggle()
                }) {
                    Text("결제하기")
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.green)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
                .sheet(isPresented: $showingPaymentView) {
                    if let paymentDto = createPaymentDto() {
                        PaymentViewControllerRepresentable(paymentDto: paymentDto)
                    } else {
                        Text("결제 정보를 생성할 수 없습니다.")
                    }
                }
                .padding()
            }
        }
    }

    func deleteItem(at offsets: IndexSet) {
        for index in offsets {
            let item = viewModel.cartItems[index]
            if let id = item.id {
                print("Deleting item with id from onDelete: \(id)")
                viewModel.deleteCartItem(cartId: Int(id))
            }
        }
    }

    func formatPrice(price: Int) -> String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.currencyCode = "KRW"
        
        return formatter.string(from: NSNumber(value: price)) ?? "Invalid Price"
    }

    func createPaymentDto() -> PaymentDto? {
        guard let firstItem = viewModel.cartItems.first else {
            return nil
        }
        return PaymentDto(
            bookname: firstItem.bookname ?? "Unknown Book",
            quantity: firstItem.quantity,
            price: firstItem.price! * firstItem.quantity
        )
    }
}

class CartViewModel: ObservableObject {
    @Published var cartItems: [CartManager.CartDto] = []
    private let cartManager = CartManager.shared

    func getCartList() {
        cartManager.getCartList { result in
            DispatchQueue.main.async {
                switch result {
                case .success(let items):
                    self.cartItems = items
                case .failure(let error):
                    print(error)
                }
            }
        }
    }

    func deleteCartItem(cartId: Int) {
        cartManager.deleteCartItem(cartId: cartId) { result in
            DispatchQueue.main.async {
                switch result {
                case .success:
                    print("Successfully deleted item with id: \(cartId)")
                    self.cartItems.removeAll { $0.id == Int64(cartId) } // UI 갱신
                case .failure(let error):
                    print(error)
                }
            }
        }
    }

    func deleteAllCartItems() {
        cartManager.deleteAllCartItems { result in
            DispatchQueue.main.async {
                switch result {
                case .success:
                    self.cartItems = [] // 카트 아이템을 비움
                case .failure(let error):
                    print(error)
                }
            }
        }
    }
}
