package com.charlie.sawl.meal;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.io.InputStream;
import java.util.Objects;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class MealLibrary {

    private static HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public static String[] getDateNew(String year, String month, String day) {

        String[] date = new String[7];
        String url = "https://stu.goe.go.kr/sts_sci_md01_001.do?schulCode=J100005284&schulCrseScCode="
                + "4&schulKndScCode=04&schMmealScCode=" + "0&schYmd=" + year + "." + month + "." + day;

        return getDateNewSub(date, url);
    }

    private static String[] getDateNewSub(String[] date, String url) {
        Source mSource = null;

        try {
            URL mUrl = new URL(url);

            InputStream mStream = null;

            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) mUrl.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setHostnameVerifier(hostnameVerifier);
                mStream = urlConnection.getInputStream();
                mSource = new Source(mStream);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mStream != null) {
                    mStream.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(mSource).fullSequentialParse();
        List<?> table = mSource.getAllElements("table");

        for (int i = 0; i < table.size(); i++) {
            if (((Element) table.get(i)).getAttributeValue("class").equals("tbl_type3")) {
                List<?> tr = ((Element) table.get(i)).getAllElements("tr");
                List<?> th = ((Element) tr.get(0)).getAllElements("th");

                for (int j = 0; j < 7; j++) {
                    date[j] = ((Element) th.get(j + 1)).getContent().toString();
                }

                break;
            }
        }

        return date;
    }

    public static String[] getMealNew(String schoolMealScCode, String year, String month, String day) {

        String[] content = new String[7];
        String url = "https://stu.goe.go.kr/sts_sci_md01_001.do?schulCode=J100005284&schulCrseScCode="
                + "4&schulKndScCode=04&schMmealScCode=" + schoolMealScCode + "&schYmd=" + year + "." + month + "." + day;

        return getMealNewSub(content, url);
    }

    private static String[] getMealNewSub(String[] content, String url) {
        Source mSource = null;

        try {
            URL mUrl = new URL(url);

            InputStream mStream = null;

            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) mUrl.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setHostnameVerifier(hostnameVerifier);
                mStream = urlConnection.getInputStream();
                mSource = new Source(mStream);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mStream != null) {
                    mStream.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        mSource.fullSequentialParse();
        List<?> table = mSource.getAllElements("table");

        for (int i = 0; i < table.size(); i++) {
            if (((Element) table.get(i)).getAttributeValue("class").equals("tbl_type3")) {
                List<?> tbody = ((Element) table.get(i)).getAllElements("tbody");
                List<?> tr = ((Element) tbody.get(0)).getAllElements("tr");
                List<?> title = ((Element) tr.get(2)).getAllElements("th");

                if (((Element) title.get(0)).getContent().toString().equals("식재료")) {
                    List<?> tdMeal = ((Element) tr.get(1)).getAllElements("td");

                    for (int j = 0; j < 7; j++) {
                        content[j] = ((Element) tdMeal.get(j)).getContent().toString();
                        content[j] = content[j].replace("<br />", "\n");
                    }

                    break;
                }

                for (int index = 0; index < content.length; index++) {
                    content[index] = null;
                }

                break;
            }
        }

        return content;
    }

    public static boolean isMealCheck(String meal) {
        return ("".equals(meal) || " ".equals(meal) || meal == null);
    }
}