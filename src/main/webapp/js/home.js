window.onload = () => {
	console.log(sessionStorage.getItem("empUsername"));
    document.getElementById("username").innerHTML = sessionStorage.getItem("empUsername");
}