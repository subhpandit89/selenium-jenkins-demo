package com.automationexcellence.testManager;

import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentTestManager {

    private ExtentTestManager(){}

    private static final ThreadLocal<ExtentTest> testLocal = new ThreadLocal<>();

    public static void initializeTest(String name, String description){
        ExtentTest test = ExtentReportManager.getExtentReports().createTest(name, description);
        testLocal.set(test);
    }

    public static ExtentTest getTest(){
        return testLocal.get();
    }

    public static void removeTest(){
        if(getTest()!=null){
            testLocal.remove();
        }
    }

    public static void main(String[] args) throws IOException{

        ExtentSparkReporter reporter = new ExtentSparkReporter("target/fragments/UnifiedReport.html");
        ExtentReports unifiedReport = new ExtentReports();
        unifiedReport.attachReporter(reporter);
        

        File targetDirectory = new File("target/fragments");
        if(targetDirectory.exists() && targetDirectory.isDirectory()){
            File[] jsonFiles = targetDirectory.listFiles((dir, name)->name.endsWith(".json"));
            for(File jsonFile : jsonFiles){
                unifiedReport.createDomainFromJsonArchive(jsonFile);
            }
        }

        unifiedReport.flush();
    }
    
}
