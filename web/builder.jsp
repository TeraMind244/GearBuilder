<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Builder</title>
        <link rel="stylesheet" href="css/search.css" />
    </head>
    <body onload="initComponents()">
        <h1>Gear Builder</h1>
        <a href="SearchServlet">Back to search page!</a>
        <div id="search--div">
            Số tiền: <input type="number" name="txtMoney" value="${param.txtMoney}" oninput="validateInput()" onchange="validateInput()" />
            <button name="btAction" value="build" onclick="buildGearSet()" >Build</button>
            <br/>
            <a href="javascipt:void(0)" onclick="toggleAdvancedSearch()" >Nâng cao</a>
            <br/>
            <div id="advanced-search--div" style="display: none;">
                <table>
                    <tr>
                        <td>Chuột</td>
                        <td>
                            <input value="35" type="number" min="0" max="100" name="txtMousePercentage" oninput="validateInput()" onchange="validateInput()" />%
                        </td>
                    </tr>
                    <tr>
                        <td>Bàn phím</td>
                        <td>
                            <input value="35" type="number" min="0" max="100" name="txtKeyboardPercentage" oninput="validateInput()" onchange="validateInput()" />%
                        </td>
                    </tr>
                    <tr>
                        <td>Lót chuột</td>
                        <td>
                            <input value="10" type="number" min="0" max="100" name="txtPadPercentage" oninput="validateInput()" onchange="validateInput()" />%
                        </td>
                    </tr>
                    <tr>
                        <td>Tai nghe</td>
                        <td>
                            <input value="25" type="number" min="0" max="100" name="txtHeadsetPercentage" disabled />%
                        </td>
                    </tr>
                </table>
            </div>
            <span style="color: red;" id="error-msg"></span>
        </div>
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
                                </td>
                                <td>
                                    <a href="${gearset.keyBoard.gearUrl}" target="_blank">
                                        <img src="${gearset.keyBoard.imgUrl}" class="width-150" />
                                        <br/>
                                        ${gearset.keyBoard.gearName}
                                    </a>
                                    <br/>
                                    ${gearset.keyBoard.getViewPrice()}
                                </td>
                                <td>
                                    <a href="${gearset.pad.gearUrl}" target="_blank">
                                        <img src="${gearset.pad.imgUrl}" class="width-150" />
                                        <br/>
                                        ${gearset.pad.gearName}
                                    </a>
                                    <br/>
                                    ${gearset.pad.getViewPrice()}
                                </td>
                                <td>
                                    <a href="${gearset.headset.gearUrl}" target="_blank">
                                        <img src="${gearset.headset.imgUrl}" class="width-150" />
                                        <br/>
                                        ${gearset.headset.gearName}
                                    </a>
                                    <br/>
                                    ${gearset.headset.getViewPrice()}
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
                <h3>Không tìm thấy Gear nào!</h3>
            </c:if>
        </c:if>
        <script type="text/javascript" src="js/search.js"></script>
    </body>
</html>
