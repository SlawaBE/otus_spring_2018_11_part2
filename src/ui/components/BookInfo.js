import React from 'react';
import Loader from './Loader';
import DataList from './DataList';

export default class BookInfo extends React.Component {

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
        if (!book) {
            return <Loader />
        }
        const { id, name, summary, genres, authors } = book;
        return (
            <React.Fragment>
                <div className="row">
                    <b>{id}</b>
                </div>

                <div className="row">
                    <h3>{name}</h3>
                </div>

                <DataList title='Жанры:' list={genres} />

                <DataList title='Авторы' list={authors} />

                <div>
                    <b>Описание:</b>
                    <p>{summary}</p>
                </div>
            </React.Fragment>
        )
    }

}
