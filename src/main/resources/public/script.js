function deleteField(a) {
     var contDiv = a.parentNode;
     contDiv.parentNode.removeChild(contDiv);
     return false;
}

function addField(fieldName, link) {
     var div = document.createElement("div");
     div.innerHTML = '<input name=' + fieldName + ' type="text" /> <a onclick="return deleteField(this)" href="#">[X]</a>';
     document.getElementById(fieldName).insertBefore(div, link);
     return false;
}
