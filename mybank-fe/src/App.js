import './App.css';
import Home from './Home';
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom/cjs/react-router-dom.min';
import CustomerList from './CustomerList';
import CustomerEdit from './CustomerEdit';
import CreateCustomer from './CreateCustomer';
import AccountList from './AccountList';
import CreateAccount from './CreateAccount';
import AccountWithdraw from './AccountWithdraw';
import AccountDeposit from './AccountDeposit';

class App extends Component{
  render(){
    return(
      <Router>
        <Switch>
          <Route path='/' exact={true} component = {Home}/>
          <Route path='/v1/customer' exact={true} component={CustomerList}/>
          <Route path='/v1/account' exact={true} component={AccountList}/>
          <Route path='/v1/customer/update/:id' component={CustomerEdit}/>
          <Route path='/v1/customer/create' component={CreateCustomer}/>
          <Route path='/v1/account/create/:id' component={CreateAccount}/>
          <Route path='/v1/account/withdraw/:id' component ={AccountWithdraw}/>
          <Route path='/v1/account/deposit/:id' component = {AccountDeposit}/>
        </Switch>
      </Router>
    )
  }
}
export default App;
/*
function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
*/
/*
class App extends Component{
  state = {
    customer: {
      accountList: []
    }
  };
  async componentDidMount(){
    const response = await fetch('v1/customer/d7bb4f85-1604-4ca2-a1a9-cb5676ed89c6');
    const body = await response.json();
    this.setState({customer:body});
  }
  render(){
    const {customer} = this.state;
    return(
      <div className="App">
          <header className="App-header">
            <img src={logo} className="App-logo" alt="logo" />
            <div className="App-intro">
              <h2>Customer</h2>
                  <div key={customer.id}>
                    {customer.name} ({customer.dateOfBirth}) ({customer.city}) ({customer.address})
                    { customer.accountList.map(a => 
                      <p> Accounts: {a.id}, {a.balance}, {a.currency}</p> 
                      )}
                  </div>
            </div>
          </header>
        </div>
    );
  }
}
export default App;
*/
