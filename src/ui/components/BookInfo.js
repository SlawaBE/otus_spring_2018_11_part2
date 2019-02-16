import React from 'react';
import Loader from './Loader';
import DataList from './DataList';

class BookInfo extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            book: null
        }
    }

    componentDidMount() {
        const id = this.props.id;
        fetch(`/api/book?id=${id}`, {
            method: 'GET',
        }).then((response) => {
            return response.json();
        }).then((value) => {
            this.setState({
                book: value
            })
        });
    }


    render() {
        const { book } = this.state;
        return (
            !book ? <Loader /> :
                <React.Fragment>
                    <div className="row">
                        <b>{book.id}</b>
                    </div>

                    <div className="row">
                        <h3>{book.name}</h3>
                    </div>

                    <DataList title='Жанры:' list={book.genres} />

                    <DataList title='Авторы' list={book.authors} />

                    <div>
                        <b>Описание:</b>
                        <p>{book.summary}</p>
                    </div>
                </React.Fragment>
        )
    }

}

export default BookInfo;