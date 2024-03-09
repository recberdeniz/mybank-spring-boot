import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios';

const currencies = [
    {id:1, name: 'Please select currency'},
    {id:2, name: 'EUR'},
    {id:3, name: 'TRY'},
    {id:4, name: 'USD'}
];

class CreateAccount extends Component{
    
    constructor(props){
        super(props);
        this.state = {
            account: {
                currency: '',
                error: null,
            },
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event){
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let account = {...this.state.account};
        account[name] = value;
        this.setState({account});
    }

    async handleSubmit(event){
        event.preventDefault();
        const {account} = this.state;
        const customerId = this.props.match.params.id;
        try {
            await axios.post(`/v1/account/create/${customerId}`, account);
            this.props.history.push('/v1/account');
          } catch (error) {
            this.setState({error: error.response.data});
          }
    }

    render() {
        const title = <h1 className="text-center">Create New Account</h1>;
        const { error } = this.state;
    
        return (
          <div>
            <AppNavbar />
            {error ? (
              <div className="alert alert-danger" role="alert">
                <strong>Error!</strong> {error}
              </div>
            ) : (
              <>
                {title}
                <Container className="d-flex justify-content-center">
                  <Form onSubmit={this.handleSubmit} className="test-center">
                    <FormGroup>
                      <Label for="currency">Currency:</Label>
                      <select
                        name="currency"
                        defaultValue={currencies[0].name}
                        onChange={this.handleChange}
                      >
                        {currencies.map((currency) => (
                          <option key={currency.id} value={currency.name}>
                            {currency.name}
                          </option>
                        ))}
                      </select>
                    </FormGroup>
                    <br />
                    <FormGroup>
                      <Button className="ms-5" color="success" type="submit">
                        Create
                      </Button>
                      <Button
                        className="ms-5"
                        color="secondary"
                        tag={Link}
                        to="/v1/customer"
                      >
                        Cancel
                      </Button>
                    </FormGroup>
                  </Form>
                </Container>
              </>
            )}
          </div>
        );
      }
        
    }



export default withRouter(CreateAccount);