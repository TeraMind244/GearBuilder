<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/search.css" />
        <title>Search Page</title>
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
                        <select name="ddlType" class="input" >
                            <option value="all">Tất cả</option>
                            <option value="chuot">Chuột</option>
                            <option value="ban-phim">Bàn Phím</option>
                            <option value="tai-nghe">Tai Nghe</option>
                            <option value="pad">Pad Chuột</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Sắp xếp: </td>
                    <td>
                        <select name="ddlSortBy" class="input" >
                            <option value="nameAsc">Tên (Tăng)</option>
                            <option value="nameDesc">Tên (Giảm)</option>
                            <option value="priceAsc">Giá (Tăng)</option>
                            <option value="priceDesc">Giá (Giảm)</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <button type="submit" name="btAction" value="search">Search</button>
                    </td>
                </tr>
            </table>
        </form>
        
        <c:set var="gears" value="${requestScope.GEARS}" />
        <c:if test="${not empty gears}" >
            <h2>Tìm thấy ${requestScope.GEARCOUNT} Gear</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Source</th>
                        <th>Price</th>
                        <th>Type</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="gear" items="${gears}" >
                        <tr>
                            <td>
                                <a href="${gear.gearUrl}" target="_blank">${gear.gearName}</a>
                            </td>
                            <td>${gear.source}</td>
                            <td>${gear.getViewPrice()}</td>
                            <td>${gear.type}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
        <c:if test="${empty gears}" >
            <h3>There is no gear available currently! Please narrow down your search criteria!</h3>
        </c:if>
        
    </body>
</html>
