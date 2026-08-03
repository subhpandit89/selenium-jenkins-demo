package com.automationexcellence.testManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public final class ExtentReportManager {
    
    private ExtentReportManager(){}

    private static volatile ExtentReports report;

    public static void createExtentReport(){
        if(report==null){
            synchronized(ExtentReportManager.class){
                if(report==null){
                    String userDirectory = System.getProperty("user.dir");
                    String dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

                    ExtentSparkReporter reporter = new ExtentSparkReporter(userDirectory + "/extentReports/Reports_" + dateFormatter + ".html");
                    reporter.config().setDocumentTitle("Regression and Smoke Reports");
                    reporter.config().setReportName("Automation Suite Execution");
                    reporter.config().setTheme(Theme.DARK);

                    report = new ExtentReports();
                    report.attachReporter(reporter);
                    report.setSystemInfo("OS", "Windows");
                    report.setSystemInfo("Browser", "Chrome");
                    report.setSystemInfo("Suite", "Smoke & Regression");
                }
            }
        }
    }

    public static ExtentReports getExtentReports(){
        return report;
    }

    public static void flushReport(){
        if(report!=null){
            report.flush();
        }
    }

}
