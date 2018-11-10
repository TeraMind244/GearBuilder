const loadingGif = '<div id="loading" ><img src="image/loading.gif" /></div>';
const domain = getDomain();

function getUrlParam(param) {
    var url = new URL(window.location.href);
    return url.searchParams.get(param);
}

function doAjaxGetText(method, url, callback) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            callback(this.responseText);
       }
    };
    xhttp.open(method, url, true);
    xhttp.send(); 
}

function doAjaxGetXML(method, url, callback) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            callback(this.responseXML);
       }
    };
    xhttp.open(method, url, true);
    xhttp.send(); 
}

function transform(xml, xsl) {
    var xslt = new XSLTProcessor();
    xslt.importStylesheet(xsl);
    
    return xslt.transformToFragment(xml, document);
}

function pushState(url) {
    history.pushState(null, null, url);
}

function getDomain() {
    var url = new URL(window.location.href);
    return url.origin + "/GearBuilder/";
}
