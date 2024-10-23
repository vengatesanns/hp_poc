import React from 'react';
import './App.css';
import SignIn from './sign-in/sign-in';
import Dashboard from './dashboard/dashboard';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import FallBackComponent from './utility-components/fall-back-component/fall-back-component';

function App() {
    return (
        <div className="App">
            <Router>
                <Switch>
                    <Route path="/" exact component={SignIn} />
                    <Route path="/dashboard" component={Dashboard} />
                    <Route path="/**" component={FallBackComponent} />
                </Switch>
            </Router>
        </div>
    );
}

export default App;
