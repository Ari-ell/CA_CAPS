import { useState } from 'react';
import React, { useEffect } from 'react';
import Carousel from 'react-bootstrap/Carousel';
// import pic from '../static/darryl.tmb-.jpg'
import axios from 'axios';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
// import React from "react";
const baseUrl = "http://localhost:8080/caps";

const ControlledCarousel = () => {

  const [index, setIndex] = useState(0);
  const [lecturer, setLecturer] = useState([]);
  const imageExists = (imageName) => {
    try {
      require(`../static/${imageName}.jpg`);
      return true;
    } catch (error) {
      return false;
    }
  };

  const getImageSource = (imageName) => {
    if (imageExists(imageName)) {
      return require(`../static/${imageName}.jpg`);
    } else {
      return require('../static/404.png');
    }
  };
  useEffect(() => {
    fetchData();
  }, []);

  const handleSelect = (selectedIndex) => {
    setIndex(selectedIndex);
  };

  const fetchData = async () => {
    try {
      const response = await axios.get(baseUrl + '/lecturers');
      const data = response.data;
      setLecturer(data);
      console.log("====== lecturers are :", data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  return (
    <Carousel activeIndex={index} onSelect={handleSelect}>
      {lecturer.map((item, index) => (

        <Carousel.Item key={index}>
          <Row className="justify-content-md-center">
              <Col className='' xs lg="6">
                  <img className="d-block w-200" alt=" " src={getImageSource(item.firstName)}/>
              </Col>
              <Col className='p-4 d-flex justify-content-left'xs lg="6">
                <div>
                <h3>{item.firstName}</h3>
                <p>{item.description}</p>
                <p>{item.email}</p>
                </div>
              </Col>
            </Row>
        </Carousel.Item>

      ))}
    </Carousel>
  );
};

export default ControlledCarousel;