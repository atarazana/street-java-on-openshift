import React from 'react'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Alert from 'react-bootstrap/Alert';

const Config = ({ config }) => {
    return (
        <Container fluid>
            <Row>
                <Col>
                    <Alert variant={config.status.status === 'OPERATIONAL' ? 'success' : 'danger'}>
                    <Alert.Heading><span class="fw-bold">Gateway</span> '{config.name}' <span class="fw-bold">status</span> {config.status.status}</Alert.Heading>
                    </Alert>
                </Col>
            </Row>
            <Row>
                <Col><h2>Backend Services</h2></Col>
            </Row>
            {config.status.checks.map((check) => (
            <Row>
                <Col>
                    <Alert variant={check.status === 'UP' ? 'success' : 'danger'}>
                    <Alert.Heading><span class="fw-bold">Service</span> '{check.name}' <span class="fw-bold">status</span> {check.status}</Alert.Heading>
                    </Alert>
                </Col>
           </Row>
            ))}
        </Container>
    )
};

export default Config