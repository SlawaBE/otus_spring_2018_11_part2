import React from 'react';
import { HashRouter, Route, Switch } from 'react-router-dom';
import BookList from './BookList';
import BookDetailView from './BookDetailView';
import BookEditForm from './BookEditForm';
import { logout } from '../utils/RequestUtil';
import LoginForm from './LoginForm';

export default class MainPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isAuthorized: true,
        }
    }

    handleLogout = () => {
        logout(() =>
            this.setState({ isAuthorized: false })
        );
    }

    handleUnauthorized = () => {
        this.setState({ isAuthorized: false });
    }

    handleLogin = () => {
        this.setState({ isAuthorized: true });
    }

    render() {
        return (

            this.state.isAuthorized ?
                <React.Fragment>
                    <div>
                        <input type="submit" onClick={this.handleLogout} value="Выйти" />
                    </div>
                    <div>
                        <HashRouter>
                            <Switch>
                                <Route exact path="/" component={(props) => <BookList {...props} handleUnauthorized={this.handleUnauthorized} />} />
                                <Route path="/book/view/:id" component={(props) => <BookDetailView {...props} handleUnauthorized={this.handleUnauthorized} />} />
                                <Route path="/book/edit/:id" component={(props) => <BookEditForm {...props} handleUnauthorized={this.handleUnauthorized} />} />
                                <Route path="/book/edit" component={(props) => <BookEditForm {...props} handleUnauthorized={this.handleUnauthorized} />} />
                            </Switch>
                        </HashRouter>
                    </div>
                </React.Fragment>
                :
                <LoginForm handleLogin={this.handleLogin} />
        )
    }

}