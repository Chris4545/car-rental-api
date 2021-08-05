
url = "http://localhost:8082/car-rental-api/rentals";

window.onload = function(){


    var rental = [];
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url)
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            JSON.parse(xhr.responseText).forEach(element => {
                rental.push(element);
                updateTable(element);
            })
        }
    }
    xhr.send();

    //post
    document.getElementById("new-rental").addEventListener("submit", function(e){
        e.preventDefault();
        var rent = {make:"",
                model:"",
                rentDate:"",
                returnDate:"",
                rentPlace:"",
                returnPlace:""
                }
        
        rent.make = document.getElementById("make").value;
        rent.model = document.getElementById("model").value;
        rent.rentDate = document.getElementById("rent-date").value;
        rent.returnDate = document.getElementById("return-date").value;
        rent.rentPlace = document.getElementById("rent-place").value;
        rent.returnPlace = document.getElementById("return-place").value;
        var xhrPost = new XMLHttpRequest();
        xhrPost.open("POST", url)
        xhrPost.onreadystatechange = function(){
            
            if(xhrPost.readyState === 4 && xhrPost.status < 400){
                rent = JSON.parse(xhrPost.response);
                updateTable(rent);
                alert("Successfully added rental");
            }else if(xhrPost.readyState === 4){
                alert("There was an error adding your rental");
            }
        }
        xhrPost.send(JSON.stringify(rent));
    });
}

//update
updateTable = function(rental){
        var row = document.createElement("TR");
        var idCol = document.createElement("TH");
        var makeCol = document.createElement("TD");
        var modelCol = document.createElement("TD");
        var rentDateCol = document.createElement("TD");
        var returnDateCol = document.createElement("TD");
        var rentPlaceCol = document.createElement("TD");
        var returnPlaceCol = document.createElement("TD");
        var deleteCol = document.createElement("TD");
        var editCol = document.createElement("TD");

        //buttons
        var editButton = document.createElement("button");
        var deleteButton = document.createElement("button");
        editButton.innerHTML = "Edit";
        editButton.setAttribute("class", "btn btn-secondary");
        editCol.appendChild(editButton);
        deleteButton.innerHTML = "Delete";
        editButton.setAttribute("onclick", `editMode(${rental.id})`);
        deleteButton.setAttribute("class", "btn btn-secondary");
        deleteCol.appendChild(deleteButton);
        deleteButton.setAttribute("onclick", `deleteItem(${rental.id})`);

        //bootstrap/css stuff
        idCol.innerText = rental.id;
        idCol.setAttribute("scope", "row");

        makeCol.innerText = rental.make;
        modelCol.innerText = rental.model;
        rentDateCol.innerText = rental.rentDate;
        returnDateCol.innerText = rental.returnDate;
        rentPlaceCol.innerText = rental.rentPlace;
        returnPlaceCol.innerText = rental.returnPlace;

        //delete and edit stuff
        row.setAttribute("id", `${rental.id}a`);
        makeCol.setAttribute("id", `${rental.id}b`);
        modelCol.setAttribute("id", `${rental.id}c`);
        rentDateCol.setAttribute("id", `${rental.id}d`);
        returnDateCol.setAttribute("id", `${rental.id}e`);
        rentPlaceCol.setAttribute("id", `${rental.id}f`);
        returnPlaceCol.setAttribute("id", `${rental.id}g`);
        idCol.setAttribute("id", `${rental.id}edit`);





        row.appendChild(idCol);
        row.appendChild(makeCol);
        row.appendChild(modelCol);
        row.appendChild(rentDateCol);
        row.appendChild(returnDateCol);
        row.appendChild(rentPlaceCol);
        row.appendChild(returnPlaceCol);
        row.appendChild(deleteCol);
        row.appendChild(editCol);
        
        document.getElementById("tbody").appendChild(row);
    }
    //delete
deleteItem = function(id){
        console.log(id);
        var xhrDelete = new XMLHttpRequest();
        xhrDelete.open("DELETE", url)
        xhrDelete.send(JSON.stringify(id));
        xhrDelete.onreadystatechange = function(){
            if(xhrDelete.readyState === 4 && xhrDelete.status < 400){
                console.log(xhrDelete.readyState);
                console.log(id + "a")
                let body = document.getElementById("tbody");
                let row = document.getElementById(id + "a");
                body.removeChild(row);
            }else if(xhrDelete.readyState === 4){
                alert("There was an error deleting");
            }
        }
    }

    //edit
editItem = function(id){
        
        var rent = {
                id:"",
                make:"",
                model:"",
                rentDate:"",
                returnDate:"",
                rentPlace:"",
                returnPlace:""
                }
        rent.id=id;
        rent.make = document.getElementById(`inIdb${id}`).value;
        rent.model = document.getElementById(`inIdc${id}`).value;
        rent.rentDate = document.getElementById(`inIdd${id}`).value;
        rent.returnDate = document.getElementById(`inIde${id}`).value;
        rent.rentPlace = document.getElementById(`inIdf${id}`).value;
        rent.returnPlace = document.getElementById(`inIdg${id}`).value;

        var xhrEdit = new XMLHttpRequest();
        xhrEdit.open("PUT", url)
        xhrEdit.send(JSON.stringify(rent));
        xhrEdit.onreadystatechange = function(){
            if(xhrEdit.readyState === 4 && xhrEdit.status == 201){
                rent = JSON.parse(xhrEdit.response);
                let submitButton = document.getElementById(`submitButton${id}`);
                submitButton.parentElement.removeChild(submitButton);
                let aToG = ['b','c','d','e','f','g'];
                console.log(rent);
                aToG.forEach(function(letter, index, array){
                    editObj = document.getElementById(id + letter);
                    switch(letter){
                        case 'b': editObj.innerHTML = rent.make;
                        break;
                        case 'c': editObj.innerHTML = rent.model;
                        break;
                        case 'd': editObj.innerHTML = rent.rentDate;
                        break;
                        case 'e': editObj.innerHTML = rent.returnDate;
                        break;
                        case 'f': editObj.innerHTML = rent.rentPlace;
                        break;
                        case 'g': editObj.innerHTML = rent.returnPlace;
                
                    }

                })

            }
        }
    }
    //Edit mode (enable editing on row)
    editMode = function(id){
        if(document.getElementById(`submitButton${id}`) == null)
        {
        let aToG = ['b','c','d','e','f','g'];
        aToG.forEach(function(letter, index, array){
            editObj = document.getElementById(id + letter);
            let input = document.createElement("input");
            input.setAttribute("id", `inId${letter}${id}`);
            input.defaultValue = editObj.innerHTML;
            editObj.innerHTML = "";
            editObj.appendChild(input);
        })
        submitCol = document.getElementById(id+"edit");
        submitButton = document.createElement("button");
        submitButton.setAttribute("id", `submitButton${id}`);
        submitButton.innerHTML = "submit";
        submitButton.setAttribute("class", "btn btn-primary");
        submitButton.setAttribute("onclick", `editItem(${id})`);
        submitCol.appendChild(submitButton);
        }else{
            console.log("already in edit mode");
        }
    }

    //search specific id
    searchByID = function(){
        var id = document.getElementById("rentalID").value;
        console.log(id);
        var getXHR = new XMLHttpRequest();
        var url01 = url+ "?" + `id=${id}`;
        getXHR.open("GET", url01);
        getXHR.send();
        getXHR.onreadystatechange = function(){
            if(getXHR.readyState == 4 && getXHR.status < 400){
                clearTable();
                rent = JSON.parse(getXHR.response)[0];
                updateTable(rent);
                createAlert("Successfully Found!", "success");
            }
            else if(getXHR.readyState == 4){
                createAlert("There was an error", "danger");

            }
        }
    }
    //clear the table
   clearTable = function(){
        tableBody = document.getElementById("tbody");
        tableBody.innerHTML = "";
    }

    //alerts
createAlert = function(msg, type){

        switch(type){
            case "success":
                alert(msg);
            break;
            case "danger":{
                alert(msg);
            break;
            }
        }

    }


