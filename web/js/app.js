
function getUrlParam(param) {
    var url_string = window.location.href;
    var url = new URL(url_string);
    return url.searchParams.get(param);
}

function doAjax(method, url, callback) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            callback(this.responseText);
       }
    };
    xhttp.open(method, url, true);
    xhttp.send(); 
}
