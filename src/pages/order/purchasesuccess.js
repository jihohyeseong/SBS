import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import style from '../cart/style.css';

function Purchasesuccess () {
    const [user, setUser] = useState(null);
    const [purchasedBooks, setPurchasedBooks] = useState(null);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await fetch(`/api/mypage`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                if(response.ok) {
                    const data = await response.json();
                    setUser(data);
                } else {
                    console.error('서버로부터 사용자 정보를 가져오는데 실패했습니다.');
                }
            } catch (error) {
                console.error('사용자 정보 요청 중 에러 발생:', error);
            }
        };
        fetchUser();
        
        // localStorage에서 구매 정보 가져오기
        const storedPurchasedBooks = localStorage.getItem('purchasedBooks');
        
        if (storedPurchasedBooks) {
            setPurchasedBooks(JSON.parse(storedPurchasedBooks));
        }

        console.log(storedPurchasedBooks);

        return () => {
            localStorage.removeItem('purchasedBooks');
        };
    }, []);

    if (!user) {
        return <h2>No User Information...</h2>;
    }

    return (
        <div>
            <head>
                <title>Purchase Success</title>
            </head>

            <body>
                <div className="jumbotron"> 
                    <div className="container">
                        <h1 className="display-3" align="center">구매 완료</h1>
                        <h5 className="display-5" align="center">Purchase Success</h5>
                    </div>
                </div>

                <div className="container">
                    <div className="text-center">
                        <h3>구매가 완료되었습니다. 감사합니다.</h3>
                    </div>
                    
                    <div className="table-container">
                        <table className="table table-hover">
                            <thead>
                                <tr>
                                    <th>구매자</th>
                                    <th>배송지</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>{user.username}</td>
                                    <td>{user.address}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    {purchasedBooks && (
                        <div className="table-container">
                            <table className="table table-hover">
                                <thead>
                                    <tr>
                                        <th>도서명</th>
                                        <th>수량</th>
                                        <th>가격</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {purchasedBooks.map((book, index) => (
                                        <tr key={index}>
                                            <td>{book.bookname}</td>
                                            <td>{book.quantity} 권</td>
                                            <td>{book.price} 원</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    )}

                </div>
            </body>
        </div>
    );
}

export default Purchasesuccess;