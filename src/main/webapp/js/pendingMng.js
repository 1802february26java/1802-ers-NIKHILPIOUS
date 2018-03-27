window.onload = () => {
//	 document.getElementById("username").innerHTML = sessionStorage.getItem("customerUsername");
	
    //Get Event Listener
//    document.getElementById("getPending").addEventListener("click", getAllCustomers);

    //Get all customers as soon as the page loads
    getAllCustomers();
}

function getAllCustomers() {
    //AJAX Logic
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
        //If the request is DONE (4), and everything is OK
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //Getting JSON from response body
            let data = JSON.parse(xhr.responseText);
            console.log(data);

            //Present the data to the user
            presentCustomers(data);
        }
    };

    //Doing a HTTP to a specific endpoint
    xhr.open("GET",`pendingmngr.do?id=1`);

    //Sending our request
    xhr.send();
}

function presentCustomers(data) {
    //If message is a member of the JSON, something went wrong
    if(data.message) {
        document.getElementById("listMessage").innerHTML = '<span class="label label-danger label-center">Something went wrong.</span>';
    }
    //We present all the customers to the user
    else {
    	var count=0;
        //Get customer list node
        let tbody = document.getElementById("tableTP");

        //Clean customer list
        var count=0;

        //Iterate over all customers
        data.forEach((customer) => {
        	
            //Creating node of <li>
        	let tr = document.createElement('tr');
        	
        	let countTd = document.createElement('td');
        	let dateTd = document.createElement('td');
        	let amountTd = document.createElement('td');
        	let statusTd = document.createElement('td');
        	let typeTd = document.createElement('td');
        	
            let countText= document.createTextNode(++count);          
            let dateText= document.createTextNode(`${customer.requested.monthValue}-${customer.requested.dayOfMonth}-${customer.requested.year}`); 
            let amountText= document.createTextNode(`${customer.amount}`);
            let statusText= document.createTextNode(`${customer.status.status}`);
            let typeText= document.createTextNode(`${customer.type.type}`);
            
            
            countTd.appendChild(countText);
            dateTd.appendChild(dateText);
            amountTd.appendChild(amountText);
            statusTd.appendChild(statusText);
            typeTd.appendChild(typeText);
            
            tr.appendChild(countTd);
            tr.appendChild(dateTd);
            tr.appendChild(amountTd);
            tr.appendChild(statusTd);
            tr.appendChild(typeTd);
            
            tbody.appendChild(tr);
            
//            let statusTd= document.getElementById("status");
//            customerList.innerHTML = `$customer.amount`;
//            let typeTd= document.getElementById("type");
//            customerList.innerHTML = "";
        });
    }
}