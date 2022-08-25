package eu.andredick.tools.orlib;

import eu.andredick.scp.SCProblem;
import eu.andredick.tools.FileTools;

import java.io.*;
import java.util.Properties;

/**
 * 在本地与线上搜索Orlib 库中的问题实例名称，<br>
 * 并调用相关脚本进行解析。
 */
public class OrlibConverter {

    public static String orlibUrl = "http://people.brunel.ac.uk/~mastjjb/jeb/orlib/files/";
    /**
     * 本地scp数据集所在位置
     * 后缀为 .txt
     */
    public static String resourcePathLocalOrlibOrigins = "resources\\orlib\\origins\\";
    /**
     * scp数据集所在位置
     * 后缀为 .jar
     */
    public static String resourcePathPackageOrlibOrigins = "/resources/orlib/origins/";
    public static String resourcePathPackageOrlibMPS = "/resources/orlib/mps/";


    public static String[][] scp4 = {{"SCP4"}, {"scp41", "scp42", "scp43", "scp44", "scp45", "scp46", "scp47", "scp48", "scp49", "scp410"}};
    public static String[][] scp5 = {{"SCP5"}, {"scp51", "scp52", "scp53", "scp54", "scp55", "scp56", "scp57", "scp58", "scp59", "scp510"}};
    public static String[][] scp6 = {{"SCP6"}, {"scp61", "scp62", "scp63", "scp64", "scp65"}};
    public static String[][] rail = {{"RAIL"}, {"rail507", "rail516", "rail582", "rail2536", "rail2586", "rail4284", "rail4872"}};
    public static String[][] scpa = {{"SCPA"}, {"scpa1", "scpa2", "scpa3", "scpa4", "scpa5"}};
    public static String[][] scpb = {{"SCPB"}, {"scpb1", "scpb2", "scpb3", "scpb4", "scpb5"}};
    public static String[][] scpc = {{"SCPC"}, {"scpc1", "scpc2", "scpc3", "scpc4", "scpc5"}};
    public static String[][] scpd = {{"SCPD"}, {"scpd1", "scpd2", "scpd3", "scpd4", "scpd5"}};
    public static String[][] scpe = {{"SCPE"}, {"scpe1", "scpe2", "scpe3", "scpe4", "scpe5"}};

    public static String[][] scpnre = {{"SCPNRE"}, {"scpnre1", "scpnre2", "scpnre3", "scpnre4", "scpnre5"}};
    public static String[][] scpnrf = {{"SCPNRF"}, {"scpnrf1", "scpnrf2", "scpnrf3", "scpnrf4", "scpnrf5"}};
    public static String[][] scpnrg = {{"SCPNRG"}, {"scpnrg1", "scpnrg2", "scpnrg3", "scpnrg4", "scpnrg5"}};
    public static String[][] scpnrh = {{"SCPNRH"}, {"scpnrh1", "scpnrh2", "scpnrh3", "scpnrh4", "scpnrh5"}};

    public static String[][] scpclr = {{"SCPCLR"}, {"scpclr10", "scpclr11", "scpclr12", "scpclr13"}};
    public static String[][] scpcyc = {{"SCPCYC"}, {"scpcyc06", "scpcyc07", "scpcyc08", "scpcyc09", "scpcyc10", "scpcyc11"}};


    public static String[][][] instanceSets = {scp4, scp5, scp6, scpa, scpb, scpc, scpd, scpe, scpnre, scpnrf, scpnrg, scpnrh, scpclr, scpcyc, rail};


    public static SCProblem getProblem(String instanceName) {
        SCProblem problem = null;

        String setName = findSetNameForInstance(instanceName);
        if (setName != null) {
            AbstractConverter converter = findConverter(setName);
            if (converter != null) {
                InputStream inputStream = findStreamForInstance(instanceName);
                if (inputStream != null) {
                    problem = convertProblemFromStream(inputStream, converter);
                } else System.out.println("OrlibConverter：无法创建 InputStream - " + instanceName);
            } else System.out.println("OrlibConverter： 找不到 SetName 的转换器 - " + setName);
        } else System.out.println("OrlibConverter：未列出实例名称 - " + instanceName);


        return problem;
    }

    /**
     * Orlib 实例的文件具有不同的格式
     * 此方法将格式转换器映射到抽象问题集的每个名称
     *
     * @param setName: 抽象问题集名称
     * @return 合适的转换器
     */
    private static AbstractConverter findConverter(String setName) {
        AbstractConverter converter = null;
        setName = setName.toUpperCase();
        switch (setName) {
            case "RAIL":
                converter = new ConverterRail();
                break;
            default:
                converter = new ConverterStandard();
        }
        return converter;
    }

    private static InputStream findStreamForInstance(String instanceName) {
        InputStream inputStream = null;
        if (inputStream == null) {
            inputStream = FileTools.getStreamFromPackageFile(resourcePathPackageOrlibOrigins, instanceName + ".txt");
        }
        if (inputStream == null)
            System.out.println("OrlibConverter: 未找到 JAR 文件 - " + resourcePathPackageOrlibOrigins + instanceName + ".txt");

        if (inputStream == null) {
            File file = new File(resourcePathLocalOrlibOrigins + instanceName + ".txt");
            if (FileTools.existsLocalFile(file)) {
                inputStream = FileTools.getStreamFromLocalFile(file);
            }
        }
        if (inputStream == null)
            System.out.println("OrlibConverter：找不到本地文件 - " + resourcePathLocalOrlibOrigins + instanceName + ".txt");

        if (inputStream == null) {
            File localResPath = new File(resourcePathLocalOrlibOrigins);
            //从线上下载数据集，并保存到本地
            if (FileTools.existsLocalFolder(localResPath) || localResPath.mkdirs()) {
                if (FileTools.existsOnlineFile(orlibUrl + instanceName + ".txt")) {
                    File destFile = new File(resourcePathLocalOrlibOrigins + instanceName + ".txt");
                    FileTools.downloadFile(orlibUrl + instanceName + ".txt", destFile);
                    inputStream = FileTools.getStreamFromLocalFile(destFile);
                } else if (FileTools.existsOnlineFile(orlibUrl + instanceName + ".gz")) {
                    File gzFile = new File(resourcePathLocalOrlibOrigins + instanceName + ".gz");
                    FileTools.downloadFile(orlibUrl + instanceName + ".gz", gzFile);
                    File txtFile = new File(resourcePathLocalOrlibOrigins + instanceName + ".txt");
                    FileTools.extractFileFromGZIP(gzFile, txtFile);
                    inputStream = FileTools.getStreamFromLocalFile(txtFile);
                }
            } else System.out.println("OrlibConverter: 无法创建本地文件夹 - " + localResPath);

        }
        return inputStream;
    }

    public static SCProblem convertProblemFromStream(InputStream in, AbstractConverter converter) {
        SCProblem problem = null;
        try {
            problem = converter.convert(in);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return problem;
    }

    public static String findSetNameForInstance(String instanceName) {
        String setName = null;
        for (String[][] pSet : instanceSets) {
            for (String iName : pSet[1]) {
                //忽略大小写进行比较，如果相等，则返回 0
                if (iName.toUpperCase().compareToIgnoreCase(instanceName) == 0) {
                    return pSet[0][0];
                }
            }
        }
        return null;
    }

    public static String[] getInstanceNamesOfProblemSet(String problemSetName) {
        for (String[][] pSet : instanceSets) {
            if (pSet[0][0].compareToIgnoreCase(problemSetName) == 0) {
                return pSet[1];
            }
        }
        return null;
    }

    public static String[] getNamesOfInstanceSets(){
        String[] names = new String[instanceSets.length];
        for (int i = 0; i < names.length; i++){
            names[i] = instanceSets[i][0][0];
        }
        return names;
    }


    public static Float getExactValue(String instanceName) {
        Properties values = getExactValues();
        String property = values.getProperty(instanceName);
        Float exactValue = (property == null) ? null : Float.valueOf(property);
        return exactValue;
    }

    public static void saveExactValues(Properties properties) {
        File file = new File(resourcePathLocalOrlibOrigins + "exactvalues.properties");

        properties.putAll(properties);
        try {
            properties.storeToXML(new FileOutputStream(file), "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getExactValues() {
        Properties properties = new Properties();
        InputStream inputStream = null;
        File file = new File(resourcePathLocalOrlibOrigins + "exactvalues.properties");
        if (FileTools.existsLocalFile(file)) {
            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            inputStream = FileTools.getStreamFromPackageFile(resourcePathPackageOrlibOrigins, "exactvalues.properties");
        }
        try {
            properties.loadFromXML(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
