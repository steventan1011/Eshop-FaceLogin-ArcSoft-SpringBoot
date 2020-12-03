package com.buaa.hci.tools;


import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.buaa.hci.mapper.UserMapper;
import com.buaa.hci.pojo.User;
import com.buaa.hci.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

@Slf4j
public class getFace {
    //从官网获取
    private static String appId = "";
    private static String sdkKey = "";

    public static String faceconfig;
    public static String face1;   // tmp文件夹内的 登录用
    public static String face2;   // 遍历所有存储的图片
    public static String face1Name;

    static {
        try {
            faceconfig = ResourceUtils.getURL("src\\main\\resources\\static\\faceImage\\libs").getPath();
            face1 = ResourceUtils.getURL("src\\main\\resources\\static\\faceImage\\tmp\\facetest.png").getPath();
            face2 = ResourceUtils.getURL("src\\main\\resources\\static\\faceImage\\facetest.png").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int i = 2;

    public getFace() {
    }

    public getFace(String face1) throws FileNotFoundException {
        this.face1 = ResourceUtils.getURL("src\\main\\resources\\static\\faceImage\\tmp").getPath() + "\\" + face1;
        this.face1Name = face1;
    }

    private ApplicationContext applicationContext = SpringUtil.getApplicationContext();
    private UserMapper userMapper = applicationContext.getBean(com.buaa.hci.mapper.UserMapper.class);

    public int registerDetectFaceNum(String faceName) throws FileNotFoundException {
        String face = ResourceUtils.getURL("src\\main\\resources\\static\\faceImage").getPath() + "\\" + faceName;

        FaceEngine faceEngine = new FaceEngine(faceconfig);
        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);
        log.info(String.valueOf(errorCode));
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }
        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }
        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }

        //人脸检测1
        ImageInfo imageInfo = getRGBData(new File(face));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        System.out.println(faceInfoList);
        return faceInfoList.size();

    }

    public HashMap<String, Object> faceCompare() throws FileNotFoundException {

        HashMap<String, Object> result = new HashMap<>();
        result.put("flag", false);

        FaceEngine faceEngine = new FaceEngine(faceconfig);
        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);
        log.info(String.valueOf(errorCode));
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }
        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }
        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }

        //人脸检测1
        ImageInfo imageInfo = getRGBData(new File(face1));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        System.out.println(faceInfoList);
        result.put("faceNum", faceInfoList.size());
        if (faceInfoList.size() == 0 || faceInfoList.size() > 1)
            return result;

        //特征提取1
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

        // 遍历所有图片
        QueryWrapper<User> userQueryWrapper = Wrappers.query();
        Integer count = userMapper.selectCount(userQueryWrapper);
        System.out.println(count);
        while (i <= count) {

            face2 = ResourceUtils.getURL("src\\main\\resources\\static\\faceImage").getPath() + "\\" + userMapper.selectById(i).getPhotoname();
            //人脸检测2
            log.info(face2);
            try {
                ImageInfo imageInfo2 = getRGBData(new File(face2));
                List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
                errorCode = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo.getImageFormat(), faceInfoList2);
                System.out.println(faceInfoList);

                //特征提取2
                FaceFeature faceFeature2 = new FaceFeature();
                errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo.getImageFormat(), faceInfoList2.get(0), faceFeature2);
                System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

                //特征比对
                FaceFeature targetFaceFeature = new FaceFeature();
                targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
                FaceFeature sourceFaceFeature = new FaceFeature();
                sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
                FaceSimilar faceSimilar = new FaceSimilar();

                errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

                // 如果两张图的相似度大于0.9
                System.out.println("相似度：" + faceSimilar.getScore());
                if (faceSimilar.getScore() > 0.9) {
                    result.put("flag", true);
                    result.put("similarity", faceSimilar.getScore());
                    result.put("face1", face1Name);
                    result.put("face2", userMapper.selectById(i).getPhotoname());

                    // 通过id获取email
                    String email = userMapper.selectById(i).getEmail();
                    result.put("email", email);


                    // 设置活体测试
                    errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
                    // 人脸属性检测
                    FunctionConfiguration configuration = new FunctionConfiguration();
                    configuration.setSupportAge(true);
                    configuration.setSupportFace3dAngle(true);
                    configuration.setSupportGender(true);
                    configuration.setSupportLiveness(true);
                    errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);


                    // 性别检测
                    // 性别，未知性别=-1 、男性=0 、女性=1
                    List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
                    errorCode = faceEngine.getGender(genderInfoList);
                    System.out.println("性别：" + genderInfoList.get(0).getGender());
                    result.put("gender", genderInfoList.get(0).getGender());

                    // 年龄检测
                    // 0:未知; >0:年龄值
                    List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
                    errorCode = faceEngine.getAge(ageInfoList);
                    System.out.println("年龄：" + ageInfoList.get(0).getAge());
                    result.put("age", ageInfoList.get(0).getAge());

                    // 3D信息检测
                    // 偏航角, 横滚角, 俯仰角
                    List<Face3DAngle> face3DAngleList = new ArrayList<Face3DAngle>();
                    errorCode = faceEngine.getFace3DAngle(face3DAngleList);
                    System.out.println("3D角度：" + face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());
                    result.put("3D", face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());

                    // 活体检测
                    // RGB活体值，未知=-1 、非活体=0 、活体=1、超出人脸=-2
                    List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
                    errorCode = faceEngine.getLiveness(livenessInfoList);
                    System.out.println("活体：" + livenessInfoList.get(0).getLiveness());
                    result.put("livenessInfo", livenessInfoList.get(0).getLiveness());

                    return result;
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }

            i++;
        }
        return result;
    }
}
