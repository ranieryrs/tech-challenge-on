$(document).ready(function () {
  init();
});
function loadData() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", 'http://localhost:8080/people', false );
    xmlHttp.send();
    return new Promise(resolve => {
        var jsonData=[];
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
            jsonData = JSON.parse(xmlHttp.responseText);
        }
        resolve(jsonData);
    });
}
function buildData(data){
    $('#tablePeople').DataTable().destroy();
    $("#tablePeople tbody").html('');
    for(var i=0;i<data.length;i++){
        var color ="";
        if(data[i]["active"]){
            color="green";
        }else{
            color="red";
        }
        $("#tablePeople tbody").append(
            "<tr id=\""+data[i]["id"]+ "\" style='background-color:"+color+"'>\n" +
            "                <td id=\"firstName\" ><input type=\"text\"  value=\""+data[i]["firstName"]+"\"></td>\n" +
            "                <td id=\"lastName\"><input type=\"text\" value=\""+data[i]["lastName"]+"\"></td>\n" +
            "                <td id=\"email\"><input type=\"text\"  value=\""+data[i]["email"]+"\"></td>\n" +
            "                <td id=\"lastLogin\">"+data[i]["lastLogin"]+"</td>\n" +
            "                <td><button class=\"delete\" width=\"100%\">Delete</button></td>\n" +
            "                <td><button class=\"edit\" width=\"100%\">Edit</button></td>\n" +
            "</tr>"
        )
    }
    $.fn.dataTableExt.ofnSearch['html-input'] = function(el) {
        return ""+$(el).val();
    };
    $('#tablePeople').DataTable({
        columnDefs: [
            { "type": "html-input", "targets": [0,1,2] }
        ],
        "aaSorting": []
    });
    $('.delete').click(function() {
        var id = $(this).parent('td').parent('tr').attr('id');
        deleteData(id);
    });
    $('.edit').click(function() {
        var id = $(this).parent('td').parent('tr').attr('id');
        var trElement =$(this).parent('td').parent('tr');
        var jsonData = {};
        jsonData["id"]= id;
        jsonData["firstName"]= trElement.children("#firstName").children("input").val();
        jsonData["lastName"]= trElement.children("#lastName").children("input").val();
        jsonData["email"]= trElement.children("#email").children("input").val();
        jsonData["lastLogin"]= trElement.children("#lastLogin").html();
        if(trElement.css( "background-color" )=="rgb(0, 128, 0)"){
            jsonData["active"]=true;
        }else{
            jsonData["active"]=false;
        }
        editData(id,jsonData);
    });

}
async function deleteData(id){
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "DELETE", 'http://localhost:8080/people/'+id, false );
    xmlHttp.send();
    return new Promise(resolve => {
        var status='false';
    if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
        status='true';
    }
    resetData();
    resolve(status);
});
}

async function editData(id,jsonData){
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "PUT", 'http://localhost:8080/people/'+id ,false);
    xmlHttp.send(JSON.stringify(jsonData));
    return new Promise(resolve => {
        var status='false';
    if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
        status='true';
    }
    resetData();
    resolve(status);
});
}
async function resetData(){
    var data = await loadData()
    buildData(data);
}
function init() {
    resetData();
}

