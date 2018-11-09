const btnPrev = document.getElementById("btnPrev");
const btnNext = document.getElementById("btnNext");

function gotoPage(page) {
    var url = "?page=" + page;
    if (txtGearName) {
        url += "&txtGearName=" + txtGearName;
    }
    if (ddlType) {
        url += "&ddlType=" + ddlType;
    }
    if (ddlSortBy) {
        url += "&ddlSortBy=" + ddlSortBy;
    }
    location.href = "search" + url;
}
