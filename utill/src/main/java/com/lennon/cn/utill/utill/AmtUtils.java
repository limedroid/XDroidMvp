package com.lennon.cn.utill.utill;

import java.math.BigDecimal;

public class AmtUtils {

    /**
     * 功能描述：金额字符串转换：单位分转成单元
     * <p>
     * 传入需要转换的金额字符串
     *
     * @return 转换后的金额字符串
     */
    public static String fenToYuan(Object o) {
        if (o == null) {
            return "0.00";
        }
        String s = o.toString();
//        if ("".equals(s)) {
//            return "0.00";
//        }
        if (s.contains(".")) {
            s = s.substring(0, s.indexOf("."));
        }
        int len = -1;
        StringBuilder sb = new StringBuilder();
        if (s != null && s.trim().length() > 0 && !"null".equalsIgnoreCase(s)) {
            s = removeZero(s);
            if (s != null && s.trim().length() > 0 && !"null".equalsIgnoreCase(s)) {
                len = s.length();
                int tmp = s.indexOf("-");
                if (tmp >= 0) {
                    if (len == 2) {
                        sb.append("-0.0").append(s.substring(1));
                    } else if (len == 3) {
                        sb.append("-0.").append(s.substring(1));
                    } else {
                        sb.append(s.substring(0, len - 2)).append(".").append(s.substring(len - 2));
                    }
                } else {
                    if (len == 1) {
                        sb.append("0.0").append(s);
                    } else if (len == 2) {
                        sb.append("0.").append(s);
                    } else {
                        sb.append(s.substring(0, len - 2)).append(".").append(s.substring(len - 2));
                    }
                }
            } else {
                sb.append("0.00");
            }
        } else {
            sb.append("0.00");
        }
        return sb.toString();
    }

    /**
     * 功能描述：金额字符串转换：单位元转成单分
     * <p>
     * 传入需要转换的金额字符串
     *
     * @return 转换后的金额字符串
     */
    public static String yuanToFen(Object o) {
        if (o == null) {
            return "0";
        }
        String s = o.toString();
        int posIndex = -1;
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (s != null && s.trim().length() > 0 && !"null".equalsIgnoreCase(s)) {
            posIndex = s.indexOf(".");
            if (posIndex > 0) {
                int len = s.length();
                if (len == posIndex + 1) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append("00");
                } else if (len == posIndex + 2) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 2)).append("0");
                } else if (len == posIndex + 3) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 3));
                } else {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 3));
                }
            } else {
                sb.append(s).append("00");
            }
        } else {
            sb.append("0");
        }
        str = removeZero(sb.toString());
        if (str != null && str.trim().length() > 0 && !"null".equalsIgnoreCase(str.trim())) {
            return str;
        } else {
            return "0";
        }
    }

    /**
     * 功能描述：去除字符串首部为"0"字符
     *
     * @param str 传入需要转换的字符串
     * @return 转换后的字符串
     */
    public static String removeZero(String str) {
        char ch;
        String result = "";
        if (str != null && str.trim().length() > 0 && !"null".equalsIgnoreCase(str.trim())) {
            try {
                for (int i = 0; i < str.length(); i++) {
                    ch = str.charAt(i);
                    if (ch != '0') {
                        result = str.substring(i);
                        break;
                    }
                }
            } catch (Exception e) {
                result = "";
            }
        } else {
            result = "";
        }
        return result;

    }

    /**
     * 金额（分)
     *
     * @param rate 手续费v
     *             小数点后位数
     * @return
     */
    public static String getSettleAmt(String amountfen, double rate) {
        String feeAmtStr = String.valueOf(Double.valueOf(AmtUtils.fenToYuan(amountfen)) * rate);
        BigDecimal mData = new BigDecimal(feeAmtStr).setScale(2, BigDecimal.ROUND_HALF_UP);
        String resultAmt = AmtUtils.yuanToFen(String.valueOf(mData));
        return resultAmt;
    }

    /**
     * 金额为分的格式
     */
    private static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

    /**
     * 将分为单位的转换为元并返回金额格式的字符串 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(Long amount) throws Exception {
        if (!amount.toString().matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        int flag = 0;
        String amString = amount.toString();
        if (amString.charAt(0) == '-') {
            flag = 1;
            amString = amString.substring(1);
        }
        StringBuffer result = new StringBuffer();
        if (amString.length() == 1) {
            result.append("0.0").append(amString);
        } else if (amString.length() == 2) {
            result.append("0.").append(amString);
        } else {
            String intString = amString.substring(0, amString.length() - 2);
            for (int i = 1; i <= intString.length(); i++) {
                result.append(intString.substring(intString.length() - i, intString.length() - i + 1));
            }
            result.reverse().append(".").append(amString.substring(amString.length() - 2));
        }
        if (flag == 1) {
            return "-" + result.toString();
        } else {
            return result.toString();
        }
    }

    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(String amount) throws Exception {
        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        return String.format("%.2f", BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)));
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount
     * @return
     */
    public static String changeY2F(Long amount) {
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();
    }

    /**
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
     *
     * @param amount
     * @return
     */
    public static String changeY2F(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", "");
        //处理包含, ￥ 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        long amLong = 0L;
        if (index == -1) {
            amLong = Long.parseLong(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.parseLong((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.parseLong((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.parseLong((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return Long.toString(amLong);
    }

}
