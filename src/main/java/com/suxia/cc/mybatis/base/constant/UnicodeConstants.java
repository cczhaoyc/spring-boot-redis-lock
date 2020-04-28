
package com.suxia.cc.mybatis.base.constant;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * unicode字符集常量
 */
public class UnicodeConstants {

    /**
     * Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set
     */
    public static final Charset US_ASCII = StandardCharsets.US_ASCII;
    /**
     * ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1
     */
    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
    /**
     * Eight-bit UCS Transformation Format
     */
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    /***
     * 字符集"UTF-8"
     */
    public static final String UTF8 = UTF_8.name();

    /***
     * 字符集"US-ASCII"
     */
    public static final String USASCII = US_ASCII.name();

    /**
     * 字符集"ISO-8859-1"
     */
    public static final String ISO8859_1 = ISO_8859_1.name();
}
