import React from 'react';
import { ErrorMessage } from '../utils/Styles';

class LoginForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            errorMessage: null,
            login: "",
            password: "",
        }
    }

    handleChangeInput = (event) => {
        this.setState({ [event.target.name]: event.target.value });
    }

    handleSubmit = () => {
        const { login, password } = this.state;
        const data = new FormData();
        data.append("username", login)
        data.append("password", password)
        fetch('/perform_login', {
            method: 'POST',
            body: new URLSearchParams(data)
        }).then(response => {
            if (response.status == 200) {
                this.props.handleLogin();
                this.setState({ login: "", password: "", errorMessage: null })
            } else {
                this.setState({ errorMessage: "Неверный логин или пароль" })
            }
        });
    }

    render() {
        const { login, password, errorMessage } = this.state;
        return (
            <div>
                <fieldset>
                    <legend>Вход</legend>
                    <div className="row">
                        <label htmlFor="login-input">Ваш логин:</label>
                        <input type="text" id="login-input" name="login" onChange={this.handleChangeInput} value={login || ''} />
                    </div>
                    <div className="row">
                        <label htmlFor="password-input">Пароль:</label>
                        <input type="password" id="password-input" name="password" onChange={this.handleChangeInput} value={password || ''} />
                    </div>
                    {
                        errorMessage &&
                        <ErrorMessage>{errorMessage}</ErrorMessage>
                    }
                    <div className="row">
                        <input onClick={this.handleSubmit} type="submit" value="Войти" />
                    </div>
                </fieldset>
            </div>
        );
    }
}

export default LoginForm;