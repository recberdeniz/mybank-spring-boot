import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import axios from 'axios';
class CustomerList extends Component{

    constructor(props){
        super(props);
        this.state = {customers: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount(){

        axios.get('/v1/customer')
        .then(response => this.setState({customers: response.data}))
        .catch(error => this.setState({error: error.response.data}));
    }

    async remove(id){
        try {
            await axios.delete(`/v1/customer/delete/${id}`);
            const updatedCustomers = this.state.customers.filter(customer => customer.id !== id);
            this.setState({ customers: updatedCustomers });
            this.props.history.push('/v1/customer');
          } catch (error) {
            this.setState({ error: error.message });
          }
    }

    render(){
        const {customers, error} = this.state;

        const customerList = customers.map(customer => {
            return <tr key={customer.id}>
                <td style={{whiteSpace: 'nowrap'}}>{customer.id}</td>
                <td>{customer.name}</td>
                <td>{customer.dateOfBirth}</td>
                <td>{customer.city}</td>
                <td>{customer.accountList.map((account) => account.currency).join(', ')}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/v1/customer/update/" + customer.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(customer.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
                <td>
                    <Button size='sm' color='success' tag={Link} to={"/v1/account/create/" + customer.id}>Create Account</Button>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid className='mt-3'>
                    <div className='float-end me-2'>
                        <Button color='success' tag={Link} to="/v1/customer/create">Add Customer</Button>
                    </div>
                    <h3 className='ms-2'>Customers</h3>
                    {error && (
                        <div className="alert alert-danger" role="alert">
                        {error}
                        </div>
                    )}
                    <Table responsive className='mt-4'>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Birth date</th>
                                <th>City</th>
                                <th>Accounts</th>
                                <th>Actions</th>
                                <th>Create</th>
                            </tr>
                        </thead>
                        <tbody>
                            {customerList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }

}

export default CustomerList;

