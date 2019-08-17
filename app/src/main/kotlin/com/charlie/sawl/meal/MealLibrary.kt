package com.charlie.sawl.meal

import net.htmlparser.jericho.Element
import net.htmlparser.jericho.Source
import java.io.IOException
import java.net.URL
import java.io.InputStream
import java.util.Objects
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection

object MealLibrary {

    private val hostnameVerifier = HostnameVerifier { _, _ -> true }

    fun getDateNew(year: String, month: String, day: String): Array<String?> {

        val date = arrayOfNulls<String>(7)
        val url = ("https://stu.goe.go.kr/sts_sci_md01_001.do?schulCode=J100005284&schulCrseScCode="
                + "4&schulKndScCode=04&schMmealScCode=" + "0&schYmd=" + year + "." + month + "." + day)

        return getDateNewSub(date, url)
    }

    private fun getDateNewSub(date: Array<String?>, url: String): Array<String?> {
        var mSource: Source? = null

        try {
            val mUrl = URL(url)
            var mStream: InputStream? = null

            try {
                val urlConnection = mUrl.openConnection() as HttpsURLConnection
                urlConnection.setRequestProperty("Content-Type", "application/json")
                urlConnection.hostnameVerifier = hostnameVerifier
                mStream = urlConnection.inputStream
                mSource = Source(mStream!!)

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mStream?.close()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        mSource!!.fullSequentialParse()
        val table = mSource.getAllElements("table")

        for (i in table.indices) {
            if ((table[i] as Element).getAttributeValue("class") == "tbl_type3") {
                val tr = (table[i] as Element).getAllElements("tr")
                val th = (tr[0] as Element).getAllElements("th")

                for (j in 0..6) {
                    date[j] = (th[j + 1] as Element).content.toString()
                }

                break
            }
        }

        return date
    }

    fun getMealNew(schoolMealScCode: String, year: String, month: String, day: String): Array<String?> {

        val content = arrayOfNulls<String>(7)
        val url = ("https://stu.goe.go.kr/sts_sci_md01_001.do?schulCode=J100005284&schulCrseScCode="
                + "4&schulKndScCode=04&schMmealScCode=" + schoolMealScCode + "&schYmd=" + year + "." + month + "." + day)

        return getMealNewSub(content, url)
    }

    private fun getMealNewSub(content: Array<String?>, url: String): Array<String?> {
        var mSource: Source? = null

        try {
            val mUrl = URL(url)
            var mStream: InputStream? = null

            try {
                val urlConnection = mUrl.openConnection() as HttpsURLConnection
                urlConnection.setRequestProperty("Content-Type", "application/json")
                urlConnection.hostnameVerifier = hostnameVerifier
                mStream = urlConnection.inputStream
                mSource = Source(mStream!!)

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mStream?.close()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        mSource!!.fullSequentialParse()
        val table = mSource.getAllElements("table")

        for (i in table.indices) {
            if ((table[i] as Element).getAttributeValue("class") == "tbl_type3") {
                val tbody = (table[i] as Element).getAllElements("tbody")
                val tr = (tbody[0] as Element).getAllElements("tr")
                val title = (tr[2] as Element).getAllElements("th")

                if ((title[0] as Element).content.toString() == "식재료") {
                    val tdMeal = (tr[1] as Element).getAllElements("td")

                    for (j in 0..6) {
                        content[j] = (tdMeal[j] as Element).content.toString()
                        content[j] = content[j]!!.replace("<br />", "\n")
                    }

                    break
                }

                for (index in content.indices) {
                    content[index] = null.toString()
                }

                break
            }
        }

        return content
    }

    fun isMealCheck(meal: String?): Boolean {
        return "" == meal || " " == meal || meal == null || meal == "null"
    }
}