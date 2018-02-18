import scala.math._
import util.control.Breaks._


class ItemTextParser() {

  def mapItemText(source: String): scala.collection.mutable.Map[String, String] = {

    val itemText = source.trim
    var items = scala.collection.mutable.Map[String, String]()
    var startPos = 0

    breakable {
      while (startPos + ItemTextParser.serialiseTagOpenLen < itemText.length){
        startPos = itemText.indexOf(ItemTextParser.serialiseTagOpen, startPos)

        if (startPos < 0) {
            break
        }

        var endPos = itemText.indexOf(ItemTextParser.serialiseTagClose, startPos)
        if (endPos < 0)
        {
          println(s"Invalid format. No closing tag - Starting at post ${startPos} : ${itemText.substring(startPos, min(itemText.length - startPos, 20))}")
        }

        var valEndPos = itemText.indexOf(ItemTextParser.serialiseTagOpen, endPos)
        if (valEndPos == -1) {
          valEndPos = itemText.length;
        }

        var key = itemText.substring(startPos + ItemTextParser.serialiseTagOpenLen, endPos)
        var value = itemText.substring(endPos + ItemTextParser.serialiseTagCloseLen, valEndPos)
        items += parseItem(key, value)

        startPos = valEndPos
      }
    }

    items
  }

  def parseItem(key: String, valueStr: String): (String, String) = {
    var newKey = ""
    if (key.startsWith(ItemTextParser.complexPrefix)) {
      newKey = key.substring(ItemTextParser.complexPrefixLen, key.length)
    } else {
      newKey = key
    }
    (newKey → valueStr)
  }
}

object ItemTextParser extends App {
  val itemText = "<$%$TITLE$%$>Conveyancer (Mandarin speaking)<$%$CONTACTDETAILS$%$><$%$ADDETAILS$%$><p><strong>About Us</strong><br/>We&nbsp;are a&nbsp;comprehensive yet boutique law firm is currently seeking a Conveyancer at their modern office based in Chatswood, to work with a team of twelve property lawyers and support staff.&nbsp;<br/><br/><strong>About the role</strong><br/>We are seeking a Mandarin speaking Conveyancer to join&nbsp;our growing team.<br/>&nbsp;<br/>The successful candidate will have at least 3 years conveyancing experience and be a hardworking, hand on, diligent candidate, understand clients' needs and build client relationship.</p> <ul> <li>Full time role</li> <li>Based in Chatswood</li></ul> <p><strong>Duties &amp; Responsibilities</strong></p> <ul> <li>Manage personal portfolio of both Residential and Commercial Property matters from initial instruction through to completion.</li> <li>Manage conveyancing files from start to completion</li> <li>Liaise with Councils, banks, planning bodies and other financial institutions</li> <li>General property matters including, acquisitions and sales, large transactions, property advice work, direct client contact &amp; strong drafting ability&nbsp;</li></ul> <p><strong>Skills &amp; Requirements</strong></p> <ul> <li>Language skills: Mandarin and / or Cantonese a definite advantage</li> <li>Proven experience in property and development projects preferred&nbsp;(sub-divisions of residential land, conveyancing and off the plan sales for large-scale apartment developments)</li> <li>High level of attention to detail to produce high quality work</li> <li>Excellent time management capability to manage settlements date for respective buyers and vendors</li> <li>Professional approach and the ability to remain calm under pressure</li> <li>Must be able to draft of legal correspondence and documentation such as leases, property acquisition and disposals.</li></ul> <p><strong>Benefits</strong></p> <ul> <li>Excellent boutique law firm culture - Work / Life Balance.</li> <li>Competitive salary rates</li> <li>Based in Chatswood-designated parking space and close to public transport.</li> <li>Great team environment</li></ul> <p><strong>How to Apply</strong><br/><strong>Apply directly via SEEK, include your resume and covering letter</strong></p><$%$*%^#@APPLICATIONEMAIL$%$>AAEAAAD/////AQAAAAAAAAAMAgAAAEBTZWVrVHlwZXMsIFZlcnNpb249MS4wLjAuMCwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj1udWxsBQEAAAAqU0VFSy5Eb21haW5TZXJ2aWNlcy5TZWVrVHlwZXMuRW1haWxBZGRyZXNzCAAAAAl0eXBlVmFsdWUHaXNWYWxpZAppc051bGxhYmxlCGlzVGVzdGVkGFJlZ2V4VmFsaWRhdGVkK3R5cGVWYWx1ZRZSZWdleFZhbGlkYXRlZCtpc1ZhbGlkGVJlZ2V4VmFsaWRhdGVkK2lzTnVsbGFibGUXUmVnZXhWYWxpZGF0ZWQraXNUZXN0ZWQBAAAAAQAAAAEBAQEBAQIAAAAKAQEBCgEBAQs=<$%$*%^#@APPLICATIONURL$%$>AAEAAAD/////AQAAAAAAAAAMAgAAAEBTZWVrVHlwZXMsIFZlcnNpb249MS4wLjAuMCwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj1udWxsBQEAAAAhU0VFSy5Eb21haW5TZXJ2aWNlcy5TZWVrVHlwZXMuVXJsCAAAAAl0eXBlVmFsdWUHaXNWYWxpZAppc051bGxhYmxlCGlzVGVzdGVkGFJlZ2V4VmFsaWRhdGVkK3R5cGVWYWx1ZRZSZWdleFZhbGlkYXRlZCtpc1ZhbGlkGVJlZ2V4VmFsaWRhdGVkK2lzTnVsbGFibGUXUmVnZXhWYWxpZGF0ZWQraXNUZXN0ZWQBAAAAAQAAAAEBAQEBAQIAAAAGAwAAAAABAQEJAwAAAAEBAQs=<$%$EXTERNALREFERENCE$%$>GUARANTEE50078358890<$%$JOBTITLE$%$>Conveyancer (Mandarin speaking)<$%$DESCRIPTION$%$>We are seeking a hardworking, hands-on & diligent Mandarin-speaking Conveyancer to join our growing team.<$%$*%^#@AUSNZ$%$>TRUE<$%$*%^#@RIGHTTOWORKINCOUNTRY$%$>TRUE"

  val serialiseTagOpen = "<$%$"
  val serialiseTagClose = "$%$>"
  val complexPrefix = "*%^#@"

  val serialiseTagOpenLen = 4
  val serialiseTagCloseLen = 4
  val complexPrefixLen = 5

  val itemTextParser = new ItemTextParser

  for ((k,v) ← itemTextParser.mapItemText(itemText)) println(s"key: ${k}, value: ${v}")
}
