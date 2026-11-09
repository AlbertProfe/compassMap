import axios from 'axios';
import { useEffect, useState } from 'react';
import './App.css';
import heroImg from './assets/hero.png';

export default function App() {
  const [count, setCount] = useState(0)

  const [books, setBooks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchBooks = async () => {
      const response = await axios.get(
        "https://openlibrary.org/search.json?q=the+lord+of+the+rings&limit=100",
      );
      const booksData = response.data.docs;
      console.log(booksData);
      setBooks(booksData);
      setIsLoading(false);
    };
    fetchBooks();
  }, []);

  return (
    <div>
      <div className="hero">
        <img src={heroImg} className="base" width="170" height="179" alt="" />
      </div>
      <div>
        <h1>CompassMap React</h1>
        <p>This is a React project bootstrapped</p>
        <p> with Vite to render the Spring Boot CompassMap application.</p>
        <p>
          The project is configured to use <b>React 18</b> and <b>Vite</b>
        </p>
        <p>for fast development and optimized builds.</p>
      </div>
      <hr/>
      <h2>Hook: useState</h2>
      <div className="card">
        <button
          type="button"
          className="counter"
          onClick={() => setCount((count) => count + 1)}
        >
          Count is {count}
        </button>
      </div>
      <hr/>
      <h2>Books</h2>
      {isLoading ? (
        <p>Loading books...</p>
      ) : (
        <p>
          {books.map((book) => (
            <p key={book.key}>
              <strong>{book.title}</strong> by {book.author_name?.join(", ")}
            </p>
          ))}
        </p>
      )}
    </div>
  );
}

