const buildServiceUrl = "/GearBuilder/resource/admin/";

const btnPause = document.getElementById("btnPause");
const btnResume = document.getElementById("btnResume");

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
    btnPause.disabled = false;
    startCrawlerMsg.innerHTML = "Processing!";
    loadingSection.innerHTML = loadingGif;
    doAjaxGetText("GET", buildServiceUrl + "startCrawler", function (reponsedText) {
        btn.disabled = false;
        startCrawlerMsg.innerHTML = reponsedText;
        loadingSection.innerHTML = "";
        btnPause.disabled = true;
    });
}

function pauseCrawler(btn) {
    startCrawlerMsg.innerHTML = "Processing!";
    loadingSection.innerHTML = loadingGif;
    doAjaxGetText("GET", buildServiceUrl + "pauseCrawler", function (reponsedText) {
        btn.disabled = true;
        startCrawlerMsg.innerHTML = reponsedText;
        loadingSection.innerHTML = "";
        btnResume.disabled = false;
    });
}

function resumeCrawler(btn) {
    startCrawlerMsg.innerHTML = "Processing!";
    loadingSection.innerHTML = loadingGif;
    doAjaxGetText("GET", buildServiceUrl + "resumeCrawler", function (reponsedText) {
        btn.disabled = true;
        startCrawlerMsg.innerHTML = reponsedText;
        loadingSection.innerHTML = "";
        btnPause.disabled = false;
    });
}
