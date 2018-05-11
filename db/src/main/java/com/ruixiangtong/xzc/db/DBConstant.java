package com.ruixiangtong.xzc.db;

/**
 * Created by apple on 15/7/9.
 */
public class DBConstant {

    /**
     * 数据库名称
     */
    public static final String DBNAME = "superVIPMerchant.db";

    /**
     * CREATE TABLE
     */
    public static final String CREATETABLE = "CREATE TABLE ";

    /**
     * INTEGER PRIMARY KEY AUTOINCREMENT ,
     */
    public static final String PRIVATEKEY = " INTEGER PRIMARY KEY AUTOINCREMENT ,";

    /**
     * TEXT ,
     */
    public static final String TEXTTYPE = " TEXT ,";

    /**
     * TEXT
     */
    public static final String TEXTTYPE_END = " TEXT ";

    public static final String TEXTTYPE_NOCASE = " TEXT COLLATE NOCASE,";

    public static final String TEXTTYPE_NOCASE_END = " TEXT COLLATE NOCASE ";

    /**
     * integer ,
     */
    public static final String INTEGERTYPE = " integer ,";

    /**
     * integer
     */
    public static final String INTEGERTYPE_END = " integer ";

    /**
     * long ,
     */
    public static final String LONGTYPE = " long , ";

    /**
     * long
     */
    public static final String LONGTYPE_END = " long ";

    public static final String NOT_NULL = " not null ";
    public static final String NOT_NULL_END = " not null,";

    public static final String DEFAULT = " default ";
    public static final String DEFAULT_END = " default,";
}
