const buildServiceUrl = "/GearBuilder/resource/admin/";

const btnPause = document.getElementById("btnPause");
const btnResume = document.getElementById("btnResume");

const dataRefMsg = document.getElementById("dataRefMsg");
const startCrawlerMsg = document.getElementById("startCrawlerMsg");
const pauseCrawlerMsg = document.getElementById("pauseCrawlerMsg");
const resumeCrawlerMsg = document.getElementById("resumeCrawlerMsg");

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
        pauseCrawlerMsg.innerHTML = "";
        loadingSection.innerHTML = "";
        btnPause.disabled = true;
    });
}

function pauseCrawler(btn) {
    startCrawlerMsg.innerHTML = "Processing!";
    loadingSection.innerHTML = loadingGif;
    doAjaxGetText("GET", buildServiceUrl + "pauseCrawler", function (reponsedText) {
        btn.disabled = true;
        pauseCrawlerMsg.innerHTML = reponsedText;
        resumeCrawlerMsg.innerHTML = "";
        loadingSection.innerHTML = "";
        btnResume.disabled = false;
    });
}

function resumeCrawler(btn) {
    loadingSection.innerHTML = loadingGif;
    doAjaxGetText("GET", buildServiceUrl + "resumeCrawler", function (reponsedText) {
        btn.disabled = true;
        resumeCrawlerMsg.innerHTML = reponsedText;
        pauseCrawlerMsg.innerHTML = "";
        resumeCrawlerMsg.innerHTML = "";
        startCrawlerMsg.innerHTML = "Processing!";
        btnPause.disabled = false;
    });
}
