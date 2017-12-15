package com.mecasut.log;

import org.apache.log4j.Logger;

/**
 * @author Danny
 */
public class RegistroLog {

    private Logger log;

    public RegistroLog(Class nombreClase, String message) {
        System.err.println("oo " + nombreClase);
        // if(nombreClase.getClass()==true){
        //}
        log = Logger.getLogger(nombreClase);
        if (log.isTraceEnabled()) {
            log.trace("mensaje de trace");
        }
        if (log.isDebugEnabled()) {
            log.debug("mensaje de debug");
        }

        if (log.isInfoEnabled()) {
            log.info("mensaje de info99");
        }
        log.warn(message);

    }

    public void warn(String message) {
    }
//        PropertyConfigurator.configure(this.getClass().getResource("/lib/log4j.properties"));
//    public static void main(String[] args) {
//        if (log.isTraceEnabled()) {
//            log.trace("mensaje de trace");
//        }
//        if (log.isDebugEnabled()) {
//            log.debug("mensaje de debug");
//        }
//
//        if (log.isInfoEnabled()) {
//            log.info("mensaje de info99");
//        }
//
//        log.error("mensaje de error");
//        log.fatal("mensaje de fatal");
//    }
}
