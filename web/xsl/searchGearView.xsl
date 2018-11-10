<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    version="1.0">
    
    <xsl:output method="html" indent="yes" encoding="UTF-8" />
    
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    <xsl:template match="/">
        <xsl:variable name="currentPage" select="//*[local-name()='CurrentPage']/text()" />
        <xsl:variable name="itemCount" select="//*[local-name()='ResultCount']/text()" />
        <xsl:variable name="maxPage" select="//*[local-name()='MaxPage']/text()" />
        
        <xsl:if test="$itemCount = 0">
            <h2>Không tìm thấy Gear nào!</h2>
        </xsl:if>
        <xsl:if test="$itemCount > 0">
            <h2>Tìm thấy <xsl:value-of select="//*[local-name()='ResultCount']"/> Gear</h2>
            <div class="pagination--div" >
                <xsl:if test="$currentPage = 0" >
                    <button id="btnPrev" onclick="gotoPage(currentPage - 1)" disabled="disabled" >&lt;</button>
                </xsl:if>
                <xsl:if test="not ($currentPage = 0)" >
                    <button id="btnPrev" onclick="gotoPage(currentPage - 1)" >&lt;</button>
                </xsl:if>

                <button id="btnCurrent"><xsl:value-of select="$currentPage"/></button>

                <xsl:if test="$currentPage = $maxPage" >
                    <button id="btnNext" onclick="gotoPage(currentPage + 1)" disabled="disabled" >&gt;</button>
                </xsl:if>
                <xsl:if test="not ($currentPage = $maxPage)" >
                    <button id="btnNext" onclick="gotoPage(currentPage + 1)" >&gt;</button>
                </xsl:if>
            </div>
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
                    <xsl:for-each select="//*[local-name()='GearList']/*[local-name()='Gear']">
                        <tr>
                            <td>
                                <a href="{*[local-name()='GearUrl']/text()}" target="_blank">
                                    <img src="{*[local-name()='ImgUrl']/text()}" class="width-150" />
                                </a>
                            </td>
                            <td>
                                <a href="{*[local-name()='GearUrl']/text()}" target="_blank">
                                    <xsl:value-of select="*[local-name()='GearName']/text()"/>
                                </a>
                            </td>
                            <td>
                                <xsl:value-of select="*[local-name()='Price']/text()"/>
                            </td>
                            <td>
                                <xsl:value-of select="*[local-name()='Type']/text()"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </tbody>
            </table>
            <div class="pagination--div" >
                <xsl:if test="$currentPage = 0" >
                    <button id="btnPrev" onclick="gotoPage(currentPage - 1)" disabled="disabled" >&lt;</button>
                </xsl:if>
                <xsl:if test="not ($currentPage = 0)" >
                    <button id="btnPrev" onclick="gotoPage(currentPage - 1)" >&lt;</button>
                </xsl:if>

                <button id="btnCurrent"><xsl:value-of select="$currentPage"/></button>

                <xsl:if test="$currentPage = $maxPage" >
                    <button id="btnNext" onclick="gotoPage(currentPage + 1)" disabled="disabled" >&gt;</button>
                </xsl:if>
                <xsl:if test="not ($currentPage = $maxPage)" >
                    <button id="btnNext" onclick="gotoPage(currentPage + 1)" >&gt;</button>
                </xsl:if>
            </div>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
