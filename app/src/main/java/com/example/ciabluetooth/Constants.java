package com.example.ciabluetooth;

public class Constants {
    public static final int MILLISECOND = 1000;

    // expandable
    public static final int PERMISSIONS_DATA = 0;
    public static final int EXPANDABLE_PARENT = 0;
    public static final int EXPANDABLE_CHILD = 1;

    // sharedPreferences
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DEVICE_SN = "deviceSN";
    public static final String DEVICE_ADDRESS = "deviceAddress";
    public static final String BRUSH_SN = "brushSN";
    public static final String COOLER_SN = "coolerSN";
    public static final String PUFF_SN = "puffSN";
    public static final String SILICON_SN = "siliconSN";
//    public static final String LAST_DATE = "last_date";
//
//    public static final String LAST_BRUSH = "last_brush";
//    public static final String LAST_COOLER = "last_cooler";
//    public static final String LAST_PUFF = "last_puff";
//    public static final String LAST_SILICON = "last_silicon";
//    public static final String LAST_ALL = "last_all";
//
//    public static final String THIS_BRUSH = "this_brush";
//    public static final String THIS_COOLER = "this_cooler";
//    public static final String THIS_PUFF = "this_puff";
//    public static final String THIS_SILICON = "this_silicon";
//    public static final String THIS_ALL = "this_all";


    public static final int MESSAGE_START_TOAST = 0;
    public static final int MESSAGE_MAKE_GRAPH_TIME = 1;
    public static final int MESSAGE_MAKE_GRAPH_MONTHLY = 2;
    public static final int INFO_DEVICE = 3;
    public static final int INFO_BATTERY = 4;
    public static final int INFO_USAGE_TIME = 5;
    public static final int MANUAL_START = 6;
    public static final int MANUAL_USAGE = 7;
    public static final int MANUAL_HEAD = 8;
    public static final int MESSAGE_SCAN_SHOW_LIST = 9;
    public static final int MESSAGE_SCAN_START_MAIN = 10;
    public static final int MESSAGE_SCAN_MAIN = 11;
    public static final int MESSAGE_SCAN_MAIN_UNCONN = 12;

    // dialog
    public static final String APP_FINISH_DIALOG = "app_finish_dialog";
    public static final String GRAPH_DIALOG = "graph_dialog";
    public static final int DIALOG_APP_FINISH = 100;
    public static final int DIALOG_GRAPH = 101;

    // Intent request
    public static final int INTENT_REQUEST_HEAD = 0;
    public static final int INTENT_REQUEST_INFO = 1;
    public static final int INTENT_REQUEST_MANUAL = 2;
    public static final int INTENT_REQUEST_PATTERN = 3;


    // handler
    public static final int MESSAGE_CONNECT = 0;

    public static final int MESSAGE_INTENT_BACK = 999;


    // request
    public static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    public static final int REQUEST_CONNECT = 1;
    public static final int REQUEST_ENABLE_BT = 2;
}

