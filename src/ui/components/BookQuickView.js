import React from 'react';
import { Link } from 'react-router-dom';

const BookQuickView = ({ book: { id, name, summary } }) => (
    <div>
        <h3>{name}</h3>
        <Link to={`/book/view/${id}`}>Подробнее</Link>
        <p>{summary}</p>
        <hr />
    </div>
);

export default BookQuickView;
