import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios';

const cities = [
    {id: 1, name: "Please select a city"},
    {id: 2, name: "ANKARA"},
    {id: 3, name: "ISTANBUL"},
    {id: 4, name: "IZMIR"}
];

class CreateCustomer extends Component{

    constructor(props){
        super(props);
        this.state = {
            customer: {
                name: '',
                dateOfBirth: null,
                city: '',
                address: '',
            },
            error: null,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event){
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let customer = {...this.state.customer};
        customer[name] = value;
        this.setState({customer});
    }

    async handleSubmit(event){
        event.preventDefault();
        const {customer} = this.state;
        try{
            await axios.post((`/v1/customer/create`), customer);
            this.props.history.push('/v1/customer');
        } catch(error){
            this.setState({error: error.response.data});
            console.error(error);
            console.log(typeof(error));
        }
    }

    render(){

        const {customer, error} = this.state;
        const title = <h1 className='text-center'>Create New Customer</h1>

        return(
            <div>
                <AppNavbar/>
                {title}
                <Container className='d-flex justify-content-center'>
                {error && (
                    <div className="alert alert-danger">
                    {"Error! " + error.message}
                    </div>
                )}
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for='name'>Name:</Label>
                            <Input
                                type='text'
                                name='name'
                                id='name'
                                value={customer.name}
                                onChange={this.handleChange}
                                required
                            />
                        </FormGroup>
                        <br />
                        <FormGroup>
                            <Label for='dateOfBirth'>Birth Year:</Label>
                            <Input
                                type='number'
                                name='dateOfBirth'
                                value={customer.dateOfBirth}
                                onChange={this.handleChange}
                                required
                            />
                        </FormGroup>
                        <br />
                        <FormGroup>
                        <Label for="city">City:</Label>
                        <select name="city" defaultValue={cities[0]} onChange={this.handleChange}>
                            {cities.map((city) => (
                                <option key={city.id} value={city.name}>
                                    {city.name}
                                </option>
                            ))}
                        </select>
                        </FormGroup>
                        <br />
                        <FormGroup>
                        <Label htmlFor="address">Address:</Label>
                        <Input
                            name="address"
                            defaultValue={customer.address}
                            onChange={this.handleChange}
                            required
                        />
                        </FormGroup>
                        <br />
                        <FormGroup>
                        <Button color='success' type="submit">Create</Button>
                        <Button className='ms-3' color="secondary" tag={Link} to="/v1/customer">Cancel</Button>
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }
}

export default withRouter(CreateCustomer);