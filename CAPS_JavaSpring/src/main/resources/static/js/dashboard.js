
console.log(courses);
console.log(enrollmentList);
console.log(users);
var myusers = [];
var mylables =[];
var mycredits=[];
var mycapacity=[];
var admin =0;
var student = 0;
var lecturer = 0;
var others = 0;
for(let i = 0;i<courses.length;i++){
    mylables.push(courses[i].name);
    mycredits.push(courses[i].credits);
    mycapacity.push(courses[i].capacity);
}

for(let i = 0;i<users.length;i++){
    if(users[i].role=='Admin'){
        admin+=1;
    }
    else if(users[i].role=='Student'){
        student+=1;
    }
    else if(users[i].role=='Lecturer'){
        lecturer+=1;
    }
    // else{
    //      others+=1;
    // }
}

var ctx = document.getElementById("myChart1").getContext('2d');
  var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: mylables,
      datasets: [{
        label: 'No. of Credits by Course',
        data: mycredits,
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(255, 159, 64, 0.2)'
        ],
        borderColor: [
          'rgba(255,99,132,1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true
          }
        }]
      }
    }
  });
//doughnut
  var ctxD = document.getElementById("myChart2").getContext('2d');
  var myLineChart = new Chart(ctxD, {
    type: 'doughnut',
    data: {
      labels: ["Admin", "Student", "Lecturer"],
      datasets: [{
        data: [admin, student, lecturer, others],
        backgroundColor: ["#FFA7D8", "#FFBF8F", "#F9F871"],
        hoverBackgroundColor: ["#FFA7D8", "#FFBF8F", "#F9F871"]
      }]
    },
    options: {
      responsive: true
    }
  });
  var ctxL = document.getElementById("myChart3").getContext('2d');
  var myLineChart = new Chart(ctxL, {
    type: 'line',
    data: {
      labels: mylables,
      datasets: [{
        label: "Credits",
        data: mycredits,
        backgroundColor: [
          'rgba(105, 0, 132, .2)',
        ],
        borderColor: [
          'rgba(200, 99, 132, .7)',
        ],
        borderWidth: 2
      },
      {
        label: "Capacity",
        data: mycapacity,
        backgroundColor: [
          'rgba(0, 137, 132, .2)',
        ],
        borderColor: [
          'rgba(0, 10, 130, .7)',
        ],
        borderWidth: 2
      }
      ]
    },
    options: {
      responsive: true
    }
  });

  new Chart(document.getElementById("myChart4"), {
      "type": "horizontalBar",
      "data": {
        "labels": mylables,
        "datasets": [{
          "label": "No. of Enrollments by Course",
          "data": mycapacity,
          "fill": false,
          "backgroundColor": ["rgba(255, 99, 132, 0.2)", "rgba(255, 159, 64, 0.2)",
            "rgba(255, 205, 86, 0.2)", "rgba(75, 192, 192, 0.2)", "rgba(54, 162, 235, 0.2)",
            "rgba(153, 102, 255, 0.2)", "rgba(201, 203, 207, 0.2)"
          ],
          "borderColor": ["rgb(255, 99, 132)", "rgb(255, 159, 64)", "rgb(255, 205, 86)",
            "rgb(75, 192, 192)", "rgb(54, 162, 235)", "rgb(153, 102, 255)", "rgb(201, 203, 207)"
          ],
          "borderWidth": 1
        }]
      },
      "options": {
        "scales": {
          "xAxes": [{
            "ticks": {
              "beginAtZero": true
            }
          }]
        }
      }
    });
