package org.example.connection.scylla;

import com.datastax.driver.core.Session;
import org.example.connection.AppParameters;

public class ScyllaQuery {

    public static void savedOffset(String appName, String topic, int partition, long offset, Session sessionScylla) {
            sessionScylla.execute("UPDATE "+ AppParameters.SCYLLA_KEYSPACES+".offset_value SET offset =  " + offset + " where partition = " + partition + " and topic = '" + topic +"' and appname = '" + appName + "'");

    }

        public static void errorSys (String appName,String topic ,Integer partition ,Long offset,String e,Session sessionScylla) {
        sessionScylla.execute("UPDATE "+ AppParameters.SCYLLA_KEYSPACES+".errorsys SET  error = '"+e+"' where partition = " + partition + " and offset= "+offset+" and topic = '"+ topic +"' and appname = '"+ appName +"'");
    }
}
