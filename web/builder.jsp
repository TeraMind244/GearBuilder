<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Builder</title>
        <link rel="stylesheet" href="css/search.css" />
        <script type="text/javascript" src="js/app.js"></script>
        <script>
            var buildData = {
                txtMoney: getUrlParam("txtMoney") ? parseInt(getUrlParam("txtMoney")) : 0,
                txtMousePercentage: getUrlParam("txtMousePercentage") ? parseInt(getUrlParam("txtMousePercentage")) : 35,
                txtKeyboardPercentage: getUrlParam("txtKeyboardPercentage") ? parseInt(getUrlParam("txtKeyboardPercentage")) : 35,
                txtPadPercentage: getUrlParam("txtPadPercentage") ? parseInt(getUrlParam("txtPadPercentage")) : 5,
                txtHeadsetPercentage: getUrlParam("txtHeadsetPercentage") ? parseInt(getUrlParam("txtHeadsetPercentage")) : 25
            };
            var xml;
            var xsl;
        </script>
    </head>
    <body>
        <h1>Gear Builder</h1>
        <a href="search">Back to search page!</a>
        <div id="search--div">
            Số tiền: <input type="number" id="txtMoney" onkeydown="build()" oninput="validateInput()" onchange="validateInput()" />
            <button id="btnBuild" onclick="buildGearSet(this)" >Build</button>
            <br/>
            <a href="#" onclick="toggleAdvancedSearch()" >Nâng cao</a>
            <br/>
            <div id="advanced-search--div" style="display: none;">
                <table>
                    <tr>
                        <td>Chuột</td>
                        <td>
                            <input class="percentage--input" type="number" min="0" max="100" 
                                   id="txtMousePercentage" oninput="validateInput()" onchange="validateInput()" />%
                        </td>
                    </tr>
                    <tr>
                        <td>Bàn phím</td>
                        <td>
                            <input class="percentage--input" type="number" min="0" max="100" 
                                   id="txtKeyboardPercentage" oninput="validateInput()" onchange="validateInput()" />%
                        </td>
                    </tr>
                    <tr>
                        <td>Lót chuột</td>
                        <td>
                            <input class="percentage--input" type="number" min="0" max="100"
                                   id="txtPadPercentage" oninput="validateInput()" onchange="validateInput()" />%
                        </td>
                    </tr>
                    <tr>
                        <td>Tai nghe</td>
                        <td>
                            <input class="percentage--input" type="number" min="0" max="100"
                                   id="txtHeadsetPercentage" disabled />%
                        </td>
                    </tr>
                </table>
            </div>
            <span style="color: red;" id="error-msg"></span>
        </div>
        <br/>
        
        <div id="gearSets"></div>
        <script type="text/javascript" src="js/builder.js"></script>
    </body>
</html>
