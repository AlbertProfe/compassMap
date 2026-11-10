import axios from 'axios';
import { useEffect, useState } from 'react';


export default function OpenLibrary() {

  const [books, setBooks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    axios
      .get('https://openlibrary.org/search.json?q=to+the+lighthouse&limit=100')
      .then(({ data }) => {
        setBooks(data.docs);
        setIsLoading(false);
      });
  }, []);

  return (
    <section>
      <h2>Books</h2>
      {isLoading ? (
        <p>Loading books...</p>
      ) : (
        <ul className="book-list">
          {books.map((book) => (
            <li key={book.key}>
              <strong>{book.title}</strong>
              {/* .length gives the number of authors: 0 is falsy,
              while a positive number is truthy.
              `template literals` are used to concatenate the author names with commas.
              join(", ") combines author names with comma-space separators;
              the template literal adds " by " before them.
              */}
              {book.author_name?.length
                ? ` by ${book.author_name.join(", ")}`
                : ""}
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}
