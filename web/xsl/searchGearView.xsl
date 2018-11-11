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
            <div id="header--div">
                <span id="found-text">Tìm thấy <xsl:value-of select="//*[local-name()='ResultCount']"/> Gear</span>
                <span class="pagination--div" >
                    <xsl:if test="$currentPage = 0" >
                        <button id="btnFirst" disabled="disabled" >&lt;&lt;</button>
                        <button id="btnPrev" disabled="disabled" >&lt;</button>
                    </xsl:if>
                    <xsl:if test="not ($currentPage = 0)" >
                        <button id="btnFirst" onclick="gotoPage(0)" >&lt;&lt;</button>
                        <button id="btnPrev" onclick="gotoPage(currentPage - 1)" >&lt;</button>
                        <button onclick="gotoPage(currentPage - 1)" >
                            <xsl:value-of select="$currentPage"/>
                        </button>
                    </xsl:if>

                    <button id="btnCurrent">
                        <xsl:value-of select="$currentPage + 1"/>
                    </button>

                    <xsl:if test="$currentPage = $maxPage" >
                        <button id="btnNext" onclick="gotoPage(currentPage + 1)" disabled="disabled" >&gt;</button>
                        <button id="btnLast" disabled="disabled" >&gt;&gt;</button>
                    </xsl:if>
                    <xsl:if test="not ($currentPage = $maxPage)" >
                        <button onclick="gotoPage(currentPage + 1)" >
                            <xsl:value-of select="$currentPage + 2"/>
                        </button>
                        <button id="btnNext" onclick="gotoPage(currentPage + 1)" >&gt;</button>
                        <button id="btnLast" onclick="gotoPage({$maxPage})" >&gt;&gt;</button>
                    </xsl:if>
                </span>
            </div>
            <div id="gearList">
                <xsl:for-each select="//*[local-name()='GearList']/*[local-name()='Gear']">
                    <div class="gear--div">
                        <a href="{*[local-name()='GearUrl']/text()}" target="_blank">
                            <div class="gear-img--div">
                                <img class="gear--img" src="{*[local-name()='ImgUrl']/text()}" />
                            </div>
                            <div class="gear-name--div">
                                <xsl:value-of select="*[local-name()='GearName']/text()"/>
                            </div>
                        </a>
                        <div class="gear-price--div">
                            <xsl:value-of select="*[local-name()='Price']/text()"/>
                        </div>
                        <div class="gear-type--div">
                            <xsl:value-of select="*[local-name()='Type']/text()"/>
                        </div>
                    </div>
                </xsl:for-each>
            </div>
            <div class="pagination--div" >
                <xsl:if test="$currentPage = 0" >
                    <button id="btnFirst" disabled="disabled" >&lt;&lt;</button>
                    <button id="btnPrev" disabled="disabled" >&lt;</button>
                </xsl:if>
                <xsl:if test="not ($currentPage = 0)" >
                    <button id="btnFirst" onclick="gotoPage(0)" >&lt;&lt;</button>
                    <button id="btnPrev" onclick="gotoPage(currentPage - 1)" >&lt;</button>
                    <button onclick="gotoPage(currentPage - 1)" >
                        <xsl:value-of select="$currentPage"/>
                    </button>
                </xsl:if>

                <button id="btnCurrent">
                    <xsl:value-of select="$currentPage + 1"/>
                </button>

                <xsl:if test="$currentPage = $maxPage" >
                    <button id="btnNext" onclick="gotoPage(currentPage + 1)" disabled="disabled" >&gt;</button>
                    <button id="btnLast" disabled="disabled" >&gt;&gt;</button>
                </xsl:if>
                <xsl:if test="not ($currentPage = $maxPage)" >
                    <button onclick="gotoPage(currentPage + 1)" >
                        <xsl:value-of select="$currentPage + 2"/>
                    </button>
                    <button id="btnNext" onclick="gotoPage(currentPage + 1)" >&gt;</button>
                    <button id="btnLast" onclick="gotoPage({$maxPage})" >&gt;&gt;</button>
                </xsl:if>
            </div>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
