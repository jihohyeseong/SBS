//
//  MainLoginViewController.swift
//  hello
//
//  Created by 은옥 on 2/29/24.
//
import UIKit
import SnapKit
import AuthenticationServices

class MainLoginViewController : UIViewController {

  private let titleLabel : UILabel = {
    let lb = UILabel()
    lb.text = "S B S"
    lb.textAlignment = .center
    lb.font = UIFont.boldSystemFont(ofSize: 20)
    lb.textColor = .white
    return lb
  }()
  
  private let descriptionLabel : UILabel = {
    let lb = UILabel()
    let attributeTitle = NSMutableAttributedString(string: "지금 가입하면 무료 배송은 기본!\n", attributes: [.font : UIFont(name: "AppleSDGothicNeo-Regular", size: 18)!, .foregroundColor : UIColor.white])
    attributeTitle.append(NSAttributedString(string: "로켓보다 빠른 하루배송까지!", attributes: [.font : UIFont.boldSystemFont(ofSize: 20), .foregroundColor : UIColor.white]))
    lb.attributedText = attributeTitle
    lb.numberOfLines = 3
    return lb
  }()
  
  private let couponLabel : UILabel = {
    let lb = UILabel()
    let attributeTitle = NSMutableAttributedString(string: "1만원 상당", attributes: [.font : UIFont.boldSystemFont(ofSize: 16), .foregroundColor : UIColor.white])
    attributeTitle.append(NSAttributedString(string: "의 쿠폰 혜택도 받아가세요", attributes: [.font : UIFont(name: "AppleSDGothicNeo-Regular", size: 16)!, .foregroundColor : UIColor.white]))
    lb.attributedText = attributeTitle
    return lb
  }()
  
  private lazy var stackView = UIStackView()
  
  private let emailLoginButton : LocalLoginButton = {
    let bt = LocalLoginButton(title: "이메일로 가입하기", color: .systemPink)
    bt.addTarget(self, action: #selector(signInWithEmail), for: .touchUpInside)
    return bt
  }()
  
  private let appleLoginButton : SocialLoginButton = {
    let viewModel = SocialCustomLoginViewModel(title: "Apple로 계속하기", image: "appleLogo")
    let bt = SocialLoginButton(with: viewModel, color: UIColor.black)
    bt.backgroundColor = UIColor.white
    bt.addTarget(self, action: #selector(signInWithApple), for: .touchUpInside)
    
    return bt
  }()
  
  private let facebookLoginButton : SocialLoginButton = {
    let viewModel = SocialCustomLoginViewModel(title: "Facebook으로 계속하기", image: "facebook")
    let bt = SocialLoginButton(with: viewModel, color: .white)
    bt.backgroundColor = UIColor.systemBlue
    bt.addTarget(self, action: #selector(signInWithFacebook), for: .touchUpInside)
    return bt
  }()
  
  private let googleLoginButton : SocialLoginButton = {
    let viewModel = SocialCustomLoginViewModel(title: "Google 계정으로 계속하기", image: "google")
    let bt = SocialLoginButton(with: viewModel, color: .lightGray)
    bt.addTarget(self, action: #selector(signInWithGoogle), for: .touchUpInside)
    bt.backgroundColor = UIColor.white
    return bt
  }()
  
  private let dividerView = DividerView()
  
  private let loginButton : LocalLoginButton = {
    let bt = LocalLoginButton(title: "로그인", color: .black)
    bt.addTarget(self, action: #selector(login), for: .touchUpInside)
    return bt
  }()
  
  private let backButton : UIButton = {
    let bt = UIButton()
    bt.setImage(UIImage(systemName: "xmark"), for: .normal)
    bt.tintColor = .white
    bt.clipsToBounds = true
    bt.contentMode = .scaleAspectFit
    return bt
  }()
  
  private let informationView = InformationView()

  override func viewDidLoad() {
    super.viewDidLoad()
    setUI()
    setConstraints()
  }
  
  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)
    setNavi()
  }
  
  private func setNavi() {
    navigationController?.navigationBar.isHidden = true
  }

  @objc func signInWithEmail() {
    let userAgreeVC = UserAgreeViewController()
    userAgreeVC.view.backgroundColor = .systemBackground
    navigationController?.pushViewController(userAgreeVC, animated: true)
  }
  
  @objc func signInWithApple(_ sender: ASAuthorizationAppleIDButton) {
    
    let provider = ASAuthorizationAppleIDProvider()
    let request = provider.createRequest()
    request.requestedScopes = [.fullName, .email]
    
    let controller = ASAuthorizationController(authorizationRequests: [request])
    controller.delegate = self
    controller.presentationContextProvider = self
    controller.performRequests()
  }
  
  @objc func signInWithFacebook() {
    
  }
  
  @objc func signInWithGoogle() {
    
  }
  
  @objc func login() {
    let controller = LoginViewController()
    let navi = UINavigationController(rootViewController: controller)
    navi.modalPresentationStyle = .fullScreen
    present(navi, animated: true)
  }
  
  private func setUI() {
    [backgroundImage, titleLabel, descriptionLabel, couponLabel, backButton].forEach {
      view.addSubview($0)
    }
    
    stackView = UIStackView(arrangedSubviews: [emailLoginButton, appleLoginButton, facebookLoginButton, googleLoginButton, dividerView ,loginButton])
    stackView.axis = .vertical
    stackView.spacing = 20
    view.addSubview(stackView)
    
    view.addSubview(informationView)
  }
  
  private func setConstraints() {
    backgroundImage.snp.makeConstraints {
      $0.top.equalToSuperview()
      $0.leading.trailing.bottom.equalToSuperview()
    }
    
    titleLabel.snp.makeConstraints {
      $0.top.equalTo(view.safeAreaLayoutGuide).offset(40)
      $0.leading.equalToSuperview().offset(30)
    }
    
    descriptionLabel.snp.makeConstraints {
      $0.top.equalTo(titleLabel.snp.bottom).offset(40)
      $0.leading.equalToSuperview().offset(30)
    }
    
    couponLabel.snp.makeConstraints {
      $0.top.equalTo(descriptionLabel.snp.bottom).offset(20)
      $0.leading.equalToSuperview().offset(30)
    }
    
    backButton.snp.makeConstraints {
      $0.top.equalTo(view.safeAreaLayoutGuide).offset(20)
      $0.trailing.equalToSuperview().offset(-20)
      $0.width.height.equalTo(20)
    }
    
    
    stackView.snp.makeConstraints {
      $0.top.equalTo(couponLabel.snp.bottom).offset(100)
      $0.leading.equalToSuperview().offset(20)
      $0.trailing.equalToSuperview().offset(-20)
    }
    
    informationView.snp.makeConstraints {
      $0.top.equalTo(stackView.snp.bottom).offset(100)
      $0.leading.equalToSuperview().offset(70)
      $0.trailing.equalToSuperview().offset(-70)
      $0.bottom.equalToSuperview().offset(-40)
    }
  }
  
}

extension MainLoginViewController: ASAuthorizationControllerDelegate {
  func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
    if let appleIDCredential = authorization.credential as? ASAuthorizationAppleIDCredential {
      let userIdentifier = appleIDCredential.user
      let userFirstName = appleIDCredential.fullName?.givenName
      let userLastName = appleIDCredential.fullName?.familyName
      let userEmail = appleIDCredential.email
      let appleIDProvider = ASAuthorizationAppleIDProvider()
      appleIDProvider.getCredentialState(
      forUserID: userIdentifier) { ( credentialState, error) in
        switch credentialState {
        case .authorized:
          print ("type(of: userEmail) : ", type(of: userEmail))
          print ("userEmail : ", userEmail!)
          print ("type(of: userLastName) : ", type(of: userLastName))
          print ("userLastName : ", userLastName!)
          print ("type(of: userFirstName) : ", type(of: userFirstName))
          print ("userFirstName : ", userFirstName!)
          break
        case .revoked:
          break
        case .notFound:
          break
        default:
          break
        }
      }
    }
  }
  
  func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
    print ("error.localizedDescription : ", error.localizedDescription)
  }
}

extension MainLoginViewController: ASAuthorizationControllerPresentationContextProviding {
  func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
    return view.window!
  }
}

