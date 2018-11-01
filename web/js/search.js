var btnPrev = document.getElementById("btnPrev");
var btnNext = document.getElementById("btnNext");

function getAllParamSearch() {
    var txtName = document.getElementsByName("txtGearName")[0].value.trim();
    var ddlType = document.getElementsByName("ddlType")[0].value;
    var ddlSortBy = document.getElementsByName("ddlSortBy")[0].value;
    return "?txtGearName=" + txtName + "&ddlType=" + ddlType + "&ddlSortBy=" + ddlSortBy;
}

function gotoPage(page) {
    location.href = "SearchServlet" + getAllParamSearch() + "&page=" + page;
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
