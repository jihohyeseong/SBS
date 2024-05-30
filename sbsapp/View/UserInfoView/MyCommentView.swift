//
//  MyCommentView.swift
//  sbsapp
//
//  Created by 은옥 on 5/30/24.
//

import SwiftUI

struct MyCommentView: View {
    @State private var comments: [Comment] = []
    
    var body: some View {
        VStack {
            if comments.isEmpty {
                Text("No comments available")
                    .padding()
            } else {
                List {
                    ForEach(comments) { comment in
                        VStack(alignment: .leading) {
                            Text(comment.bookname)
                                .font(.headline)
                            Text(comment.content)
                                .font(.body)
                            
                            HStack {
                                Text("작성자: \(comment.username)")
                                    .font(.footnote)
                                    .foregroundColor(.gray)
                                Spacer()
                            }
                        }
                        .padding(.vertical, 5)
                    }
                }
            }
        }
        .onAppear {
            fetchMyComments()
        }
        .navigationTitle("My Comments")
    }
    
    private func fetchMyComments() {
        CommentManager.shared.fetchMyComments { fetchedComments in
            DispatchQueue.main.async {
                if let fetchedComments = fetchedComments {
                    self.comments = fetchedComments
                } else {
                    print("No comments fetched")
                }
            }
        }
    }
}

