const buildServiceUrl = "/GearBuilder/resource/build/";

const txtMoney = document.getElementById("txtMoney");

const txtMousePercentage = document.getElementById("txtMousePercentage");
const txtKeyboardPercentage = document.getElementById("txtKeyboardPercentage");
const txtPadPercentage = document.getElementById("txtPadPercentage");
const txtHeadsetPercentage = document.getElementById("txtHeadsetPercentage");

const btnBuild = document.getElementById("btnBuild");

const advancedSearchDiv = document.getElementById("advanced-search--div");

const errorMsg = document.getElementById("error-msg");

const gearSets = document.getElementById("gearSets");

(function() {
    doAjaxGetXML("GET", buildServiceUrl + "xsl?xslFilePath=xsl/listGearSet.xsl", function (returnedXML) {
        xsl = returnedXML;
        if (getUrlParam("txtMoney")) {
            getGearSets(buildData);
        }
    });
    setParamsForInput();
    window.onhashchange = function() {
        getGearSets();
    };
})();

function getGearSets(params) {
    btnBuild.disabled = true;
    gearSets.innerHTML = loadingGif;
    var paramsUrl = getSearchUrl(params);
    doAjaxGetXML("GET", buildServiceUrl + paramsUrl, function (returnedXML) {
        if (returnedXML === "Error" || !returnedXML) {
            gearSets.innerHTML = "<h2>Không tìm thấy Set Gear phù hợp!</h2>";
        } else {
            xml = returnedXML;
            gearSets.innerHTML = "";
            gearSets.appendChild(transform(xml, xsl));
            pushState(domain + paramsUrl);
        }
        btnBuild.disabled = false;
    });
}

function getSearchUrl(params) {
    var param = "?";
    if (!params) {
        params = buildData;
    }
    if (params.txtMoney) {
        param += "txtMoney=" + params.txtMoney;
    }
    if (params.txtMousePercentage) {
        param += "&txtMousePercentage=" + params.txtMousePercentage;
    }
    if (params.txtKeyboardPercentage) {
        param += "&txtKeyboardPercentage=" + params.txtKeyboardPercentage;
    }
    if (params.txtPadPercentage) {
        param += "&txtPadPercentage=" + params.txtPadPercentage;
    }
    if (params.txtHeadsetPercentage) {
        param += "&txtHeadsetPercentage=" + params.txtHeadsetPercentage;
    }
    return "build" + param;
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
    
    if (total >= 100) {
        errorMsg.innerHTML = "Phần trăm vượt quá 100%!";
        return;
    }
    
    var money = parseInt(txtMoney.value.trim()) * 1000;
    
    if (!money || money <= 0) {
        errorMsg.innerHTML = "Giá tiền không hợp lệ!";
        return;
    }
    
    errorMsg.innerHTML = "";
    txtHeadsetPercentage.value = 100 - total;
}

function getAllParam() {
    buildData = {
        txtMoney: parseInt(txtMoney.value) * 1000,
        txtMousePercentage: parseInt(txtMousePercentage.value),
        txtKeyboardPercentage: parseInt(txtKeyboardPercentage.value),
        txtPadPercentage: parseInt(txtPadPercentage.value),
        txtHeadsetPercentage: parseInt(txtHeadsetPercentage.value)
    };
    return buildData;
}

function buildGearSet() {
    var error = errorMsg.innerHTML;
    if (error) {
        return;
    } else {
        var params = getAllParam();
        if (params.txtMoney > 4000000000) {
            errorMsg.innerHTML = "Giá tiền quá lớn!";
            return;
        }
        getGearSets(params);
    }
}

function build() {
    if (event.keyCode === 13) {
        buildGearSet();
    }
}

function setParamsForInput() {
    txtMoney.value = buildData.txtMoney / 1000;
    
    txtMousePercentage.value = buildData.txtMousePercentage;
    txtKeyboardPercentage.value = buildData.txtKeyboardPercentage;
    txtPadPercentage.value = buildData.txtPadPercentage;
    txtHeadsetPercentage.value = buildData.txtHeadsetPercentage;
}
