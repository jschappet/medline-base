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
 *         &lt;element ref="{}Year"/>
 *         &lt;element ref="{}Month"/>
 *         &lt;element ref="{}Day"/>
 *       &lt;/sequence>
 *       &lt;attribute name="DateType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="Electronic" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "year",
    "month",
    "day"
})
@XmlRootElement(name = "ArticleDate")
public class ArticleDate {

    @XmlElement(name = "Year", required = true)
    protected Year year;
    @XmlElement(name = "Month", required = true)
    protected Month month;
    @XmlElement(name = "Day", required = true)
    protected Day day;
    @XmlAttribute(name = "DateType", required = true)
    protected String dateType;

    /**
     * Gets the value of the year property.
     * 
     * @return
     *     possible object is
     *     {@link Year }
     *     
     */
    public Year getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     * @param value
     *     allowed object is
     *     {@link Year }
     *     
     */
    public void setYear(Year value) {
        this.year = value;
    }

    /**
     * Gets the value of the month property.
     * 
     * @return
     *     possible object is
     *     {@link Month }
     *     
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     * 
     * @param value
     *     allowed object is
     *     {@link Month }
     *     
     */
    public void setMonth(Month value) {
        this.month = value;
    }

    /**
     * Gets the value of the day property.
     * 
     * @return
     *     possible object is
     *     {@link Day }
     *     
     */
    public Day getDay() {
        return day;
    }

    /**
     * Sets the value of the day property.
     * 
     * @param value
     *     allowed object is
     *     {@link Day }
     *     
     */
    public void setDay(Day value) {
        this.day = value;
    }

    /**
     * Gets the value of the dateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateType() {
        if (dateType == null) {
            return "Electronic";
        } else {
            return dateType;
        }
    }

    /**
     * Sets the value of the dateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateType(String value) {
        this.dateType = value;
    }

}
