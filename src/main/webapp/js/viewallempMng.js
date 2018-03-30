window.onload = () => {
	document.getElementById("username").innerHTML = sessionStorage.getItem("empUsername");

    getAllemployees();
}

function getAllemployees() {

    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {

        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            console.log(data);

            presentemployees(data);
        }
    };

    xhr.open("GET",`viewprofileAllmngr.do?`);

    xhr.send();
}

function presentemployees(data) {
    if(data.message) {
        document.getElementById("listMessage").innerHTML = '<span class="label label-danger label-center">Something went wrong.</span>';
    }
    else {
    	var count=0;
        let tbody = document.getElementById("tableTVA");

        var count=0;

        data.forEach((employee) => {
        	
        	let tr = document.createElement('tr');
        	
        	let countTd = document.createElement('td');
    		let idTd = document.createElement('td');
    		let firstTd = document.createElement('td');
    		let lastTd = document.createElement('td');
    		let usernTd = document.createElement('td');
    		let emailTd = document.createElement('td');
    		let buttonTd = document.createElement('td');
    		
    		
    		let countText = document.createTextNode(count++);
    		let userName = document.createTextNode(`${employee.username}`);
    		let uid= document.createTextNode(`${employee.id}`);
    		let firstName=document.createTextNode(`${employee.firstName}`);
    		let lastName=document.createTextNode(`${employee.lastName}`);
    		let email = document.createTextNode(`${employee.email}` );
    		
    		let selectbtn = document.createElement('input');
    		selectbtn.type="button";
    		selectbtn.className="btn btn-success";
    		selectbtn.value="SELECT";
    		selectbtn.onclick =(()=>{
    			sessionStorage.setItem("emp_idv",`${employee.id}`);
				window.location.replace("decisionview.do");

//    			
//    			xhr.onreadystatechange =()=>{
//    				if(xhr.readyState===XMLHttpRequest.DONE && xhr.status===200){
//    					let data= JSON.parse(xhr.responseText);
//    					console.log(data);
//    					
//    				}
//    			};
//    			xhr.open("GET",`getselectedemp.do?id=${employee.id}`);
    			//xhr.send();
    		})
    		

    		countTd.appendChild(countText);
    		idTd.appendChild(uid);
    		firstTd.appendChild(firstName);
    		lastTd.appendChild(lastName);
    		usernTd.appendChild(userName);
    		emailTd.appendChild(email);
    		buttonTd.appendChild(selectbtn);
    		
    		tr.appendChild(countTd);
    		tr.appendChild(idTd);
    		tr.appendChild(firstTd);
    		tr.appendChild(lastTd);
    		tr.appendChild(usernTd);
    		tr.appendChild(emailTd);
    		tr.appendChild(buttonTd);
    		
    		tbody.appendChild(tr);
        	
        

        });
    }
}