import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate, BrowserRouter as Router, Route, Link } from "react-router-dom";
import axios from 'axios';
// import { MDBInputGroup, MDBInput, MDBIcon, MDBBtn } from 'mdb-react-ui-kit';


function Books() {
    const navigate = useNavigate();
    const [books, setBooks] = useState([]);
    const [bookname, setBookname] = useState('');
    const url="/api/books";

    useEffect(() => {
        axios.get(`${url}`)
        .then(response => setBooks(response.data))
        .catch(error => console.log(error))
    }, []);

    const booknamechage = (event) => {
        setBookname(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        if(bookname){
            console.log("검색창1:",bookname);
            navigate(`/books/search/${bookname}`);
        }
        else if (bookname == "") {
            alert("검색 결과가 없습니다.");
            console.log("검색창2:",bookname);
        }
    };
    
    return (
        <html>
            <head>
                <title>도서 목록</title>
            </head>

            <body>
                <div className="jumbotron"> 
                    <div className="container">
                        <h1 className="display-3" align="center">도서 목록</h1>
                        <h5 className="display-5" align="center">Books List</h5>
                    </div>
                </div> 

            <form className="d-flex justify-content-center" onSubmit={handleSubmit}>
                <div className="col-md-8">
                    <input name="bookname" value={bookname} className="form-control" onChange={booknamechage} placeholder="Search" aria-label="Search" />
                </div>
                    <button className="btn btn-primary" type="submit">검색</button>
            </form>

            <div className="container">
                <div className="row">
                    {books && books.map((book) => (
                        <div className="col-12 d-flex mb-4" key={book.id}>
                            <div>
                                <Link to={`/books/book/${book.id}`}>
                                    <br />
                                    <img src={book.imageurl} style={{width:'70%'}} alt={book.bookname} />
                                </Link>
                            </div>
                            <div>
                                <br />
                                <Link to={`/books/book/${book.id}`}>
                                    <h3>{book.bookname}</h3>
                                </Link>
                                <p>{book.author}</p>
                                {book.publisher} | {book.releasedate}
                                <p>₩{book.price}</p>
                                <p>{book.description.length > 100 ? book.description.slice(0, 100) + "..." : book.description}</p>
                            </div>
                        </div>
                    ))}
                    
                </div>
            </div>
            
            </body>
        </html>        
    );
}

export default Books;