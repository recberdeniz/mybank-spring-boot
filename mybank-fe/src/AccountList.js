import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import axios from 'axios';
class AccountList extends Component{

    constructor(props){
        super(props);
        this.state = {accounts: [], error: null,};
        this.remove = this.remove.bind(this);
    }

    componentDidMount(){
        axios.get('/v1/account')
        .then(response => this.setState({accounts: response.data}))
        .catch(error => this.setState({error: error.response.data}));
        
    }

    async remove(id){
        try {
            await axios.delete(`/v1/account/delete/${id}`);
            const updatedAccounts = this.state.accounts.filter(account => account.id !== id);
            this.setState({ accounts: updatedAccounts });
            this.props.history.push('/v1/account');
          } catch (error) {
            this.setState({ error: error.message });
          }
    }

    render(){
        const {accounts, error} = this.state;

        const accountList = accounts.map(account => {
            return <tr key={account.id}>
                <td style={{whiteSpace: 'nowrap'}}>{account.id}</td>
                <td>{account.balance}</td>
                <td>{account.currency}</td>
                <td className='text-center'>{account.customer.id}</td>
                <td className='text-center'>
                    <ButtonGroup>
                        <Button size='sm' color='primary' tag={Link} to={"/v1/account/deposit/" + account.id}>Deposit</Button>
                        <Button size='sm' color='secondary' tag={Link} to={"/v1/account/withdraw/" + account.id}>Withdraw</Button>
                        <Button size='sm' color='danger' onClick={() => this.remove(account.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid className='mt-3'>
                    <h3 className='ms-2'>Accounts</h3>
                    {error && (
                        <div className="alert alert-danger" role="alert">
                        {error}
                        </div>
                    )}
                    <Table responsive className='table-resposive mt-4'>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Balance</th>
                                <th>Currency</th>
                                <th className='text-center'>Account Holder</th>
                                <th className='text-center'>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {accountList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default AccountList;