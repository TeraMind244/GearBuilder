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
    setParamsForInput();
})();

function getGears(params) {
    gears.innerHTML = loadingGif;
    var paramsUrl = getSearchUrl(params);
    doAjaxGetXML("GET", searchServiceUrl + paramsUrl, function (returnedXML) {
        xml = returnedXML;
        gears.innerHTML = "";
        gears.appendChild(transform(xml, xsl));
        var url = new URL(window.location.href);
        pushState(domain + paramsUrl);
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

function setParamsForInput() {
    txtGearName.value = searchData.txtGearName;
    var selectedIndex = 0;
    switch (searchData.ddlType) {
        case "all":
            selectedIndex = 0;
            break;
        case "chuot":
            selectedIndex = 1;
            break;
        case "ban-phim":
            selectedIndex = 2;
            break;
        case "tai-nghe":
            selectedIndex = 3;
            break;
        case "pad":
            selectedIndex = 4;
            break;
    }
    ddlType.options.selectedIndex = selectedIndex;
    switch (searchData.ddlSortBy) {
        case "nameAsc":
            selectedIndex = 0;
            break;
        case "nameDesc":
            selectedIndex = 1;
            break;
        case "priceAsc":
            selectedIndex = 2;
            break;
        case "priceDesc":
            selectedIndex = 3;
            break;
        default:
            selectedIndex = 0;
            break;
    }
    ddlSortBy.options.selectedIndex = selectedIndex;
}
