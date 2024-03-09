import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Label, Input } from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios';

class AccountDeposit extends Component{

    constructor(props){
        super(props);
        this.state = {
            account: null,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount(){
        axios.get(`/v1/account/${this.props.match.params.id}`)
        .then(response => this.setState({account: response.data}))
        .catch(error => this.setState({error: error.response.data}));
    };

    handleChange(event){
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let account = {...this.state.account};
        account[name] = value;
        this.setState({account});
    };

    async handleSubmit(event){
        event.preventDefault();
        const {account} = this.state;
        try {
            await axios.put(`/v1/account/deposit/${account.id}`, account, {
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                },
            });
            this.props.history.push('/v1/account/');
        } catch (error) {
            this.setState({error: 'Amount must not be negative!'});
        }
    }

    render(){
        const {account, error} = this.state;
        const title = <h2 className='text-center'>Deposit</h2>

        return(
            <div>
                <AppNavbar/>
                {title}
                {error && (
                <div className="alert alert-danger" role="alert">
                    <strong>Error!</strong> {error}
                </div>
                )}
                <Container className='d-flex justify-content-center'>
                {account && (
                    <Form onSubmit={this.handleSubmit} className='text-center'>
                        <FormGroup>
                        <Label for='accountHolder'>Account Holder</Label>
                        <Input
                            type='text'
                            name='accountHolder'
                            id='accountHolder'
                            defaultValue={account.customer.name}
                            disabled
                        />
                        </FormGroup>
                        <br />
                        <FormGroup>
                        <Label for='amount'>Amount</Label>
                        <Input
                            type='number'
                            step={0.01}
                            name='amount'
                            onChange={this.handleChange}
                        />
                        </FormGroup>
                        <br />
                        <FormGroup>
                        <Label for='currency'>Currency</Label>
                        <Input
                            type='text'
                            name='currency'
                            defaultValue={account.currency}
                            disabled
                        />
                        </FormGroup>
                        <br />
                        <FormGroup>
                        <Button color='success' type="submit">Deposit</Button>
                        <Button className='ms-3' color="secondary" tag={Link} to="/v1/account">Cancel</Button>
                        </FormGroup>
                    </Form>
                )}
                </Container>
            </div>
        );
    }
}

export default withRouter(AccountDeposit);