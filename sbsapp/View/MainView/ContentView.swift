//
//  ContentView.swift
//  sbsapp
//
//  Created by 은옥 on 3/7/24.
//
//let categories = ["가정_육아", "건강", "경제_경영", "소설", "시_에세이", "어린이(초등)", "역사_문화", "외국어", "유아(0~7세)", "인문", "자기계발", "정치_사회"] // 카테고리 목록

import SwiftUI

struct ContentView: View {
    @State private var searchText: String = ""
    @State private var isLoginSheetPresented: Bool = false
    @State private var filteredBooks: [Book] = []
    @State private var isDrawerOpen: Bool = false
    @State private var searchHistory: [String] = []
    @State private var books: [Book] = []
    @State private var isLoggedIn: Bool = false
    @State private var currentUser: String = "exampleUser" // 현재 로그인된 사용자 이름
    @State private var selectedCategory: String = ""

    let categories = ["가정_육아", "건강", "경제_경영", "소설", "시_에세이", "어린이(초등)", "역사_문화", "외국어", "유아(0~7세)", "인문", "자기계발", "정치_사회"]
    // 카테고리 목록

    var body: some View {
        NavigationView {
            VStack {
                SearchBar(text: $searchText, placeholder: "책 이름으로 검색")
                    .onChange(of: searchText) { _ in
                        updateSearchHistory()
                        filterBooks()
                    }

                CategorySelectorView(selectedCategory: $selectedCategory, categories: categories, onCategorySelected: { category in
                    fetchBooksByCategory(category)
                })

                if filteredBooks.isEmpty {
                    Text("랭킹 책 목록")
                        .font(.headline)
                        .padding()
                }

                List(filteredBooks) { book in
                    NavigationLink(destination: ProductDetailView(book: book, bookId: book.id, currentUser: currentUser)) {
                        ProductRowView(product: book)
                    }
                }

                HStack {
                    Button(action: fetchRankingBooks) {
                        Text("랭킹")
                    }
                    .padding()
                    
                    Button(action: fetchNewBooks) {
                        Text("신간")
                    }
                    .padding()
                }
            }
            .navigationTitle("SBS BOOKMARKET")
            .navigationBarItems(leading: (
                Button(action: {
                    isDrawerOpen.toggle()
                }) {
                    Image(systemName: "line.horizontal.3")
                        .imageScale(.large)
                }
            ), trailing: (
                Group {
                    if isLoggedIn {
                        NavigationLink(destination: MyPageView()) {
                            Image(systemName: "person.crop.circle.fill")
                                .imageScale(.large)
                        }
                    } else {
                        Button(action: {
                            isLoginSheetPresented = true
                        }) {
                            Image(systemName: "person.crop.circle")
                                .imageScale(.large)
                        }
                        .sheet(isPresented: $isLoginSheetPresented) {
                            LoginView(isLoggedIn: $isLoggedIn)
                        }
                    }
                }
            ))
            .overlay(
                DrawerMenu(isOpen: $isDrawerOpen, isLoggedIn: $isLoggedIn, onToggle: {
                    isDrawerOpen.toggle()
                }),
                alignment: .leading
            )
            .onAppear {
                fetchRankingBooks()
            }
        }
    }

    private func refreshBookList() {
        fetchRankingBooks()
    }

    private func fetchRankingBooks() {
        ShowingManager.shared.fetchRankingBooks { result in
            switch result {
            case .success(let fetchedBooks):
                books = fetchedBooks
                filterBooks()
            case .failure(let error):
                print("Error fetching ranking books: \(error)")
            }
        }
    }

    private func fetchNewBooks() {
        ShowingManager.shared.fetchNewBooks { result in
            switch result {
            case .success(let fetchedBooks):
                books = fetchedBooks
                filterBooks()
            case .failure(let error):
                print("Error fetching new books: \(error)")
            }
        }
    }

    private func fetchBooksByCategory(_ category: String) {
        ShowingManager.shared.fetchBooksByCategory(category) { result in
            switch result {
            case .success(let fetchedBooks):
                books = fetchedBooks
                filterBooks()
            case .failure(let error):
                print("Error fetching books by category: \(error)")
            }
        }
    }

    private func filterBooks() {
        if searchText.isEmpty {
            filteredBooks = books
        } else {
            filteredBooks = books.filter { $0.bookName.localizedCaseInsensitiveContains(searchText) }
        }
    }

    private func updateSearchHistory() {
        searchHistory = SearchHistoryManager.shared.getSearchHistory()
    }
}

struct ProductRowView: View {
    var product: Book
    
    var body: some View {
        HStack {
            if let url = URL(string: product.imageUrl) {
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
                Text(product.bookName)
                if let price = product.price {
                    Text("\(formatPrice(price: price))")
                } else {
                    Text("Not available")
                }
            }
            Spacer()
        }
    }
}

func formatPrice(price: Int) -> String {
    let formatter = NumberFormatter()
    formatter.numberStyle = .currency
    formatter.currencyCode = "KRW"
    
    if let formattedPrice = formatter.string(from: NSNumber(value: price)) {
        return formattedPrice
    } else {
        return "Invalid Price"
    }
}


// 좌측 사이드 서랍 메뉴
struct DrawerMenu: View {
    @Binding var isOpen: Bool
    @Binding var isLoggedIn: Bool
    var onToggle: () -> Void

    @State private var isLoginActive: Bool = false
    @State private var isJoinActive: Bool = false
    @State private var isCartActive: Bool = false

    var body: some View {
        VStack(alignment: .leading) {
            if isLoggedIn {
                NavigationLink(destination: MyPageView(), isActive: $isLoginActive) {
                    Text("마이페이지")
                        .padding()
                }
            } else {
                NavigationLink(destination: LoginView(isLoggedIn: $isLoggedIn), isActive: $isLoginActive) {
                    Text("로그인")
                        .padding()
                }
            }
            
            NavigationLink(destination: JoinView(), isActive: $isJoinActive) {
                Text("회원가입")
                    .padding()
            }
            
            NavigationLink(destination: CartView(), isActive: $isCartActive) {
                Text("장바구니")
                    .padding()
            }

            Spacer()
        }
        .frame(width: 200)
        .background(Color.gray.opacity(0.2))
        .offset(x: isOpen ? 0 : -200)
        .animation(.easeInOut)
        .onTapGesture {
            onToggle()
        }
    }
}
