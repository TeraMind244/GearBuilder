<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <h1>Search Page</h1>
        
        <c:set var="gears" value="${requestScope.GEARS}" />
        
        <table border="1">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Source</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="gear" items="${gears}" >
                    <tr>
                        <td>
                            <a href="${gear.gearUrl}" target="_blank">${gear.gearName}</a>
                        </td>
                        <td>${gear.source}</td>
                        <td>${gear.price}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        
        
        
    </body>
</html>
