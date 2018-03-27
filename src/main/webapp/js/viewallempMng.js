window.onload = () => {
//	 document.getElementById("username").innerHTML = sessionStorage.getItem("employeeUsername");
	
    //Get Event Listener
//    document.getElementById("getPending").addEventListener("click", getAllemployees);

    //Get all employees as soon as the page loads
    getAllemployees();
}

function getAllemployees() {
    //AJAX Logic
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
        //If the request is DONE (4), and everything is OK
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //Getting JSON from response body
            let data = JSON.parse(xhr.responseText);
            console.log(data);

            //Present the data to the user
            presentemployees(data);
        }
    };

    //Doing a HTTP to a specific endpoint
    xhr.open("GET",`viewprofileAllmngr.do?`);

    //Sending our request
    xhr.send();
}

function presentemployees(data) {
    //If message is a member of the JSON, something went wrong
    if(data.message) {
        document.getElementById("listMessage").innerHTML = '<span class="label label-danger label-center">Something went wrong.</span>';
    }
    //We present all the employees to the user
    else {
    	var count=0;
        //Get employee list node
        let tbody = document.getElementById("tableTVA");

        //Clean employee list
        var count=0;

        //Iterate over all employees
        data.forEach((employee) => {
        	
            //Creating node of <li>
        	let tr = document.createElement('tr');
        	
        	let countTd = document.createElement('td');
        	let uidTd = document.createElement('td');
        	let firstTd = document.createElement('td');
        	let lastTd = document.createElement('td');
        	let unameTd = document.createElement('td');
        	let emailTd = document.createElement('td');
        	
        	
            let countText= document.createTextNode(++count);          
            let uidText= document.createTextNode(`${employee.requested.monthValue}-${employee.requested.dayOfMonth}-${employee.requested.year}`); 
            let firstText= document.createTextNode(`${employee.requested.monthValue}-${employee.requested.dayOfMonth}-${employee.requested.year}`); 
            let lastText= document.createTextNode(`${employee.amount}`);
            let unameText= document.createTextNode(`${employee.status.status}`);
            let emailText= document.createTextNode(`${employee.type.type}`);
            
            
            countTd.appendChild(countText);
            uidTd.appendChild(uidText);
            firstTd.appendChild(firstText);
            lastTd.appendChild(lastText);
            unameTd.appendChild(unameText);
            emailTd.appendChild(emailText);
            
            tr.appendChild(countTd);
            tr.appendChild(uidTd);
            tr.appendChild(firstTd);
            tr.appendChild(lastTd);
            tr.appendChild(unameTd);
            tr.appendChild(emailTd);
            
            tbody.appendChild(tr);
            
//            let statusTd= document.getElementById("status");
//            employeeList.innerHTML = `$employee.amount`;
//            let typeTd= document.getElementById("type");
//            employeeList.innerHTML = "";
        });
    }
}