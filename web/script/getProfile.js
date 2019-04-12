function sendAjaxGet(url, func) {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(this.readyState === 4 && this.status === 200)
            func(this);
    }


    xhr.open("GET", url);
    xhr.send();
}

sendAjaxGet("http://localhost:8080/ers/get_profile", display);

function display(xhr) {
    requests = JSON.parse(xhr.responseText).employee;

    fnInput = document.getElementById("form_firstname");
    lnInput = document.getElementById("form_lastname");
    username = document.getElementById("form_username");
    email = document.getElementById("form_useremail");
    fnInput.value = requests.firstName;
    lnInput.value = requests.lastName;
    username.innerHTML = requests.username;
    email.innerHTML = requests.email;

}