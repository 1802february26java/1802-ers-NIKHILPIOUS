window.onload = () => {
	document.getElementById("username").innerHTML = sessionStorage.getItem("empUsername");
	
	let data1 = sessionStorage.getItem("emp_idv");
	getAllreimbursements();
	
}

function getAllreimbursements() {
	let usr_id=sessionStorage.getItem("emp_idv");
   let xhr = new XMLHttpRequest();

xhr.onreadystatechange =()=>{
	if(xhr.readyState===XMLHttpRequest.DONE && xhr.status===200){
		let data= JSON.parse(xhr.responseText);
		console.log(data);
		presentreimbursements(data);
		
	}
};
xhr.open("GET",`getselectedemp.do?id=${usr_id}`);
xhr.send();
}



function presentreimbursements(data) {

     if(data){
    	var count=0;

        let tbody = document.getElementById("tableTVR");

        var count=0;


        data.forEach((reimbursement) => {
        	

        	let tr = document.createElement('tr');
        	
        	let countTd = document.createElement('td');
        	let dateTd = document.createElement('td');
        	let rdateTd = document.createElement('td');
        	let amountTd = document.createElement('td');
        	let statusTd = document.createElement('td');
        	let typeTd = document.createElement('td');
        	
            let countText= document.createTextNode(++count);          
            let dateText= document.createTextNode(`${reimbursement.requested.monthValue}-${reimbursement.requested.dayOfMonth}-${reimbursement.requested.year}`);

            
            if(`${reimbursement.status.status}`=="PENDING"){
            	var resdateText =document.createTextNode(" ---------------");
            	
            } 
            else{
            	resdateText= document.createTextNode(`${reimbursement.requested.monthValue}-${reimbursement.requested.dayOfMonth}-${reimbursement.requested.year}`);
            	
            	}
            let amountText= document.createTextNode(`${reimbursement.amount}`);
            let statusText= document.createTextNode(`${reimbursement.status.status}`);
            let typeText= document.createTextNode(`${reimbursement.type.type}`);
            
            
            
            let approvedbtn = document.createElement('input');
    		approvedbtn.type="button";
    		approvedbtn.className="btn btn-sm btn-success";
    		approvedbtn.value="APPROVE";
    		approvedbtn.onclick =(()=>{
    			let statusId=2;
    			let xhr = new XMLHttpRequest();
    			
    			xhr.onreadystatechange =()=>{
    				if(xhr.readyState===XMLHttpRequest.DONE && xhr.status===200){
    					let data= JSON.parse(xhr.responseText);

    					transactionMessage(data)
    				}
    			};
    			xhr.open("GET",`decision.do?reimbid=${reimbursement.id}&status_id=${statusId}`);
    			xhr.send();
    		})
    		
    		let declinedbtn = document.createElement('input');
    		declinedbtn.type="button";
    		declinedbtn.className="btn btn-sm btn-warning";
    		declinedbtn.value="DECLINE";
    		declinedbtn.onclick =(()=>{// same as (function(){
    			let statusId=3;
    			let xhr = new XMLHttpRequest();
    			
    			xhr.onreadystatechange =()=>{
    				if(xhr.readyState===XMLHttpRequest.DONE && xhr.status===200){
    					let data= JSON.parse(xhr.responseText);

    					transactionMessage(data)
    				}
    			};
    			xhr.open("GET",`decision.do?reimbid=${reimbursement.id}&status_id=${statusId}`);
    			xhr.send();
    		})
            let emptybtn = document.createElement('input');
    		emptybtn.type="button";
            
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
            if(`${reimbursement.status.status}`=="PENDING"){
            	tr.appendChild(approvedbtn);
            	tr.appendChild(declinedbtn);
            }


            
            tbody.appendChild(tr);
            

        });
    }
    else{}
     
}
function transactionMessage(data){
	if(data.message==="RESOLVED REQUEST UNSUCCESSFULT"){
		document.getElementById("resolveMessage").innerHTML = '<scope class="label label-danger label center"> Request wasnt able to resolve successfully ></scope>';
	}
	else 
		document.getElementById("resolveMessage").innerHTML = '<scope class="label label-success label center"> Request resolved successfully</scope>';	
}

