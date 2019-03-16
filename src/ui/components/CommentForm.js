import React from 'react';
import { sendJson } from '../utils/RequestUtil';

export default class CommentForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            userName: null,
            text: null
        };
    }

    handleChangeInput = (event) => {
        this.setState({ [event.target.name]: event.target.value });
    }

    handleSubmit = (event) => {
        const comment = {
            userName: this.state.userName,
            text: this.state.text,
            bookId: this.props.bookId
        };
        sendJson(`/api/book/${this.props.bookId}/comment`,
            comment,
            (value) => {
                this.props.addComment(value);
                this.setState({ userName: null, text: null });
            },
            this.props.handleUnauthorized
        );
    }

    render() {
        const { userName, text } = this.state;
        return (
            <div>
                <fieldset>
                    <legend>Добавить комментарий</legend>
                    <div className="row">
                        <label htmlFor="name-input">Ваше имя</label>
                        <input id="name-input" name="userName" onChange={this.handleChangeInput} value={userName || ''} />
                    </div>
                    <div className="row">
                        <label htmlFor="text-input">Текст комментария:</label>
                        <div>
                            <textarea id="text-input" name="text" onChange={this.handleChangeInput} value={text || ''}></textarea>
                        </div>
                    </div>
                    <div className="row">
                        <input onClick={this.handleSubmit} type="submit" value="Добавить" />
                    </div>
                </fieldset>
            </div>
        )
    }

}
