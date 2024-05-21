import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate, useParams, BrowserRouter as Router, Route, Link } from "react-router-dom";
import axios from 'axios';

function Book() {
    const navigate = useNavigate();

    const { id } = useParams();
    const [book, setBook] = useState();
    const [comment, setComment] = useState([]);
    const [newcomment, setNewcomment] = useState('')
    const [cartQuantity, setCartQuantity] = useState(1);
    const [purchaseQuantity, setPurchaseQuantity] = useState(1);
    const [editingCommentId, setEditingCommentId] = useState(null);
    const [editingContent, setEditingContent] = useState("");
    const [bookid, setBookid] = useState();
    const [summary, setSummary] = useState('');
    const [bookdetail, setBookdetail] = useState('');
    const [page, setPage] = useState(0);

    const increaseQuantity = () => {
      setCartQuantity(prevQuantity => prevQuantity + 1);
    };
  
    const decreaseQuantity = () => {
      setCartQuantity(prevQuantity => prevQuantity > 1 ? prevQuantity - 1 : 1);
    };

    const increasePurchaseQuantity = () => {
        setPurchaseQuantity(prevQuantity => prevQuantity + 1);
      };
    
    const decreasePurchaseQuantity = () => {
        setPurchaseQuantity(prevQuantity => prevQuantity > 1 ? prevQuantity - 1 : 1);
    };

    useEffect(() => {
        if(id) {
            const url = `/api/books/${id}`;
            const commenturl = `/api/books/${id}/comments`;
            const bookdetailurl = `/api/${id}/detail`

            axios.get(`${url}`)
            .then(response => setBook(response.data))
            .catch(error => console.log(error));

            
            axios.get(`${commenturl}`)
            .then(response => setComment(response.data))
            .catch(error => console.log(error));

            axios.get(`${bookdetailurl}`)
            .then(response => setBookdetail(response.data))
            .catch(error => console.log(error));
        }
    }, [id]);

    if(!book) {
        return <h2>Book Loading...</h2>
    }

    const cart = async () => {
        const isConfirmed = window.confirm("장바구니에 " + cartQuantity + "권을 추가하시겠습니까?");
    
        if(isConfirmed) {
            try {
                await axios.post(`/api/books/${id}/cart`, {
                    "quantity": cartQuantity,
                    "bookId": id
                });
    
                const isConfirmed2 = window.confirm("장바구니에 " + cartQuantity + "권을 추가하였습니다. 장바구니로 이동하시겠습니까?");
                if(isConfirmed2) {
                    navigate("/cart");
                } else {
                    window.location.reload();
                }
            } catch (error) {
                console.error(error);
                alert('장바구니 추가에 실패했습니다. 로그인이 필요한 서비스 입니다.');
                navigate('/login');
            }
        } else {
            window.location.reload();
        }
    }
    
    const purchase = async () => {
        const isConfirmed = window.confirm(purchaseQuantity + "권을 바로 구매하시겠습니까?");
    
        if (isConfirmed) {
            try {
                const response = await axios.post(`/payment/ready`, [{
                    "bookname": book.bookname,
                    "quantity": purchaseQuantity,
                    "price": book.price
                }]);
                
                const isMobile = /iPhone|iPad|iPod|Android/i.test(navigator.userAgent);
                const paymentPageUrl = isMobile ? response.data.next_redirect_mobile_url : response.data.next_redirect_pc_url;
                
                window.location.href = paymentPageUrl;

                // axios.post(`/api/books/${id}/purchase`, {
                //     "quantity": purchaseQuantity,
                //     "bookId": id
                // }); 
                
                localStorage.setItem('purchasedBooks', JSON.stringify([{
                    "bookId": id, 
                    "quantity": purchaseQuantity, 
                    "bookname": book.bookname, 
                    "price": book.price
                }])
                );

            } catch (error) {
                console.error(error);
                alert('구매목록 추가에 실패했습니다. 로그인이 필요한 서비스 입니다.');
                navigate('/login');
            }
        } 
        else {
            window.location.reload();
        }
    };

    const createcomment = async (event) => {
        event.preventDefault();
        try {
            await axios.post(`/api/books/${id}/comments`, {
                "bookId": id,
                "content": newcomment
            })
            alert('댓글이 작성되었습니다.');
            window.location.reload();
        } catch (error) {
            console.error(error)
            alert('댓글 작성에 실패했습니다. 로그인이 필요한 서비스 입니다.');
            navigate('/login');
        }
    }

    const deletecomment = async (id) => {
        const isConfirmed = window.confirm("댓글을 삭제 하시겠습니까?");

        if(isConfirmed) {
            try {
                await axios.delete(`/api/comments/${id}`);
                alert('댓글이 삭제되었습니다.');
                window.location.reload();
            } catch (error) {
                console.error("삭제에 실패했습니다:", error);
                alert('댓글 삭제에 실패했습니다.');
                window.location.reload();
            }
        }
        else {
            window.location.reload();
        }
        
    }

    const startEdit = (comment) => {
        setEditingCommentId(comment.id);
        setEditingContent(comment.content);
    };

    const saveComment = async (id) => {
        try {
            await axios.patch(`/api/comments/${id}`, {
                "id" : editingCommentId,
                "bookId": bookid,
                "content": editingContent
            });
            alert('댓글이 수정되었습니다.');
            window.location.reload();
        } catch (error) {
            console.error("현재 id: "+id + "현재 commentid : " + editingCommentId)
            alert('댓글 수정에 실패했습니다.');
            window.location.reload();
        }
    };

    const cancelComment = () => {
        setEditingContent(comment.content);
        window.location.reload();
    };

    function AISummary(commentsData) {
        const response = fetch(`/api/summary`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(commentsData)
        })
        .then(response => response.json()) // 서버 응답을 JSON으로 변환
        .then(data => {
            setSummary(data.summary);
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
    }

    const showebook = () => {    
        axios.get(`/api/ebook/${book.id}/${page}`)
        .then(response => {
            window.open(`/books/ebooks/${book.id}/${page}`, 'ebookPreview', 'width=1000,height=800,left=0,top=100');
            console.log(response);
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('해당 도서는 E-Book 미리보기를 지원하지 않습니다.');
        });
    }
    
    
    return (
        <html>
            <head>
                <title>도서 정보</title>
            </head>

            <body>
                <div className="jumbotron"> 
                    <div className="container">
                        <h1 className="display-3" align="center">도서 정보</h1>
                        <h5 className="display-5" align="center">Book Details</h5>
                    </div>
                </div>

                <div className="container">
                    <div className="row">
                        <div className="col-md-4" align="center">
                            <img src={book.imageurl} style={{width:'100%'}}></img>
                        </div>
                        
                        <div className="col-md-8" align="center">
                            <h3>{book.bookname}</h3>
                            <br />
                            <p><b>도서코드 : </b><span className="badge badge-info">{book.id}</span></p>
                            <p><b>저자</b> : {book.author}</p>
                            <p><b>출판사</b> : {book.publisher}</p>
                            <p><b>출판일</b> : {book.releasedate}</p>
                            <p><b>분류</b> : {book.category}</p>
                            <p><b>재고수</b> : {book.unitsinstock}</p>
                            <h4>₩{book.price}</h4>
                            <div>
                                <div>
                                    <button 
                                        onClick={decreaseQuantity} 
                                        className="btn btn-secondary" 
                                        style={{margin: '0 10px'}}
                                        disabled={cartQuantity === 1}
                                    >
                                        -
                                    </button>
                                    <span>{cartQuantity}</span>
                                    <button 
                                        onClick={increaseQuantity} 
                                        className="btn btn-secondary" 
                                        style={{margin: '0 10px'}}
                                    >
                                        +
                                    </button>
                                    <button className="btn btn-success" onClick={cart}>장바구니에 추가 &raquo;</button>
                                </div>
                                
                            </div>
                            
                            <br />
                            
                            <div>
                                <div>
                                    <button 
                                        onClick={decreasePurchaseQuantity} 
                                        className="btn btn-secondary" 
                                        style={{margin: '0 10px'}}
                                        disabled={purchaseQuantity === 1}
                                    >
                                        -
                                    </button>
                                    <span>{purchaseQuantity}</span>
                                    <button 
                                        onClick={increasePurchaseQuantity} 
                                        className="btn btn-secondary" 
                                        style={{margin: '0 10px'}}
                                    >
                                        +
                                    </button>
                                    <button className="btn btn-danger" onClick={purchase}>바로 구매하기 &raquo;</button>
                                </div>

                                <br />
                                <button className="btn btn-primary" onClick={showebook}>E-Book 미리보기</button>

                            </div>

                        </div>

                        <div className="col-md-10">
                        <br />

                        <div className="container" align="center">
                            <img src={bookdetail.detailUrl} style={{ width: '800px', maxWidth: '100%'}}></img>
                        </div>        

                        <br />
                        <br />               

                        <div className="col-md-10">
                            {book.description}
                        </div>

                        <br />
                        <br />
                     
                        <form className="form-horizontal" onSubmit={(e) => {
                                e.preventDefault();
                                if(comment.length > 0) {
                                    const commentsData = comment.map(c => ({
                                        id: c.id,
                                        bookId: book.id,
                                        username: c.username,
                                        content: c.content,
                                        bookName: book.bookname
                                    }));
                                    AISummary(commentsData);
                                }
                            }}>
                                <strong>모든 댓글 AI 요약</strong>
                                <textarea name="summary" className="form-control" style={{width: '100%', height: '100px'}} value={summary} placeholder="AI Comment Summary" readOnly/> 
                                <br />
                                <button className="btn btn-primary">요약하기</button>
                            </form>

                            <br />

                            <b>댓글</b>
                            
                            <div>

                            {comment && comment.map((comment) => (
                                <div className="card" key={comment.id}>
                                    <div className="card-header">
                                        {comment.username}  
                                    </div>
                                    <div className="card-body">
                                        {editingCommentId === comment.id ? (
                                            <>
                                                <div className="d-flex">
                                                    <div className="col-sm-5">
                                                        <input value={editingContent} onChange={(e) => setEditingContent(e.target.value)} className="form-control"/>
                                                    </div>
                                                    <div className="float-righ">  
                                                        <button className="btn btn-success" onClick={() => saveComment(comment.id)}>저장</button> 
                                                        &nbsp;
                                                        <button className="btn btn-secondary" onClick={cancelComment}>취소</button>
                                                    </div>
                                                </div>
                                            </>
                                        ) : (
                                            <>
                                                {comment.content}
                                                <div className="float-right">
                                                    <button className="btn btn-success" onClick={() => startEdit(comment)}>수정 &raquo;</button>              
                                                    &nbsp;
                                                    <button className="btn btn-danger" onClick={() => deletecomment(comment.id)}>삭제 &raquo;</button>
                                                </div>
                                            </>
                                        )}
                                    </div>                           
                                </div>   
                            ))}             
                                {comment.length === 0 && (
                                    <div className="alert alert-info" role="alert">
                                        작성된 댓글이 없습니다.
                                    </div>
                                )}                  
                            </div>

                            <br />

                            <form onSubmit={createcomment} className="form-horizontal">
                                <div className="d-flex">
                                    <div className="col-sm-5">
                                        <input name="content" value={newcomment} onChange={(e) => setNewcomment(e.target.value)} className="form-control" placeholder="댓글을 입력하세요"/>
                                    </div>
                                    <div className="col-sm-2">  
                                        <button type="submit" className="btn btn-primary">등록</button>      
                                    </div>
                                </div>                                         
                            </form>
                        </div>
                    </div>
                </div>
            </body>
        </html>        
    )
}

export default Book;