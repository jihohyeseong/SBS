//
//  SummaryView.swift
//  sbsapp
//
//  Created by 은옥 on 5/30/24.
//

import SwiftUI

struct SummaryView: View {
    let summary: String
    
    var body: some View {
        VStack {
            Text("요약")
                .font(.largeTitle)
                .padding()
            
            Text(summary)
                .font(.body)
                .padding()
                .background(Color.gray.opacity(0.2))
                .cornerRadius(8)
            
            Spacer()
        }
        .padding()
        .navigationTitle("Summary")
    }
}
