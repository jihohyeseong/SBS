import SwiftUI
import Foundation

struct ContentView: View {
    @State private var searchText: String = ""
    @State private var isLoginSheetPresented: Bool = false // 로그인 화면 표시 여부
    @State private var filteredBooks: [Book] = []
    @State private var isDrawerOpen: Bool = false // 사이드 드로어 메뉴 오픈 여부

    var body: some View {
        NavigationView {
            VStack {
                SearchBar(text: $searchText, placeholder: "책 이름으로 검색")

                List(filteredBooks.isEmpty ? books : filteredBooks) { book in
                                    NavigationLink(destination: ProductDetailView(book: book)) {
                                        ProductRowView(product: book)
                                    }
                                }
                                .overlay(Scrollbar(), alignment: .trailing) // 스크롤바 추가
                            }
                            .navigationTitle("책 쇼핑몰")
                            .navigationBarItems(leading: (
                                // 좌측 사이드 서랍 메뉴 버튼
                                Button(action: {
                                    // 좌측 사이드 서랍 메뉴를 토글합니다.
                                    isDrawerOpen.toggle()
                                }) {
                                    Image(systemName: "line.horizontal.3")
                                        .imageScale(.large)
                                }
                            ), trailing: (
                                // 우측 상단 로그인 버튼
                                Button(action: {
                                    isLoginSheetPresented = true
                                }) {
                                    Image(systemName: "person.crop.circle")
                                        .imageScale(.large)
                                }
                            ))
                            .listStyle(SidebarListStyle()) // 좌측 사이드 서랍 메뉴 스타일
                            .offset(x: isDrawerOpen ? 0 : -200) // 사이드 드로어 메뉴가 열리면 콘텐츠를 이동시킵니다.
                            .overlay(
                                // 사이드 드로어 메뉴
                                SideDrawerMenu(isOpen: $isDrawerOpen)
                                    .frame(width: 200)
                                    .edgesIgnoringSafeArea(.vertical)
                                    .offset(x: isDrawerOpen ? 0 : -200) // 사이드 드로어 메뉴가 열리면 오프셋을 0으로 조정하여 나타냅니다.
                            )
                        }
                        .sheet(isPresented: $isLoginSheetPresented) {
                            LoginView()
                        }
                        .onAppear {
                            // 최초에는 전체 제품을 보여줍니다.
                            filteredBooks = books
                        }
                        .onChange(of: searchText, initial : true) { initial, newValue in
                            // 검색어가 변경될 때마다 제품 목록을 필터링합니다.
                            filteredBooks = books.filter { newValue.isEmpty ? true : $0.title.localizedCaseInsensitiveContains(newValue) }
                        }
                    }
                }

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

struct ProductRowView: View {
    var product: Book
    
    var body: some View {
        HStack {
            Image(product.imageName)
                .resizable()
                .frame(width: 50, height: 50)
            Text(product.title)
            Spacer()
            Text(formatPrice(price: product.price))
        }
    }
}

struct Book: Identifiable {
    var id = UUID() // 책 아이디
    var title: String // 책 이름
    var price: Double // 책 가격
    var imageName: String // 책 이미지
    var description: String // 책 설명
    var author: String // 작가
    var publisher: String // 출판사
    var category: String // 카테고리
    var unitsInStock: Int //
    var releaseDate: String // 출판날짜
    var condition: String // 중고, 신품?
}

func formatPrice(price: Double) -> String {
    let formatter = NumberFormatter()
    formatter.numberStyle = .currency
    formatter.currencyCode = "KRW" // 대한민국 원화 코드
    
    if let formattedPrice = formatter.string(from: NSNumber(value: price)) {
        return formattedPrice
    } else {
        return "Invalid Price"
    }
}

//스크롤바를 추가하기 (contentview나 bookdetailview에 추가?)
struct Scrollbar: View {
    var body: some View {
        VStack {
            Spacer()
            VStack(spacing: 2) {
                ForEach(0..<30) { _ in
                    Rectangle()
                        .fill(Color.accentColor)
                        .frame(width: 4, height: 10)
                        .cornerRadius(2)
                }
            }
            .frame(width: 4)
            .opacity(0.8)
            .background(Color.secondary)
            .cornerRadius(2)
            .padding(.trailing, 3)
        }
    }
}

let books: [Book] = [
    Book(title: "C#프로그래밍", price: 27000, imageName: "ISBN1234", description: "C#을 처음 접하는 독자를 대상으로 일대일 수업처럼 자세히 설명한 책이다. 꼭 알아야 할 핵심 개념은 기본 예제로 최대한 쉽게 설명했으며, 중요한 내용은 응용 예제, 퀴즈, 셀프 스터디, 예제 모음으로 한번 더 복습할 수 있다.", author: "우재남", publisher: "한빛아카데미", category: "IT모바일", unitsInStock: 1000, releaseDate: "2022/10/06", condition: "new"),
    Book(title: "자바 마스터", price: 30000, imageName: "ISBN1235", description: "자바를 처음 배우는 학생을 위해 자바의 기본 개념과 실습 예제를 그림을 이용하여 쉽게 설명합니다. 자바의 이론적 개념→기본 예제→프로젝트 순으로 단계별 학습이 가능하며, 각 챕터의 프로젝트를 실습하면서 온라인 서점을 완성할 수 있도록 구성하였습니다.",  author: "송미영", publisher: "한빛아카데미", category: "IT모바일", unitsInStock: 1000, releaseDate: "2023/01/01", condition: "new"),
    Book(title: "파이썬 프로그래밍", price: 30000, imageName: "ISBN1236", description: "파이썬으로 프로그래밍을 시작하는 입문자가 쉽게 이해할 수 있도록 기본 개념을 상세하게 설명하며, 다양한 예제를 제시합니다. 또한 프로그래밍의 기초 원리를 이해하면서 파이썬으로 데이터를 처리하는 기법도 배웁니다.",  author: "최성철", publisher: "한빛아카데미", category: "IT모바일", unitsInStock: 1000, releaseDate: "2023/01/01", condition: "new"),
    // 같은 형식으로 책을 추가할 수 있음 (node.js로 데이터베이스 연결할 것)
]
