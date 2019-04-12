function sendAjaxGet(url, func) {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(this.readyState === 4 && this.status === 200)
            func(this);
    }


    xhr.open("GET", url);
    xhr.send();
}

sendAjaxGet("http://localhost:8080/ers/get_pending", display);

function display(xhr) {
    requests = JSON.parse(xhr.responseText).pending;

    table = document.getElementById("adminViewPendingTable");

    for(let i in requests) {
        let reqType = " ";
        let amount = (requests[i].amount).toString();
        let lastChar = amount.charAt(amount.length-2);
        let reqid = (requests[i].requestID);

        if (requests[i].typeID == 1) {
            reqType = "Lodging";
        } else if(requests[i].typeID == 2) {
            reqType = "Travel";
        } else if(requests[i].typeID == 3) {
            reqType = "Food";
        } else {
            reqType = "Other";
        }

        if(lastChar == '.') {
            amount = amount + "0";
        }


        newRow = document.createElement("tr");

        newRow.innerHTML =
            `<td>${reqid}</td>
            <td>${requests[i].employeeID}</td>
			<td>${amount}</td>
			<td>${requests[i].submitDate}</td>
			<td>${reqType}</td>
			<td>${requests[i].description}</td>
            <td><form action="update_request" method="POST"><button name="approve" type="submit" class="btn" value=${reqid}>Approve</button> <button name="deny" action="update_request" method="POST" type="submit" class="btn" value=${reqid} >Deny</button></form></td>`;

        table.appendChild(newRow);

    }
}