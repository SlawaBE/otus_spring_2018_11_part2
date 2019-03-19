import React from 'react';
import { Link } from 'react-router-dom';
import BookQuickView from './BookQuickView';
import { sendGet } from '../utils/RequestUtil';

export default class BookList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            books: [],
        };
    }

    componentDidMount() {
        sendGet("/api/books",
            (books) => {
                this.setState({ books: books })
            },
            this.props.handleUnauthorized
        );
    }

    render() {
        const { books } = this.state;
        return (
            <React.Fragment>
                <Link to="/book/edit">Добавить книгу</Link>
                {
                    books.map((book, index) => (
                        <BookQuickView key={index} book={book} />
                    ))
                }
            </React.Fragment>
        )
    }

}
