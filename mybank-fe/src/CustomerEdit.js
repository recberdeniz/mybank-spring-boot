import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios';

const cities = [
    {id: 1, name: "ANKARA"},
    {id: 2, name: "ISTANBUL"},
    {id: 3, name: "IZMIR"}
];
class CustomerEdit extends Component{
    
    constructor(props){
        super(props);
        this.state = {
            customer: null,
            error: null,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount(){
        try{
            const response = await(axios.get(`/v1/customer/${this.props.match.params.id}`));
            this.setState({customer: response.data});
        } catch(error){
            console.error(error);
            console.error(error.response.data);
            this.setState({error: error.response.data});
            
        }
    };

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let customer = {...this.state.customer};
        customer[name] = value;
        this.setState({customer});
    };

    async handleSubmit(event){
        event.preventDefault();
        const {customer} = this.state;
        axios.put(`/v1/customer/update/${customer.id}`, customer, {
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            },
        });
        this.props.history.push('/v1/customer/');
    }

    render(){
        const {customer, error} = this.state;
        const title = <h2 className='text-center'>Update Customer Information</h2>;
        console.log(error);
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
                {customer && (
                    <Form onSubmit={this.handleSubmit} className='text-center'>
                        <FormGroup>
                        <Label for="name">Name:</Label>
                        <Input
                            type="text"
                            name="name"
                            id='name'
                            defaultValue={customer.name}
                            disabled
                        />
                        </FormGroup>
                        <br />
                        <FormGroup>
                        <Label for="dateOfBirth">Birth Year:</Label>
                        <Input
                            type="number"
                            name="dateOfBirth"
                            defaultValue={customer.dateOfBirth}
                            disabled
                        />
                        </FormGroup>
                        <br />
                        <FormGroup>
                        <Label for="city">City:</Label>
                        <select name="city" defaultValue={customer.city} onChange={this.handleChange}>
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
                        <Button color='success' type="submit">Update</Button>
                        <Button className='ms-3' color="secondary" tag={Link} to="/v1/customer">Cancel</Button>
                        </FormGroup>
                    </Form>
                )}
                </Container>
            </div>
        );
    }
}

export default withRouter(CustomerEdit);
