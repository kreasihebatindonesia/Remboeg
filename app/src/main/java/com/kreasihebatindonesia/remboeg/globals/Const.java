package com.kreasihebatindonesia.remboeg.globals;

/**
 * Created by IT DCM on 06/11/2017.
 */

public class Const {
    public static final String HOST_ADDRESS = "http://192.168.100.11/event";
    //public static final String HOST_ADDRESS = "http://10.0.2.2/event";

    public static final String METHOD_CITY = HOST_ADDRESS + "/api/location";
    public static final String METHOD_CITY_GPS = HOST_ADDRESS + "/api/location/gps";

    public static final String METHOD_SEARCH_EVENT = HOST_ADDRESS + "/api/search/events";
    public static final String METHOD_SEARCH_JOB = HOST_ADDRESS + "/api/search/jobs";

    //EVENT
    public static final String METHOD_EVENT_ACTIVE = HOST_ADDRESS + "/api/events/active";
    public static final String METHOD_EVENT_INACTIVE = HOST_ADDRESS + "/api/events/inactive";
    public static final String METHOD_EVENT_DETAIL_ACTIVE = HOST_ADDRESS + "/api/events/active/id";
    public static final String METHOD_EVENT_DETAIL_INACTIVE = HOST_ADDRESS + "/api/events/inactive/id";
    public static final String METHOD_EVENT_SET_VIEW = HOST_ADDRESS + "/api/events/view/id";

    //JOB
    public static final String METHOD_JOB_ACTIVE = HOST_ADDRESS + "/api/jobs/active";
    public static final String METHOD_JOB_DETAIL_ACTIVE = HOST_ADDRESS + "/api/jobs/active/id";
    public static final String METHOD_JOB_DETAIL_INACTIVE = HOST_ADDRESS + "/api/jobs/inactive/id";

    //NEARBY
    public static final String METHOD_NEARBY_EVENT = HOST_ADDRESS + "/api/nearby/events";
    public static final String METHOD_NEARBY_JOB = HOST_ADDRESS + "/api/nearby/jobs";

    public static final String METHOD_PROMO = HOST_ADDRESS + "/api/promo";

    public static final String URL_UPLOADS = HOST_ADDRESS + "/uploads";


    public static final int DUMMY_USER_ID = 1;
    public static int DUMMY_LOCATION_ID = 0;
}
