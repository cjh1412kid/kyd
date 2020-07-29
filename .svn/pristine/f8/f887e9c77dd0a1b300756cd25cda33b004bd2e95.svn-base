package io.nuite.modules.system.service.impl.online_sale;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.common.exception.RRException;
import io.nuite.common.utils.FileUtils;
import io.nuite.common.utils.PageInfo;
import io.nuite.common.utils.PageUtils;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.dao.online_sale.OlsSowingDao;
import io.nuite.modules.system.entity.online_sale.OlsSowingEntity;
import io.nuite.modules.system.service.online_sale.OlsSowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class OlsSowingServiceImpl extends ServiceImpl<OlsSowingDao, OlsSowingEntity> implements OlsSowingService {
    @Autowired
    private ConfigUtils configUtils;

    @Autowired
    private OlsSowingDao olsSowingDao;

    public PageUtils sowingPageList(Integer brandSeq) {
        List<OlsSowingEntity> sowingMapList;

        EntityWrapper<OlsSowingEntity> ew = new EntityWrapper<>();
        ew.eq("Brand_Seq", brandSeq);
        sowingMapList = selectList(ew);
        for (OlsSowingEntity olsSowingEntity : sowingMapList) {
            String imageSrc = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/"
                    + configUtils.getSowingMap() + "/" + olsSowingEntity.getBrandSeq() + "/"
                    + olsSowingEntity.getImage();
            olsSowingEntity.setImage(imageSrc);
        }
        return new PageUtils(sowingMapList, sowingMapList.size(), 1, 1);
    }

    @Override
    public PageUtils olsGoodsList(Integer brandSeq, PageInfo pageInfo) {
        Page<Map<String, Object>> page = new Page<>(pageInfo.getNowpage(), pageInfo.getSize());
        page.setAsc("asc".equalsIgnoreCase(pageInfo.getOrder()));
        page.setOrderByField(pageInfo.getSort());
        pageInfo.getCondition().put("brandSeq", brandSeq);
        List<Map<String, Object>> list = olsSowingDao.selectOlsGoodsList(page, pageInfo.getCondition());
        for (Map<String, Object> maps : list) {
            String goodId = (String) maps.get("goodID");
            String img1 = (String) maps.get("img1");
            String imageUrl = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes()
                    + "/" + goodId + "/" + img1;
            maps.replace("img1", imageUrl);

        }
        return new PageUtils(list, page.getTotal(), pageInfo.getSize(), pageInfo.getNowpage());
    }

    @Override
    public OlsSowingEntity selectSowing(Integer seq) {
        OlsSowingEntity olsSowingEntity = selectById(seq);
        String imageSrc = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/"
                + configUtils.getSowingMap() + "/" + olsSowingEntity.getBrandSeq() + "/"
                + olsSowingEntity.getImage();
        olsSowingEntity.setImage(imageSrc);
        return olsSowingEntity;
    }

    @Transactional
    @Override
    public void saveSowing(OlsSowingEntity olsSowingEntity) {
        MultipartFile imageFile = olsSowingEntity.getImageFile();
        if (imageFile == null || imageFile.getSize() <= 0) {
            throw new RRException("未上传图片");
        }

        if (olsSowingEntity.getType() == null || olsSowingEntity.getLinkSeq() == null) {
            throw new RRException("未选择关联信息");
        }
        String imageGoodsDir = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + File.separator
                + configUtils.getBaseDir() + File.separator + configUtils.getSowingMap() + File.separator
                + olsSowingEntity.getBrandSeq() + File.separator;
        try {
            String fileName = FileUtils.upLoadFile(imageGoodsDir, null, imageFile);
            olsSowingEntity.setImage(fileName);
        } catch (IOException e) {
            throw new RRException("上传图片失败");
        }
        insert(olsSowingEntity);
    }

    @Transactional
    @Override
    public void updateSowing(Integer brandSeq, OlsSowingEntity olsSowingEntity) {
        MultipartFile imageFile = olsSowingEntity.getImageFile();
        if (imageFile != null && imageFile.getSize() > 0) {
            String imageGoodsDir = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + File.separator
                    + configUtils.getBaseDir() + File.separator + configUtils.getSowingMap() + File.separator
                    + olsSowingEntity.getBrandSeq() + File.separator;
            try {
                String fileName = FileUtils.upLoadFile(imageGoodsDir, null, imageFile);
                olsSowingEntity.setImage(fileName);
            } catch (IOException e) {
                throw new RRException("上传图片失败");
            }
        }

        updateById(olsSowingEntity);
    }
}
