import React from 'react';
import { HashRouter, Route, Switch, Link } from 'react-router-dom';
import BookList from './BookList';
import BookDetailView from './BookDetailView';
import BookEditForm from './BookEditForm';


const Header = ({ title }) => (<h2>{title}</h2>);

const App = () => (
    <React.Fragment>
        <Header title="Электронная библиотека" />
        <HashRouter>
            <Switch>
                <Route exact path="/" component={BookList} />
                <Route path="/book/view/:id" component={BookDetailView} />
                <Route path="/book/edit/:id" component={BookEditForm} />
                <Route path="/book/edit" component={BookEditForm} />
            </Switch>
        </HashRouter>
    </React.Fragment>
);

export default App;
