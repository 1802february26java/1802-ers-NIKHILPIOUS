window.onload = () => {

    getAllemployees();
}

function getAllemployees() {
	document.getElementById("username").innerHTML = sessionStorage.getItem("empUsername");

    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
 
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
  
            let data = JSON.parse(xhr.responseText);
            console.log(data);

            presentemployees(data);
        }
    };
    xhr.open("GET",`resolveemp.do?reqpor_id=2`);


    xhr.send();
}

function presentemployees(data) {

    if(data.message) {
        document.getElementById("listMessage").innerHTML = '<span class="label label-danger label-center">Something went wrong.</span>';
    }

    else {
    	var count=0;

        let tbody = document.getElementById("tableTE");


        var count=0;

 
        data.forEach((employee) => {
        	

        	let tr = document.createElement('tr');
        	
        	let countTd = document.createElement('td');
        	let dateTd = document.createElement('td');
        	let rdateTd = document.createElement('td');
        	let amountTd = document.createElement('td');
        	let statusTd = document.createElement('td');
        	let typeTd = document.createElement('td');
        	
            let countText= document.createTextNode(++count);          
            let dateText= document.createTextNode(`${employee.requested.monthValue}-${employee.requested.dayOfMonth}-${employee.requested.year}`); 
            let resdateText= document.createTextNode(`${employee.requested.monthValue}-${employee.requested.dayOfMonth}-${employee.requested.year}`); 
            let amountText= document.createTextNode(`${employee.amount}`);
            let statusText= document.createTextNode(`${employee.status.status}`);
            let typeText= document.createTextNode(`${employee.type.type}`);
            
            
            countTd.appendChild(countText);
            dateTd.appendChild(dateText);
            rdateTd.appendChild(resdateText);
            amountTd.appendChild(amountText);
            statusTd.appendChild(statusText);
            typeTd.appendChild(typeText);
            
            tr.appendChild(countTd);
            tr.appendChild(dateTd);
            tr.appendChild(rdateTd);
            tr.appendChild(amountTd);
            tr.appendChild(statusTd);
            tr.appendChild(typeTd);
            
            tbody.appendChild(tr);

        });
    }
}