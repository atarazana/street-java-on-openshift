import React, {Component, useEffect} from 'react';
import Fruits from './components/fruits'
import Config from './components/config'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

const DEFAULT_STATE = {
  fruits: [],
  config: {
    name: "NO NAME",
    status: {
      status: "UNKNOWN",
      checks: []
    }
  }
}

class App extends Component {
  intervalId;
  constructor(props) {
    super(props)
    this.state = DEFAULT_STATE
  }

  reset = () => {
    this.setState(DEFAULT_STATE)
  }

  fetchData() {
    fetch('/api/fruits')
    .then(res => res.json())
    .then((data) => {
      this.setState({ fruits: data })
    })
    .catch(console.log)

    fetch('/api/config')
    .then(res => res.json())
    .then((data) => {
      this.setState({ config: data })
    })
    .catch(console.log)
  }

  componentDidMount() {
    this.fetchData()
    this.intervalId = setInterval(() => {
      this.fetchData()
    }, 2000);
  }

  componentWillUnmount(){
    clearInterval(this.intervalId);
  }

  render () {
    return (
      <Container fluid>
        <Row>
          <Col><h1>Street Java Status</h1></Col>
        </Row>
        <Row>
          <Col><Config config={this.state.config} /></Col>
        </Row>
        <Row>
          <Col><Fruits fruits={this.state.fruits} /></Col>
        </Row>
      </Container>
    );
  }
}

export default App;