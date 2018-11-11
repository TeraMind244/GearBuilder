<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gear Builder - Admin</title>
        <script type="text/javascript" src="js/app.js"></script>
    </head>
    <body>
        <h1>Hello Admin!</h1>
        
        <table>
            <tr>
                <td>
                    <button onclick="refreshDataRef(this)">Refresh data reference!</button>
                </td>
                <td class="width-150">
                    <span id="dataRefMsg" style="color: red;" ></span>
                </td>
            </tr>
            <tr>
                <td>
                    <button onclick="startCrawler(this)">Start crawler!</button>
                </td>
                <td class="width-150">
                    <span id="startCrawlerMsg" style="color: red;" ></span>
                </td>
            </tr>
            <tr>
                <td>
                    <button id="btnPause" onclick="pauseCrawler(this)" disabled >Pause crawler!</button>
                </td>
                <td class="width-150">
                    <span id="pauseCrawlerMsg" style="color: red;" ></span>
                </td>
            </tr>
            <tr>
                <td>
                    <button id="btnResume" onclick="resumeCrawler(this)" disabled >Resume crawler!</button>
                </td>
                <td class="width-150">
                    <span id="resumeCrawlerMsg" style="color: red;" ></span>
                </td>
            </tr>
        </table>
        
        <div id="loading-section"></div>
        <script type="text/javascript" src="js/admin.js"></script>
    </body>
</html>
