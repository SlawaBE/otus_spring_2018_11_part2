import React from 'react';
import { Redirect } from 'react-router-dom';
import InputList from './InputList';

export default class BookEditForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            summary: '',
            genres: [],
            authors: [],
            redirect: false
        }
    }

    componentDidMount() {
        const id = this.props.match.params.id;
        console.log(id);
        if (id) {
            fetch(`/api/book?id=${id}`, {
                method: 'GET',
            }).then((response) => {
                return response.json();
            }).then((value) => {
                this.setState({
                    id: value.id,
                    name: value.name,
                    summary: value.summary,
                    genres: value.genres,
                    authors: value.authors
                })
            });
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
        fetch('/api/book', {
            method: 'POST',
            body: JSON.stringify(book),
            headers: { 'Content-Type': 'application/json' }
        }).then((response) => {
            return response.json();
        }).then((value) => {
            this.setState({ redirect: `/book/view/${value.id}` });
        });
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
        const { name, summary, genres, authors } = this.state;
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
            </div>
        )
    }
}
