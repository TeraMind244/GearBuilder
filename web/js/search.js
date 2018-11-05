const btnPrev = document.getElementById("btnPrev");
const btnNext = document.getElementById("btnNext");
const advancedSearchDiv = document.getElementById("advanced-search--div");

const txtMoney = document.getElementsByName("txtMoney")[0];

const txtMousePercentage = document.getElementsByName("txtMousePercentage")[0];
const txtKeyboardPercentage = document.getElementsByName("txtKeyboardPercentage")[0];
const txtPadPercentage = document.getElementsByName("txtPadPercentage")[0];
const txtHeadsetPercentage = document.getElementsByName("txtHeadsetPercentage")[0];

const errorMsg = document.getElementById("error-msg");

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

function toggleAdvancedSearch() {
    if (advancedSearchDiv.style.display === "none") {
        advancedSearchDiv.style.display = "block";
    } else {
        advancedSearchDiv.style.display = "none";
    }
}

function validateInput() {
    var mousePercentage = parseInt(txtMousePercentage.value);
    var keyboardPercentage = parseInt(txtKeyboardPercentage.value);
    var padPercentage = parseInt(txtPadPercentage.value);
    
    var total = mousePercentage + keyboardPercentage + padPercentage;
    
    if (total > 100) {
        errorMsg.innerHTML = "Phần trăm vượt quá 100%!";
        return;
    }
    
    var money = parseInt(txtMoney.value.trim());
    
    if (!money || money <= 0) {
        errorMsg.innerHTML = "Giá tiền không hợp lệ!";
        return;
    }
    
    errorMsg.innerHTML = "";
    txtHeadsetPercentage.value = 100 - total;
}

function buildGearSet() {
    var error = errorMsg.innerHTML;
    if (error) {
        return;
    } else {
        var params = getAllParam();
//        console.log("/build" + params);
        location.href = "/GearBuilder/build" + params;
    }
}

function getAllParam() {
    var param = "?";
    var mousePercentage = parseInt(txtMousePercentage.value);
    var keyboardPercentage = parseInt(txtKeyboardPercentage.value);
    var padPercentage = parseInt(txtPadPercentage.value);
    var headsetPercentage = parseInt(txtHeadsetPercentage.value);
    
    param += "txtMousePercentage=" + mousePercentage +
            "&txtKeyboardPercentage=" + keyboardPercentage +
            "&txtPadPercentage=" + padPercentage +
            "&txtHeadsetPercentage=" + headsetPercentage;
    
    var money = parseInt(txtMoney.value);
    
    if (money) {
        param += "&txtMoney=" + money;
    } else {
        money
    }
    
    return param;
}

