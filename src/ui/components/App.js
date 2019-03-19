import React from 'react';
import MainPage from './MainPage';


const Header = ({ title }) => (<h2>{title}</h2>);

const App = () => (
    <React.Fragment>
        <Header title="Электронная библиотека" />
        <MainPage />
    </React.Fragment>
);

export default App;
