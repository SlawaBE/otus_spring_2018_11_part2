import React from 'react';
import { Link, Redirect } from 'react-router-dom';
import BookInfo from './BookInfo';
import CommentBlock from './CommentBlock';
import { sendDelete } from '../utils/RequestUtil';
import { ErrorMessage } from '../utils/Styles';

export default class BookDetailView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            redirect: null,
            errorMessage: null,
        }
    }

    handleDelete = () => {
        sendDelete(`/api/book?id=${this.props.match.params.id}`,
            () => {
                this.setState({ redirect: '/' });
            },
            this.handleError
        );
    }

    handleError = (errorCode) => {
        if (errorCode == 403) {
            this.setState({ errorMessage: "Недостаточно прав" });
        } else {
            this.props.handleUnauthorized();
        }
    }

    handleEdit = () => {
        this.setState({ redirect: `/book/edit/${this.props.match.params.id}` });
    }

    render() {
        const { redirect, errorMessage } = this.state;
        if (redirect) {
            return <Redirect to={redirect} />;
        }
        const bookId = this.props.match.params.id;
        return (
            <React.Fragment>
                <div>
                    <Link to='/'>Вернуться к списку</Link>
                </div>

                <BookInfo id={bookId} handleUnauthorized={this.props.handleUnauthorized} />

                <div>
                    <input type="submit" value='Редактировать' onClick={this.handleEdit} />
                    <input type="submit" value='Удалить' onClick={this.handleDelete} />
                </div>

                {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}

                <CommentBlock bookId={bookId} handleUnauthorized={this.props.handleUnauthorized} />
            </React.Fragment>
        );
    }
}
