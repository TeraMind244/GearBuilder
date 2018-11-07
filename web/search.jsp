<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/search.css" />
        <title>Search Page</title>
        <script>
            var currentPage = ${requestScope.PAGE};
            var txtGearName = "${param.txtGearName}";
            var ddlType = "${param.ddlType}";
            var ddlSortBy = "${param.ddlSortBy}";
        </script>
    </head>
    <body>
        <h1>Search Page</h1>
        
        <form action="OrderServlet" method="GET">
            <table>
                <tr>
                    <td>Tên: </td>
                    <td>
                        <input type="text" name="txtGearName" value="${param.txtGearName}" class="input" />
                    </td>
                </tr>
                <tr>
                    <td>Loại: </td>
                    <td>
                        <select name="ddlType" class="input" value="${param.ddlType}" >
                            <option value="all">Tất cả</option>
                            <option value="chuot"
                                    <c:if test="${param.ddlType == 'chuot'}">
                                        selected
                                    </c:if>
                                    >Chuột</option>
                            <option value="ban-phim"
                                    <c:if test="${param.ddlType == 'ban-phim'}">
                                        selected
                                    </c:if>
                                    >Bàn Phím</option>
                            <option value="tai-nghe"
                                    <c:if test="${param.ddlType == 'tai-nghe'}">
                                        selected
                                    </c:if>
                                    >Tai Nghe</option>
                            <option value="pad"
                                    <c:if test="${param.ddlType == 'pad'}">
                                        selected
                                    </c:if>
                                    >Pad Chuột</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Sắp xếp: </td>
                    <td>
                        <select name="ddlSortBy" class="input" value="${param.ddlSortBy}" >
                            <option value="nameAsc"
                                    <c:if test="${param.ddlSortBy == 'nameAsc'}">
                                        selected
                                    </c:if>
                                    >Tên (A-Z)</option>
                            <option value="nameDesc"
                                    <c:if test="${param.ddlSortBy == 'nameDesc'}">
                                        selected
                                    </c:if>
                                        >Tên (Z-A)</option>
                            <option value="priceAsc"
                                    <c:if test="${param.ddlSortBy == 'priceAsc'}">
                                        selected
                                    </c:if>
                                    >Giá (++)</option>
                            <option value="priceDesc"
                                    <c:if test="${param.ddlSortBy == 'priceDesc'}">
                                        selected
                                    </c:if>
                                    >Giá (--)</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button type="reset">Reset</button>
                    </td>
                    <td>
                        <button type="submit" name="btAction" value="search">Search</button>
                    </td>
                </tr>
            </table>
        </form>
        <a href="builder.jsp" >Build bộ Gear của riêng bạn!</a>
        
        <c:set var="maxPage" value="${requestScope.MAXPAGE}" />
        <c:set var="page" value="${requestScope.PAGE}" />
        <c:set var="gears" value="${requestScope.GEARS}" />
        
        <c:if test="${not empty gears}" >
            <h2>Tìm thấy ${requestScope.GEARCOUNT} Gear</h2>
            
            <button id="btnPrev" onclick="gotoPage(${requestScope.PAGE - 1})"
                    <c:if test="${page eq 0}" >
                        disabled
                    </c:if>
                    >&lt;</button>
            <button id="btnCurrent">${page + 1}</button>
            <button id="btnNext" onclick="gotoPage(${requestScope.PAGE + 1})"
                    <c:if test="${page eq maxPage}" >
                        disabled
                    </c:if>
                    >&gt;</button>
            
            <table border="1">
                <thead>
                    <tr>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Type</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="gear" items="${gears}" >
                        <tr>
                            <td>
                                <a href="${gear.gearUrl}" target="_blank">
                                    <img src="${gear.imgUrl}" class="width-150" />
                                </a>
                            </td>
                            <td>
                                <a href="${gear.gearUrl}" target="_blank">${gear.gearName}</a>
                            </td>
                            <td>${gear.getViewPrice()}</td>
                            <td>${gear.getViewType()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <button id="btnPrev" onclick="gotoPage(${requestScope.PAGE - 1})"
                    <c:if test="${page eq 0}" >
                        disabled
                    </c:if>
            >&lt;</button>
            <button id="btnCurrent">${page + 1}</button>
            <button id="btnNext" onclick="gotoPage(${requestScope.PAGE + 1})"
                    <c:if test="${page eq maxPage}" >
                        disabled
                    </c:if>
            >&gt;</button>
        </c:if>
        
        <c:if test="${empty gears}" >
            <h3>Không tìm thấy Gear nào!</h3>
        </c:if>
        <script type="text/javascript" src="js/search.js"></script>
    </body>
</html>
