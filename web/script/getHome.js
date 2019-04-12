function sendAjaxGet(url, func) {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(this.readyState === 4 && this.status === 200)
            func(this);
    }


    xhr.open("GET", url);
    xhr.send();
}

sendAjaxGet("http://localhost:8080/ers/get_home", display);

function display(xhr) {
    requests = JSON.parse(xhr.responseText).data;

    head = document.getElementById("welcomeuser");
    head.innerHTML = "Welcome to the reimbursement system, " + requests.username;
    textOne = document.getElementById("pending");
    textOne.innerHTML = "You have " + requests.pending + " pending requests";
    textTwo = document.getElementById("resolved");
    textTwo.innerHTML = requests.resolved.toString() + " resolved requests";


}