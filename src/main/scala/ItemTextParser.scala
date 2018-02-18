import scala.math._
import util.control.Breaks._


class ItemTextParser() {

  def mapItemText(items: Map[String, String], itemText: String): Map[String, String] = {

    val tagStartPos = itemText.indexOf(ItemTextParser.serialiseTagOpen, 0)
    if (tagStartPos < 0) {
      return items
    }

    val tagEndPos = itemText.indexOf(ItemTextParser.serialiseTagClose, tagStartPos) + ItemTextParser.serialiseTagClose.length
    if (tagEndPos < 0)
    {
      println(s"Invalid format. No closing tag - Starting at post ${tagStartPos} : ${itemText.substring(tagStartPos, min(itemText.length - tagStartPos, 20))}")
      return items
    }

    val maybeValEndPos = itemText.indexOf(ItemTextParser.serialiseTagOpen, tagEndPos)
    val valEndPos = if (maybeValEndPos > 0) maybeValEndPos else itemText.length

    val key = itemText
      .take(tagEndPos)
      .stripPrefix(ItemTextParser.serialiseTagOpen)
      .stripPrefix(ItemTextParser.complexPrefix)
      .stripSuffix(ItemTextParser.serialiseTagClose)
    val value = itemText
      .take(valEndPos)
      .drop(tagEndPos)
    println(s"(key:value): (${key}, ${value})")

    // Recursively send the the unprocessed part of itemText to mapItemText
    mapItemText(items + (key → value), itemText.drop(valEndPos))
  }
}

object ItemTextParser extends App {
  val itemText = "<$%$TITLE$%$>Conveyancer (Mandarin speaking)<$%$CONTACTDETAILS$%$><$%$ADDETAILS$%$><p><strong>About Us</strong><br/>We&nbsp;are a&nbsp;comprehensive yet boutique law firm is currently seeking a Conveyancer at their modern office based in Chatswood, to work with a team of twelve property lawyers and support staff.&nbsp;<br/><br/><strong>About the role</strong><br/>We are seeking a Mandarin speaking Conveyancer to join&nbsp;our growing team.<br/>&nbsp;<br/>The successful candidate will have at least 3 years conveyancing experience and be a hardworking, hand on, diligent candidate, understand clients' needs and build client relationship.</p> <ul> <li>Full time role</li> <li>Based in Chatswood</li></ul> <p><strong>Duties &amp; Responsibilities</strong></p> <ul> <li>Manage personal portfolio of both Residential and Commercial Property matters from initial instruction through to completion.</li> <li>Manage conveyancing files from start to completion</li> <li>Liaise with Councils, banks, planning bodies and other financial institutions</li> <li>General property matters including, acquisitions and sales, large transactions, property advice work, direct client contact &amp; strong drafting ability&nbsp;</li></ul> <p><strong>Skills &amp; Requirements</strong></p> <ul> <li>Language skills: Mandarin and / or Cantonese a definite advantage</li> <li>Proven experience in property and development projects preferred&nbsp;(sub-divisions of residential land, conveyancing and off the plan sales for large-scale apartment developments)</li> <li>High level of attention to detail to produce high quality work</li> <li>Excellent time management capability to manage settlements date for respective buyers and vendors</li> <li>Professional approach and the ability to remain calm under pressure</li> <li>Must be able to draft of legal correspondence and documentation such as leases, property acquisition and disposals.</li></ul> <p><strong>Benefits</strong></p> <ul> <li>Excellent boutique law firm culture - Work / Life Balance.</li> <li>Competitive salary rates</li> <li>Based in Chatswood-designated parking space and close to public transport.</li> <li>Great team environment</li></ul> <p><strong>How to Apply</strong><br/><strong>Apply directly via SEEK, include your resume and covering letter</strong></p><$%$*%^#@APPLICATIONEMAIL$%$>AAEAAAD/////AQAAAAAAAAAMAgAAAEBTZWVrVHlwZXMsIFZlcnNpb249MS4wLjAuMCwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj1udWxsBQEAAAAqU0VFSy5Eb21haW5TZXJ2aWNlcy5TZWVrVHlwZXMuRW1haWxBZGRyZXNzCAAAAAl0eXBlVmFsdWUHaXNWYWxpZAppc051bGxhYmxlCGlzVGVzdGVkGFJlZ2V4VmFsaWRhdGVkK3R5cGVWYWx1ZRZSZWdleFZhbGlkYXRlZCtpc1ZhbGlkGVJlZ2V4VmFsaWRhdGVkK2lzTnVsbGFibGUXUmVnZXhWYWxpZGF0ZWQraXNUZXN0ZWQBAAAAAQAAAAEBAQEBAQIAAAAKAQEBCgEBAQs=<$%$*%^#@APPLICATIONURL$%$>AAEAAAD/////AQAAAAAAAAAMAgAAAEBTZWVrVHlwZXMsIFZlcnNpb249MS4wLjAuMCwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj1udWxsBQEAAAAhU0VFSy5Eb21haW5TZXJ2aWNlcy5TZWVrVHlwZXMuVXJsCAAAAAl0eXBlVmFsdWUHaXNWYWxpZAppc051bGxhYmxlCGlzVGVzdGVkGFJlZ2V4VmFsaWRhdGVkK3R5cGVWYWx1ZRZSZWdleFZhbGlkYXRlZCtpc1ZhbGlkGVJlZ2V4VmFsaWRhdGVkK2lzTnVsbGFibGUXUmVnZXhWYWxpZGF0ZWQraXNUZXN0ZWQBAAAAAQAAAAEBAQEBAQIAAAAGAwAAAAABAQEJAwAAAAEBAQs=<$%$EXTERNALREFERENCE$%$>GUARANTEE50078358890<$%$JOBTITLE$%$>Conveyancer (Mandarin speaking)<$%$DESCRIPTION$%$>We are seeking a hardworking, hands-on & diligent Mandarin-speaking Conveyancer to join our growing team.<$%$*%^#@AUSNZ$%$>TRUE<$%$*%^#@RIGHTTOWORKINCOUNTRY$%$>TRUE"
  val serialiseTagOpen = "<$%$"
  val serialiseTagClose = "$%$>"
  val complexPrefix = "*%^#@"

  val itemTextParser = new ItemTextParser

  val items = Map[String, String]()
  for ((k,v) ← itemTextParser.mapItemText(items, itemText)) println(s"key: ${k}, value: ${v}")
}
