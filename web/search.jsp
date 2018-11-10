<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/search.css" />
        <title>Gear Builder - Tìm kiếm</title>
        <script type="text/javascript" src="js/app.js"></script>
        <script>
            var currentPage = getUrlParam("page") ? parseInt(getUrlParam("page")) : 0;
            var searchData = {
                txtGearName: getUrlParam("txtGearName") ? getUrlParam("txtGearName") : "",
                ddlType: getUrlParam("ddlType") ? getUrlParam("ddlType") : "all",
                ddlSortBy: getUrlParam("ddlSortBy") ? getUrlParam("ddlSortBy") : ""
            };
            var xml;
            var xsl;
        </script>
    </head>
    <body>
        <h1>Tìm kiếm</h1>
        <table>
            <tr>
                <td>Tên: </td>
                <td>
                    <input type="text" id="txtGearName" class="input" onkeydown="search()" autocomplete="off" />
                </td>
            </tr>
            <tr>
                <td>Loại: </td>
                <td>
                    <select id="ddlType" class="input" >
                        <option value="all" selected >Tất cả</option>
                        <option value="chuot" >Chuột</option>
                        <option value="ban-phim" >Bàn Phím</option>
                        <option value="tai-nghe" >Tai Nghe</option>
                        <option value="pad" >Lót Chuột</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Sắp xếp: </td>
                <td>
                    <select id="ddlSortBy" class="input" >
                        <option value="nameAsc" selected >Tên (A-Z)</option>
                        <option value="nameDesc" >Tên (Z-A)</option>
                        <option value="priceAsc" >Giá (++)</option>
                        <option value="priceDesc" >Giá (--)</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button id="btnSearch" onclick="searchGears()" >Tìm kiếm</button>
                </td>
            </tr>
        </table>

        <a href="build" >Build bộ Gear của riêng bạn!</a>
        
        <div id="gears"></div>
        <script type="text/javascript" src="js/search.js"></script>
    </body>
</html>
