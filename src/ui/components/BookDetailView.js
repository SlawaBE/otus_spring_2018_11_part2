import React from 'react';
import { Link, Redirect } from 'react-router-dom';
import BookInfo from './BookInfo';
import CommentBlock from './CommentBlock';

class BookDetailView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            redirect: null
        }
    }

    handleDelete = () => {
        fetch(`/api/book?id=${this.props.match.params.id}`, { method: 'DELETE' })
            .then((response) => {
                this.setState({ redirect: '/' });
            });
    }

    handleEdit = () => {
        this.setState({ redirect: `/book/edit/${this.props.match.params.id}` });
    }

    render() {
        if (this.state.redirect) {
            return <Redirect to={this.state.redirect} />;
        }
        const bookId = this.props.match.params.id;
        return (
            <React.Fragment>
                <div>
                    <Link to='/'>Вернуться к списку</Link>
                </div>

                <BookInfo id={bookId} />

                <div>
                    <input type="submit" value='Редактировать' onClick={this.handleEdit} />
                    <input type="submit" value='Удалить' onClick={this.handleDelete} />
                </div>

                <CommentBlock bookId={this.props.match.params.id} />
            </React.Fragment>
        );
    }
}

export default BookDetailView;
