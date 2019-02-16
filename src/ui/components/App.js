import React from 'react';
import { HashRouter, Route, Switch, Link } from 'react-router-dom';
import BookList from './BookList';
import BookDetailView from './BookDetailView';
import BookEditForm from './BookEditForm';


const Header = (props) => (
    <h2>{props.title}</h2>
);

export default class App extends React.Component {

    constructor() {
        super();
        this.state = {};
    }

    render() {
        return (
            <React.Fragment>
                <Header title={'Электронная библиотека'} />
                <HashRouter>
                    <Switch>
                        <Route exact path="/" component={BookList} />
                        <Route path="/book/view/:id" component={BookDetailView} />
                        <Route path="/book/edit/:id" component={BookEditForm} />
                        <Route path="/book/edit" component={BookEditForm} />
                    </Switch>
                </HashRouter>
            </React.Fragment>
        )
    }
};

