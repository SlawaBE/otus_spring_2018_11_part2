import React from 'react';
import { Link } from 'react-router-dom';

class BookQuickView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        const { book } = this.props;
        return (
            <div>
                <h3>{book.name}</h3>
                <Link to={`/book/view/${book.id}`}>Подробнее</Link>
                <p>{book.summary}</p>
                <hr />
            </div>
        );
    }
}

export default BookQuickView;
