import { useState } from 'react';
import { Row } from 'react-bootstrap';
import Col from 'react-bootstrap/Col';
import React, { useEffect } from 'react';
import axios from 'axios';
import Accordion from 'react-bootstrap/Accordion';
const baseUrl = "http://localhost:8080/caps";

function Courses() {

    const [courses, setCourses] = useState([]);
    useEffect(() => {
        fetchData();
      }, []);

    
      const fetchData = async () => {
        try {
          const response = await axios.get(baseUrl + '/courses');
          const data = response.data;
          setCourses(data);
          console.log("====== courses are :", data);
        } catch (error) {
          console.error("Error fetching data:", error);
        }
      };
    return (
        <Row>
            <Col className='' xs lg="12">
            <Accordion>
                {courses.map((item,index)=>(
                    <Accordion.Item eventKey={index} key={index}>
                    <Accordion.Header >
                        <div>                       
                         {index+1} &nbsp;{item.name}
                        </div>
                        </Accordion.Header>
                    <Accordion.Body>
                        <div >
                        <p className='d-flex justify-content-left'><b>Capacity Remaining:</b> &nbsp;  {item.capacity}</p>
                        <p className='d-flex justify-content-left'><b>Course Start at:  </b> &nbsp; {item.startDate}</p>
                    <p className='d-flex justify-content-left'><b>Description:  </b>&nbsp; {item.description}</p>
                    <p className='d-flex justify-content-left'><b>Course Code:  </b> &nbsp; {item.courseCode}</p>
                        </div>

                    </Accordion.Body>
                    </Accordion.Item>
                ))}
            </Accordion>
            </Col>
    
        </Row>

      );
}

export default Courses;
