import React from 'react'
import Table from 'react-bootstrap/Table';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Alert from 'react-bootstrap/Alert';

const Fruits = ({ fruits }) => {
    return (
        <Container fluid>
        <Row>
            <Col><h2>Fruits</h2></Col>
        </Row>
        <Row>
            <Col>
            {fruits.length > 0 ? (
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                    </tr>
                </thead>
                <tbody >
                {fruits.map((event) => (
                    <tr key={event.id} role="row">
                        <td>{event.id}</td>
                        <td>{event.name}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
            ) : 
            (
            <Alert variant='danger'>
            No fruits!
            </Alert>
            )}
        </Col>
        </Row>
        </Container>
    )
};

export default Fruits