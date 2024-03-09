import React, {Component} from "react";
import './App.css';
import AppNavbar from "./AppNavbar";
import { Link } from "react-router-dom";
import {Button, Container} from 'reactstrap';

class Home extends Component{
    render(){
        return(
            <div>
                <AppNavbar/>
                <Container fluid>

                    <Button color="link">
                        <Link to="/v1/customer">Customers</Link>
                    </Button>
                    <Button color="link">
                        <Link to="/v1/account">Accounts</Link>
                    </Button>

                </Container>
            </div>
        );
    }
}

export default Home;