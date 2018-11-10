const loading = document.getElementById("loading");

const txtGearName = document.getElementById("txtGearName");
const ddlType = document.getElementById("ddlType");
const ddlSortBy = document.getElementById("ddlSortBy");

const gears = document.getElementById("gears");

const searchServiceUrl = "/GearBuilder/resource/search/";

(function() {
    doAjaxGetXML("GET", searchServiceUrl + "xsl?xslFilePath=xsl/searchGearView.xsl", function (returnedXML) {
        xsl = returnedXML;
        getGears(null);
    });
})();

function getGears(params) {
    gears.innerHTML = loadingGif;
    doAjaxGetXML("GET", searchServiceUrl + getSearchUrl(params), function (returnedXML) {
        xml = returnedXML;
        gears.innerHTML = "";
        gears.appendChild(transform(xml, xsl));
    });
}

function getSearchUrl(params) {
    var param = "?page=" + currentPage;
    if (!params) {
        params = searchData;
    } 
    if (params.txtGearName) {
        param += "&txtGearName=" + params.txtGearName;
    }
    if (params.ddlType) {
        param += "&ddlType=" + params.ddlType;
    }
    if (params.ddlSortBy) {
        param += "&ddlSortBy=" + params.ddlSortBy;
    }
    return "search" + param;
}

function gotoPage(page) {
    currentPage = page;
    getGears(null);
}

function searchGears() {
    currentPage = 0;
    getGears(getSearchParams());
}

function search() {
    if (event.keyCode === 13) {
        searchGears();
    }
}

function getSearchParams() {
    searchData = {
        txtGearName: txtGearName.value.trim(),
        ddlType: ddlType.value,
        ddlSortBy: ddlSortBy.value
    };
    return searchData;
}
