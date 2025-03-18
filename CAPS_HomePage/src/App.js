// import logo from './logo.svg';
import './App.css';
import ControlledCarousel from './components/Carousel';
import 'bootstrap/dist/css/bootstrap.min.css';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Courses from './components/Courses'
import Button from 'react-bootstrap/Button';

// import logo from '../public/logo.png'

import React from "react";

function App() {

  return (
    
    <div className="App bg-grey">
      <Container>
      <div >
        <div><img className='nus-log p-4' src={require(`../src/logo.png`)} alt="logo" />|<img className='nus-log p-4' src={require(`../src/iss.png`)} alt="logo" /></div>
        <div className='mycontainer'>
          <img className ='myelement' alt=" " src={require(`../src/static/campus.jpg`)}/>
         
        </div>
        <h3 className='text-grey p-5'>Team 1 CAPS CA</h3>
        <Row>
          <Col className='bg-primary-subtle p-4 d-flex justify-content-center'xs lg="6">
            <div>
              <h3 className='text-grey p-2'>Our Staff</h3>
              <hr className='text-grey p-2'/>
            <ControlledCarousel/>
            </div>
          </Col>
          <Col className='bg-success-subtle p-4 text-dark'xs lg="6">
          <div>
              <h3 className='text-grey p-2'>Available Courses</h3>
              <hr className='text-grey p-2'/>
              <Courses/>
          </div>
            <Button className="btnLogin" variant="primary" size="lg" onClick={event =>  window.location.href='http://localhost:8080/'}>
              <i className="fa fa-sign-in" aria-hidden="true"></i>
          </Button>
          </Col>
        </Row>
        <h3 className='text-grey p-4'>Address</h3>
        <p className='text-grey p-2'>25 Heng Mui Keng Terrace, Institute of Systems Science, Singapore 119615</p>
        <div>            
          <div id="map-container-google-1" className="z-depth-1-half map-container">
            <iframe title=" Address " src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3988.8036845918436!2d103.77402507423855!3d1.2921869617600052!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31da1a56784202d9%3A0xc3162c3946c3da12!2sNUS-ISS!5e0!3m2!1sen!2ssg!4v1687346628058!5m2!1sen!2ssg" frameborder="0" allowfullscreen>
            </iframe>
          </div>
        </div>

      </div>
    </Container>
    </div>
  );
}


export default App;


