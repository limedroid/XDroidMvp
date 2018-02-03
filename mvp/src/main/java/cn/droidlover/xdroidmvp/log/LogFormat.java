package cn.droidlover.xdroidmvp.log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.UnknownHostException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import cn.droidlover.xdroidmvp.kit.Kits;

/**
 * Created by wanglei on 2016/11/29.
 */

public class LogFormat {

    static final int JSON_INDENT = 4;
    static final int XML_INDENT = 4;

    private static final char VERTICAL_BORDER_CHAR = '║';

    // Length: 100.
    private static final String TOP_HORIZONTAL_BORDER =
            "╔═════════════════════════════════════════════════" +
                    "══════════════════════════════════════════════════";

    // Length: 99.
    private static final String DIVIDER_HORIZONTAL_BORDER =
            "╟─────────────────────────────────────────────────" +
                    "──────────────────────────────────────────────────";

    // Length: 100.
    private static final String BOTTOM_HORIZONTAL_BORDER =
            "╚═════════════════════════════════════════════════" +
                    "══════════════════════════════════════════════════";

    public static String formatJson(String json) {
        String formatted = null;
        if (json == null || json.length() == 0) {
            return formatted;
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jo = new JSONObject(json);
                formatted = jo.toString(JSON_INDENT);
            } else if (json.startsWith("[")) {
                JSONArray ja = new JSONArray(json);
                formatted = ja.toString(JSON_INDENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formatted;
    }

    public static String formatXml(String xml) {
        String formatted = null;
        if (xml == null || xml.trim().length() == 0) {
            return formatted;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
                    String.valueOf(XML_INDENT));
            transformer.transform(xmlInput, xmlOutput);
            formatted = xmlOutput.getWriter().toString().replaceFirst(">", ">"
                    + XPrinter.lineSeparator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatted;
    }

    public static String formatThrowable(Throwable tr) {
        if (tr == null) {
            return "";
        }
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }


    public static String formatArgs(String format, Object... args) {
        try {
            if (!Kits.Empty.check(format)) {
                return String.format(format, args);
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0, N = args.length; i < N; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(args[i]);
            }
            return sb.toString();

        } catch (Exception e) {
        }
        return "";
    }

    public static String formatBorder(String[] segments) {
        if (segments == null || segments.length == 0) {
            return "";
        }
        String[] nonNullSegments = new String[segments.length];
        int nonNullCount = 0;
        for (String segment : segments) {
            if (segment != null) {
                nonNullSegments[nonNullCount++] = segment;
            }
        }
        if (nonNullCount == 0) {
            return "";
        }

        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append(TOP_HORIZONTAL_BORDER).append(XPrinter.lineSeparator);
        for (int i = 0; i < nonNullCount; i++) {
            msgBuilder.append(appendVerticalBorder(nonNullSegments[i]));
            if (i != nonNullCount - 1) {
                msgBuilder.append(XPrinter.lineSeparator).append(DIVIDER_HORIZONTAL_BORDER)
                        .append(XPrinter.lineSeparator);
            } else {
                msgBuilder.append(XPrinter.lineSeparator).append(BOTTOM_HORIZONTAL_BORDER);
            }
        }
        return msgBuilder.toString();
    }

    private static String appendVerticalBorder(String msg) {
        StringBuilder borderedMsgBuilder = new StringBuilder(msg.length() + 10);
        String[] lines = msg.split(XPrinter.lineSeparator);
        for (int i = 0, N = lines.length; i < N; i++) {
            if (i != 0) {
                borderedMsgBuilder.append(XPrinter.lineSeparator);
            }
            String line = lines[i];
            borderedMsgBuilder.append(VERTICAL_BORDER_CHAR).append(line);
        }
        return borderedMsgBuilder.toString();
    }
}
