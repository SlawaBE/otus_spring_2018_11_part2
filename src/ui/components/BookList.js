import React from 'react';
import {Link} from 'react-router-dom';
import BookQuickView from './BookQuickView';

export default class BookList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            books: [],
        };
    }

    componentDidMount() {
        fetch("/api/books", {
            method: 'GET',
        }).then((response) => {
            return response.json();
        }).then((value) => {
            this.setState({
                books: value
            })
        });
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
