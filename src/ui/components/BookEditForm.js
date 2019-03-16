import React from 'react';
import { Redirect } from 'react-router-dom';
import InputList from './InputList';
import { sendGet, sendJson } from '../utils/RequestUtil';
import { ErrorMessage } from '../utils/Styles';

export default class BookEditForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            summary: '',
            genres: [],
            authors: [],
            redirect: false,
            errorMessage: null,
        }
    }

    componentDidMount() {
        const id = this.props.match.params.id;
        if (id) {
            sendGet(`/api/book?id=${id}`,
                (value) => {
                    this.setState({
                        id: value.id,
                        name: value.name,
                        summary: value.summary,
                        genres: value.genres,
                        authors: value.authors
                    })
                },
                this.props.handleUnauthorized
            );
        }
    }

    handleChangeInput = (event) => {
        this.setState({ [event.target.name]: event.target.value });
    }

    handleSubmit = (event) => {
        const book = {
            id: this.state.id,
            name: this.state.name,
            summary: this.state.summary,
            genres: this.state.genres,
            authors: this.state.authors
        };
        sendJson('/api/book',
            book,
            (value) => {
                this.setState({ redirect: `/book/view/${value.id}` });
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

    handleCancel = (event) => {
        this.setState({ redirect: !this.state.id ? "/" : `/book/view/${this.state.id}` });
    }

    updateGenres = (genres) => {
        this.setState({ genres: genres });
    }

    updateAuthors = (authors) => {
        this.setState({ authors: authors });
    }

    render() {
        const { redirect } = this.state;
        if (redirect) {
            return <Redirect to={redirect} />
        }
        const { name, summary, genres, authors, errorMessage } = this.state;
        return (
            <div>
                <h3>Информация о книге:</h3>

                <div className="row">
                    <label htmlFor="holder-input">Название:</label>
                    <input id="holder-input" name="name" type="text" onChange={this.handleChangeInput} value={name} />
                </div>

                <InputList title='Жанры' list={genres} updateParentState={this.updateGenres} />

                <InputList title='Авторы' list={authors} updateParentState={this.updateAuthors} />

                <div className="row">
                    <label htmlFor="summary-input">Описание:</label>
                    <div>
                        <textarea id="summary-input" name="summary" onChange={this.handleChangeInput} value={summary}></textarea>
                    </div>
                </div>

                <input type="submit" onClick={this.handleSubmit} value="Сохранить" />
                <input type="submit" onClick={this.handleCancel} value="Отмена" />
                {
                    errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>
                }
            </div>
        )
    }
}
