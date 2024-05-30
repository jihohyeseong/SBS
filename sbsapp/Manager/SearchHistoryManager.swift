//
//  SearchHistoryManager.swift
//  sbsapp
//
//  Created by 은옥 on 3/8/24.
//

import Foundation

class SearchHistoryManager {
    static let shared = SearchHistoryManager()
    
    private let searchHistoryKey = "SearchHistory"
    
    func saveSearchQuery(_ query: String) {
        var searchHistory = getSearchHistory()
        searchHistory.append(query)
        UserDefaults.standard.set(searchHistory, forKey: searchHistoryKey)
    }
    
    func getSearchHistory() -> [String] {
        return UserDefaults.standard.array(forKey: searchHistoryKey) as? [String] ?? []
    }
    
    func clearSearchHistory() {
        UserDefaults.standard.removeObject(forKey: searchHistoryKey)
    }
}

