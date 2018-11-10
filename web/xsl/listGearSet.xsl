<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8" />

    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    <xsl:template match="/">
        <xsl:variable name="itemCount" select="count(//GearSet)" />
        
        <xsl:if test="$itemCount = 0">
            <h2>Không tìm thấy Set Gear phù hợp!</h2>
        </xsl:if>
        <xsl:if test="not ($itemCount = 0)">
            <h2><xsl:value-of select="$itemCount"/></h2>
        </xsl:if>
        
<!--        <c:if test="${not empty param.txtMoney}">
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
        </c:if>-->
        
        
    </xsl:template>

</xsl:stylesheet>
