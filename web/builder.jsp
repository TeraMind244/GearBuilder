<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Builder</title>
        <link rel="stylesheet" href="css/search.css" />
    </head>
    <body>
        <h1>Gear Builder</h1>
        <a href="SearchServlet">Back to search page!</a>
        <form action="OrderServlet" method="GET">
            Số tiền: <input type="number" name="txtMoney" value="${param.txtMoney}" />
            <button name="btAction" value="build" >Build</button>
        </form>
        <br/>
        
        <c:if test="${not empty param.txtMoney}">
            <c:set var="gearsets" value="${requestScope.GEARSETS}" />
            <c:if test="${not empty gearsets}" >
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Mouse</th>
                            <th>Keyboard</th>
                            <th>Pad</th>
                            <th>Headset</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="gearset" items="${gearsets}" varStatus="counter" >
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    <a href="${gearset.mouse.gearUrl}" target="_blank">
                                        <img src="${gearset.mouse.imgUrl}" class="width-150" />
                                        <br/>
                                        ${gearset.mouse.gearName}
                                    </a>
                                    <br/>
                                    ${gearset.mouse.getViewPrice()}
                                    <br/>
                                    <a href="//TODO" >Change this gear!</a>
                                </td>
                                <td>
                                    <a href="${gearset.keyBoard.gearUrl}" target="_blank">
                                        <img src="${gearset.keyBoard.imgUrl}" class="width-150" />
                                        <br/>
                                        ${gearset.keyBoard.gearName}
                                    </a>
                                    <br/>
                                    ${gearset.keyBoard.getViewPrice()}
                                    <br/>
                                    <a href="//TODO" >Change this gear!</a>
                                </td>
                                <td>
                                    <a href="${gearset.pad.gearUrl}" target="_blank">
                                        <img src="${gearset.pad.imgUrl}" class="width-150" />
                                        <br/>
                                        ${gearset.pad.gearName}
                                    </a>
                                    <br/>
                                    ${gearset.pad.getViewPrice()}
                                    <br/>
                                    <a href="//TODO" >Change this gear!</a>
                                </td>
                                <td>
                                    <a href="${gearset.headset.gearUrl}" target="_blank">
                                        <img src="${gearset.headset.imgUrl}" class="width-150" />
                                        <br/>
                                        ${gearset.headset.gearName}
                                    </a>
                                    <br/>
                                    ${gearset.headset.getViewPrice()}
                                    <br/>
                                    <a href="//TODO" >Change this gear!</a>
                                </td>
                                <td>
                                    ${gearset.getViewValue()}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty gearsets}" >
                <h3>No gear set found with your price!</h3>//TODO
            </c:if>
        </c:if>
    </body>
</html>
