package com.ephemeralin.z360.stemming;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.*;

@Log4j2
public class Stemmer {
    private Properties properties;

    private Stemmer() {
        try {
            InputStream is = getClass().getResourceAsStream("/properties.properties");
            this.properties = new Properties();
            this.properties.load(is);
        } catch (Exception e) {
            log.error("Properties file was not found!", e);
        }
    }

    public static class SingletonHolder {
        public static final Stemmer instance = new Stemmer();
    }

    public static Stemmer getInstance() {
        return SingletonHolder.instance;
    }

    public List<String> stem(String input) throws IOException, InterruptedException {
        String pathToMystem = properties.getProperty("mystem.path");
        String[] commands = new String[]{pathToMystem, "-n", "--weight", "--eng-gr", "--format", "json", "--generate-all"};
        Process process = Runtime.getRuntime().exec(commands);
//        process.waitFor();

//        p.waitFor();
//        BufferedReader reader =
//                new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//        String line = "";
//        StringBuilder sb = new StringBuilder();
//        while ((line = reader.readLine())!= null) {
//            sb.append(line + "\n");
//        }


        PrintWriter pw = new PrintWriter(process.getOutputStream());
        pw.write(input);

        pw.close();

        List<String> result = new ArrayList<String>();
        InputStream in = process.getInputStream();
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine())
            result.add(sc.nextLine().trim());
        sc.close();

        return result;
    }

}
