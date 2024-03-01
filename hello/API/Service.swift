//
//  Service.swift
//  hello
//
//  Created by 은옥 on 2/29/24.
//

import UIKit
import Alamofire

struct Service {
  
  static func getProductOption(productId: Int, completion: @escaping (Bool) -> Void) {
    
    let productOptionUrl = "http://localhost:8080/"
    
    AF.request(productOptionUrl, method: .get).responseJSON { response in
      guard let jsondata = response.data else { return }
      do {
        let productOptionDatas = try JSONDecoder().decode([ProductOptinCodAble.ProductOption].self,
                                                          from: jsondata)
        
        for index in 0..<productOptionDatas.count {
          
          if !(ProductOption.size.contains(productOptionDatas[index].size)) {
            ProductOption.size.append(productOptionDatas[index].size)
          }
          
          if !(ProductOption.color.contains(productOptionDatas[index].color)) {
            ProductOption.color.append(productOptionDatas[index].color)
          }
          
        }
        completion(true)
        
      } catch {
        print ("ProductOption Json Error: ", error.localizedDescription)
      }
    }
  }
  
  static func getProductInfoDetail(completion: @escaping (Bool) -> Void) {
    let productDetailUrl = "http://localhost:8080/"
    
    AF.request(productDetailUrl, method: .get).responseJSON { response in
      guard let jsonData = response.data else { return }
      do {
        let productDetailDatas = try JSONDecoder().decode([ProductInfoCodAble.ProductDetail].self, from: jsonData)
        
        for index in 0..<productDetailDatas.count {
          let id = productDetailDatas[index].product
          
          for indexImage in 0..<productDetailDatas[index].info_img.count {
            let image = productDetailDatas[index].info_img[indexImage].image
            
            if !(ProductInfoCategoryDatas.idAndInfoImages.keys.contains(id)) {
              ProductInfoCategoryDatas.idAndInfoImages[id] = [indexImage:image]
            } else {
              ProductInfoCategoryDatas.idAndInfoImages[id]?.updateValue(image, forKey: indexImage)
            }
          }
        }
        completion(true)
      } catch {
        print ("error : ", error.localizedDescription)
      }
    }
  }
  
  static func getBannerImages(completion: @escaping (Bool) -> Void ) {
    
    let bannerUrl = "http://localhost:8080/"
    AF.request(bannerUrl, method: .get).responseJSON { response in
      guard let jsonData = response.data else { return }
      do {
        let bannerImages = try JSONDecoder().decode([HomeInfoCodAble.Events].self, from: jsonData)
        for index in 0..<bannerImages.count {
          HomeInfoDatas.bannerImages.append(bannerImages[index].images)
        }
        DispatchQueue.main.async {
          completion(true)
        }
      } catch {
        print ("error : ", error.localizedDescription)
      }
    }
  }
  
  static func getProductList(completion: @escaping (Bool) -> Void) {
    let productUrl = "http://localhost:8080/"
    
    AF.request(productUrl, method: .get).responseJSON { response in
      guard let jsonData = response.data else { return }
      do {
        let productDatas = try JSONDecoder().decode(HomeInfoCodAble.ProductList.self, from: jsonData)
        
      } catch {
        print ("failed to convert error : ", error.localizedDescription)
      }
    }
  }
  
  static func signUpUser(username: String, email: String, password1: String, password2: String, phonenumber: String) {
    let signUpUrl = "http://localhost:8080/"
    guard let url = URL(string: signUpUrl) else  { return print ("can't not create url")}
    
    let userData: [String:Any] = ["username": username,
                                  "email": email,
                                  "password1" : password1,
                                  "password2" : password2,
                                  "phonenumber" : phonenumber]
    
    guard let userJsonData = try? JSONSerialization.data(withJSONObject: userData) else { return print ("JSONSerialization error")}
    
    var urlRequest = URLRequest(url: url)
    urlRequest.httpMethod = "POST"
    urlRequest.httpBody = userJsonData
    urlRequest.addValue("application/json", forHTTPHeaderField: "Content-Type")
    
    let task = URLSession.shared.dataTask(with: urlRequest) { (data, response, error) in
      guard error == nil else { return print("error : ", error!.localizedDescription)}
      guard let response = response as? HTTPURLResponse else { return print("response error")}
      
      guard let data = data,
            let signedUpUser = try? JSONSerialization.jsonObject(with: data) as? [String : Any] else { return }
      
      print (signedUpUser)
      print (response.statusCode)
    }
    task.resume()
  }
  
  static func signInUser(username: String, password: String, completion: @escaping (Bool) -> Void) {
    
    let loginUrl = "http://localhost:8080/"
    guard let url = URL(string: loginUrl) else { return print ("can't not create url")}
    
    let loginData: [String:Any] = ["username" : username,
                                   "password":password]
    
    guard let loginJsonData = try? JSONSerialization.data(withJSONObject: loginData) else { return print ("JSONSerialization error")}
    
    var urlRequest = URLRequest(url: url)
    urlRequest.httpMethod = "POST"
    urlRequest.httpBody = loginJsonData
    urlRequest.addValue("application/json", forHTTPHeaderField: "Content-Type")
    
    let task = URLSession.shared.dataTask(with: urlRequest) { (data, response, error) in
      guard error == nil else { return print("error : ", error!.localizedDescription)}
      guard let response = response as? HTTPURLResponse else { return print("response error")}
      guard let data = data,
            let loginUser = try? JSONSerialization.jsonObject(with: data) as? [String : Any] else { return }
      
      print ("loginUser : ", loginUser)
      
      guard response.statusCode == 200 else { return print ("response StatusCode Error")}
      completion(true)
    }
    task.resume()
  }
}
