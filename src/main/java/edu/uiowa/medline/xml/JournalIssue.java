//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.16 at 09:33:32 AM CDT 
//


package edu.uiowa.medline.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Volume" minOccurs="0"/>
 *         &lt;element ref="{}Issue" minOccurs="0"/>
 *         &lt;element ref="{}PubDate"/>
 *       &lt;/sequence>
 *       &lt;attribute name="CitedMedium" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="Internet"/>
 *             &lt;enumeration value="Print"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "volume",
    "issue",
    "pubDate"
})
@XmlRootElement(name = "JournalIssue")
public class JournalIssue {

    @XmlElement(name = "Volume")
    protected Volume volume;
    @XmlElement(name = "Issue")
    protected Issue issue;
    @XmlElement(name = "PubDate", required = true)
    protected PubDate pubDate;
    @XmlAttribute(name = "CitedMedium", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String citedMedium;

    /**
     * Gets the value of the volume property.
     * 
     * @return
     *     possible object is
     *     {@link Volume }
     *     
     */
    public Volume getVolume() {
        return volume;
    }

    /**
     * Sets the value of the volume property.
     * 
     * @param value
     *     allowed object is
     *     {@link Volume }
     *     
     */
    public void setVolume(Volume value) {
        this.volume = value;
    }

    /**
     * Gets the value of the issue property.
     * 
     * @return
     *     possible object is
     *     {@link Issue }
     *     
     */
    public Issue getIssue() {
        return issue;
    }

    /**
     * Sets the value of the issue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Issue }
     *     
     */
    public void setIssue(Issue value) {
        this.issue = value;
    }

    /**
     * Gets the value of the pubDate property.
     * 
     * @return
     *     possible object is
     *     {@link PubDate }
     *     
     */
    public PubDate getPubDate() {
        return pubDate;
    }

    /**
     * Sets the value of the pubDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link PubDate }
     *     
     */
    public void setPubDate(PubDate value) {
        this.pubDate = value;
    }

    /**
     * Gets the value of the citedMedium property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitedMedium() {
        return citedMedium;
    }

    /**
     * Sets the value of the citedMedium property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitedMedium(String value) {
        this.citedMedium = value;
    }

}
