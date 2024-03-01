//
//  CartView.swift
//  hello
//
//  Created by 은옥 on 2/6/24.
//

import SwiftUI

struct CartItem: Identifiable {
    var id = UUID()
    var name: String
    var price: Double
    var quantity: Int
}

struct CartView: View {
    @State private var cartItems: [CartItem] = [
        CartItem(name: "iPhone 13", price: 1000000, quantity: 2),
        CartItem(name: "AirPods Pro", price: 250000, quantity: 1),
        CartItem(name: "Apple Watch Series 7", price: 700000, quantity: 1)
    ]
    
    var body: some View {
        NavigationView {
            List {
                ForEach(cartItems) { item in
                    HStack {
                        Text(item.name)
                        Spacer()
                        Text("\(item.price * Double(item.quantity))")
                    }
                }
                .onDelete(perform: deleteCartItem)
            }
            .navigationTitle("장바구니")
            .navigationBarItems(trailing: EditButton())
        }
    }
    
    private func deleteCartItem(at offsets: IndexSet) {
        cartItems.remove(atOffsets: offsets)
    }
}

struct CartView_Previews: PreviewProvider {
    static var previews: some View {
        CartView()
    }
}

