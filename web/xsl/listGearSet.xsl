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
            <table border="1" id="builder--table">
                <thead>
                    <tr>
                        <th>Stt.</th>
                        <th>Chuột</th>
                        <th>Bàn Phím</th>
                        <th>Lót chuột</th>
                        <th>Tai nghe</th>
                        <th>Tổng tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <xsl:for-each select="//GearSet">
                        <xsl:variable name="count" select="position()" />
                        <tr>
                            <td>
                                <xsl:value-of select="$count"/>
                            </td>
                            <td>
                                <a href="{*[local-name()='Mouse']/*[local-name()='GearUrl']}" target="_blank">
                                    <img src="{*[local-name()='Mouse']/*[local-name()='ImgUrl']}" class="width-150" />
                                    <br/>
                                    <xsl:value-of select="*[local-name()='Mouse']/*[local-name()='GearName']"/>
                                </a>
                                <br/>
                                <xsl:value-of select="*[local-name()='Mouse']/*[local-name()='Price']"/>
                            </td>
                            <td>
                                <a href="{*[local-name()='KeyBoard']/*[local-name()='GearUrl']}" target="_blank">
                                    <img src="{*[local-name()='KeyBoard']/*[local-name()='ImgUrl']}" class="width-150" />
                                    <br/>
                                    <xsl:value-of select="*[local-name()='KeyBoard']/*[local-name()='GearName']"/>
                                </a>
                                <br/>
                                <xsl:value-of select="*[local-name()='KeyBoard']/*[local-name()='Price']"/>
                            </td>
                            <td>
                                <a href="{*[local-name()='Pad']/*[local-name()='GearUrl']}" target="_blank">
                                    <img src="{*[local-name()='Pad']/*[local-name()='ImgUrl']}" class="width-150" />
                                    <br/>
                                    <xsl:value-of select="*[local-name()='Pad']/*[local-name()='GearName']"/>
                                </a>
                                <br/>
                                <xsl:value-of select="*[local-name()='Pad']/*[local-name()='Price']"/>
                            </td>
                            <td>
                                <a href="{*[local-name()='HeadSet']/*[local-name()='GearUrl']}" target="_blank">
                                    <img src="{*[local-name()='HeadSet']/*[local-name()='ImgUrl']}" class="width-150" />
                                    <br/>
                                    <xsl:value-of select="*[local-name()='HeadSet']/*[local-name()='GearName']"/>
                                </a>
                                <br/>
                                <xsl:value-of select="*[local-name()='HeadSet']/*[local-name()='Price']"/>
                            </td>
                            <td>
                                <xsl:value-of select="Value"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </tbody>
            </table>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
