var btnPrev = document.getElementById("btnPrev");
var btnNext = document.getElementById("btnNext");

function gotoPage(page) {
    var url = "?";
    if (txtGearName) {
        url += "txtGearName=" + txtGearName;
    }
    if (ddlType) {
        url += "ddlType=" + ddlType;
    }
    if (ddlSortBy) {
        url += "ddlSortBy=" + ddlSortBy;
    }
    location.href = "SearchServlet" + url + (url.length > 1 ? "&" : "") + "page=" + page;
}

function getUrlParam(param) {
    var url_string = window.location.href;
    var url = new URL(url_string);
    return url.searchParams.get(param);
}

function setUrlParam(param, value) {
    var url_string = window.location.href;
    var url = new URL(url_string);
    url.searchParams.set(param, value);
}
