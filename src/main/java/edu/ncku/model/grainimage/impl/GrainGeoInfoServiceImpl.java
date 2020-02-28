package edu.ncku.model.grainimage.impl;

import edu.ncku.model.grainimage.GrainGeoInfoService;
import edu.ncku.model.grainimage.vo.GrainPointVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class GrainGeoInfoServiceImpl implements GrainGeoInfoService {
    private static final String COMMAND_PATTERN = "java -jar %s %s %s";
    private String jarPath;

    private final Logger logger = LogManager.getLogger(GrainGeoInfoServiceImpl.class);

    @Autowired
    public GrainGeoInfoServiceImpl(@Value("${geoinfo.tiff.jar}") String jarPath){
        this.jarPath = jarPath;
    }

    public GrainPointVO getOriPoint(File impPath) {
        File resultFile = new File("result.json");
        if (resultFile.exists() && !resultFile.delete())
            return new GrainPointVO(0.0, 0.0);
        try {
            String command = String.format(COMMAND_PATTERN, jarPath, impPath.getAbsolutePath(), resultFile.getAbsolutePath());
            Process proc = Runtime.getRuntime().exec(command);
            proc.exitValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GrainPointVO(0.0, 0.0);
    }
}
