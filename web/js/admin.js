const buildServiceUrl = "/GearBuilder/resource/admin/";

const dataRefMsg = document.getElementById("dataRefMsg");
const startCrawlerMsg = document.getElementById("startCrawlerMsg");

const loadingSection = document.getElementById("loading-section");

function refreshDataRef(btn) {
    btn.disabled = true;
    dataRefMsg.innerHTML = "Processing!";
    loadingSection.innerHTML = loadingGif;
    doAjaxGetText("GET", buildServiceUrl + "getDataRef", function (reponsedText) {
        btn.disabled = false;
        dataRefMsg.innerHTML = reponsedText;
        loadingSection.innerHTML = "";
    });
}

function startCrawler(btn) {
    btn.disabled = true;
    startCrawlerMsg.innerHTML = "Processing!";
    loadingSection.innerHTML = loadingGif;
    doAjaxGetText("GET", buildServiceUrl + "startCrawler", function (reponsedText) {
        btn.disabled = false;
        startCrawlerMsg.innerHTML = reponsedText;
        loadingSection.innerHTML = "";
    });
}
