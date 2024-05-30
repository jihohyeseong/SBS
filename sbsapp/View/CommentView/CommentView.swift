//
//  CommentView.swift
//  sbsapp
//
//  Created by 은옥 on 5/24/24.
//

import SwiftUI

struct CommentView: View {
    @State private var comments: [Comment] = []
    @State private var newCommentContent: String = ""
    @State private var editingComment: Comment?
    @State private var summary: String = ""
    @State private var isSummaryVisible: Bool = true
    
    var bookId: Int
    var currentUser: String // 현재 로그인된 사용자 이름
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    var body: some View {
        NavigationView {
            VStack {
                summaryButton
                
                if !summary.isEmpty {
                    summaryView
                }

                Divider()
                
                ScrollView {
                    commentsSection
                }
                
                if let editingComment = editingComment {
                    editCommentView(editingComment: editingComment)
                }
                newCommentInput
            }
            .onAppear {
                fetchComments()
            }
            .background(Color(UIColor.systemGray6))
        }
    }
    
    private var summaryButton: some View {
        Button(action: summarizeAllComments) {
            Text("요약하기")
                .foregroundColor(.white)
                .padding()
                .background(Color.green)
                .cornerRadius(8)
        }
    }
    
    private var summaryView: some View {
        VStack {
            HStack {
                Text("요약")
                    .font(.headline)
                    .padding(.leading)
                Spacer()
                Button(action: {
                    withAnimation {
                        isSummaryVisible.toggle()
                    }
                }) {
                    Image(systemName: isSummaryVisible ? "chevron.up" : "chevron.down")
                        .padding(.trailing)
                }
            }
            
            if isSummaryVisible {
                Text(summary)
                    .font(.body)
                    .padding()
                    .background(Color.gray.opacity(0.2))
                    .cornerRadius(8)
                    .transition(.slide)
            }
        }
    }
    
    private var commentsSection: some View {
        VStack(spacing: 10) {
            if comments.isEmpty {
                Text("No comments available")
                    .padding()
            } else {
                ForEach(comments) { comment in
                    commentView(comment: comment)
                }
            }
        }
        .padding(.horizontal)
    }

    private func commentView(comment: Comment) -> some View {
        VStack(alignment: .leading, spacing: 5) {
            Text(comment.username)
                .font(.headline)
            Text(comment.content)
                .font(.body)
            
            HStack {
                Button(action: {
                    editingComment = comment
                    newCommentContent = comment.content
                }) {
                    Text("수정")
                        .foregroundColor(.blue)
                        .padding(.horizontal)
                        .background(Color.gray.opacity(0.2))
                        .cornerRadius(5)
                }
                
                Button(action: {
                    deleteComment(comment: comment)
                }) {
                    Text("삭제")
                        .foregroundColor(.red)
                        .padding(.horizontal)
                        .background(Color.gray.opacity(0.2))
                        .cornerRadius(5)
                }
            }
            .padding(.top, 5)
        }
        .padding()
        .background(Color.white)
        .cornerRadius(10)
        .shadow(radius: 1)
        .padding(.horizontal, 20)
    }
    
    private func editCommentView(editingComment: Comment) -> some View {
        VStack {
            TextField("Edit your comment", text: $newCommentContent)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            
            HStack {
                Button(action: {
                    var updatedComment = editingComment
                    updatedComment.content = newCommentContent
                    updateComment(comment: updatedComment)
                    newCommentContent = ""
                    self.editingComment = nil
                }) {
                    Text("업데이트")
                        .foregroundColor(.white)
                        .padding()
                        .background(Color.blue)
                        .cornerRadius(8)
                }
                
                Button(action: {
                    newCommentContent = ""
                    self.editingComment = nil
                }) {
                    Text("취소")
                        .foregroundColor(.white)
                        .padding()
                        .background(Color.red)
                        .cornerRadius(8)
                }
            }
            .padding(.top, 5)
        }
        .padding()
        .background(Color.white)
        .cornerRadius(10)
        .shadow(radius: 1)
        .padding(.horizontal)
    }
    
    private var newCommentInput: some View {
        VStack {
            TextField("Enter your comment", text: $newCommentContent)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            
            Button(action: {
                addComment(content: newCommentContent)
                newCommentContent = ""
            }) {
                Text("추가")
                    .foregroundColor(.white)
                    .padding()
                    .background(Color.blue)
                    .cornerRadius(8)
            }
            .padding()
        }
        .padding(.horizontal)
    }
    
    private func fetchComments() {
        print("Fetching comments for book ID: \(bookId)")
        CommentManager.shared.fetchComments(bookId: bookId) { fetchedComments in
            if let fetchedComments = fetchedComments {
                self.comments = fetchedComments
                print("Comments updated in view: \(self.comments)")
            } else {
                print("No comments fetched")
            }
        }
    }
    
    private func addComment(content: String) {
        let newComment = Comment(id: nil, bookId: bookId, username: currentUser, content: content, bookname: "")
        CommentManager.shared.addComment(bookId: bookId, comment: newComment) { result in
            switch result {
            case .success(let addedComment):
                self.comments.append(addedComment)
            case .failure(let error):
                print("Failed to add comment: \(error)")
            }
        }
    }
    
    private func updateComment(comment: Comment) {
        CommentManager.shared.updateComment(comment: comment) { result in
            switch result {
            case .success(let updatedComment):
                if let index = comments.firstIndex(where: { $0.id == comment.id }) {
                    comments[index] = updatedComment
                }
            case .failure(let error):
                print("Failed to update comment: \(error)")
            }
        }
    }

    private func deleteComment(comment: Comment) {
        CommentManager.shared.deleteComment(commentId: comment.id!) { result in
            switch result {
            case .success:
                self.comments.removeAll { $0.id == comment.id }
            case .failure(let error):
                print("Failed to delete comment: \(error)")
            }
        }
    }

    private func summarizeAllComments() {
        CommentManager.shared.summarizeComments(bookId: bookId) { result in
            switch result {
            case .success(let summaryResult):
                self.summary = summaryResult
            case .failure(let error):
                print("Failed to summarize comments: \(error)")
            }
        }
    }
}
