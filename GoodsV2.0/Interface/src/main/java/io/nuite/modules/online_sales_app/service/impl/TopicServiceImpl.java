package io.nuite.modules.online_sales_app.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.common.utils.FileUtils;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.modules.online_sales_app.dao.TopicDao;
import io.nuite.modules.online_sales_app.entity.TopicEntity;
import io.nuite.modules.online_sales_app.service.TopicService;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicDao, TopicEntity> implements TopicService {

    @Autowired
    private ConfigUtils configUtils;

    @Autowired
    private TopicDao topicDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params, Integer brandSeq) {
        String topicId = (String) params.get("topicId");

        Page<TopicEntity> page = this.selectPage(
                new Query<TopicEntity>(params).getPage(),
                new EntityWrapper<TopicEntity>().eq("Brand_Seq", brandSeq)
                        .like(StringUtils.isNotBlank(topicId), "TopicName", topicId)
        );
        List<TopicEntity> records = page.getRecords();
        for (TopicEntity topicEntity : records) {
            setTopicImageSrc(topicEntity);
        }
        return new PageUtils(page);
    }

    @Override
    public TopicEntity selectOneBySeq(Long seq) {
        TopicEntity topicEntity = this.selectById(seq);
        setTopicImageSrc(topicEntity);
        return topicEntity;
    }

    private void setTopicImageSrc(TopicEntity topicEntity) {
        String imageName = topicEntity.getTopicImage();
        if (imageName != null && !imageName.isEmpty()) {
            String topicPic = configUtils.getPictureBaseUrl() + "/"
                    + configUtils.getOnlineSalesApp().getOnlineDir() + "/"
                    + configUtils.getOnlineSalesApp().getTopicDir() + "/"
                    + topicEntity.getBrandSeq() + "/" + imageName;
            topicEntity.setTopicImage(topicPic);
        }
    }

    @Override
    @Transactional
    public String saveTopic(TopicEntity topicEntity) throws Exception {
        String oldImage = null;
        TopicEntity oldTopicEntity = this.selectById(topicEntity.getSeq());
        if (oldTopicEntity == null) {
            oldTopicEntity = new TopicEntity();
            oldTopicEntity.setBrandSeq(topicEntity.getBrandSeq());
        }


        MultipartFile file = topicEntity.getTopicImageFile();

        String topicDir = FileUtils.getWebAppsPath() + File.separator
                + configUtils.getPictureBaseUploadProject() + File.separator
                + configUtils.getOnlineSalesApp().getOnlineDir() + File.separator
                + configUtils.getOnlineSalesApp().getTopicDir() + File.separator
                + topicEntity.getBrandSeq() + File.separator;
        if (file != null && file.getSize() > 0) {
            String fileName = FileUtils.upLoadFile(topicDir, null, file);
            oldImage = topicDir + oldTopicEntity.getTopicImage();

            oldTopicEntity.setTopicImage(fileName);
        } else {
            oldTopicEntity.setTopicImage(null);
        }

        oldTopicEntity.setTopicName(topicEntity.getTopicName());
        oldTopicEntity.setTopicType(topicEntity.getTopicType());

        this.insertOrUpdate(oldTopicEntity);

        int topicType = oldTopicEntity.getSeq();
        String topicLinks = topicEntity.getTopicLink();
        if (topicLinks != null && !topicLinks.trim().isEmpty()) {
            String[] shoesSeqArray = topicLinks.split(",");
            if (shoesSeqArray.length > 0) {
                topicDao.updateGoodsTopic(topicType, Arrays.asList(shoesSeqArray));
            }
        }
        return oldImage;
    }
}
