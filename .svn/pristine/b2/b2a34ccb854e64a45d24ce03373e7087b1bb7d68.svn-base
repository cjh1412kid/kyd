package io.nuite.common.utils;

/**
 * 常量
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;

    /**
     * 菜单类型
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 账号的销售数据类型
     */
    public enum UserSaleType {
        /**
         * 工厂
         */
        FACTORY(1),
        /**
         * 贴牌商
         */
        OEM(2),
        /**
         * 批发商
         */
        WHOLESALER(3),
        /**
         * 直营店
         */
        STORE(4);

        private int value;

        UserSaleType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 账号的销售数据类型
     */
    public enum UserAttachType {
        /**
         * 工厂
         */
        FACTORY(1, "工厂"),
        /**
         * 分公司
         */
        OFFICE(2, "分公司"),
        /**
         * 代理
         */
        AGENT(3, "代理"),
        /**
         * 工厂子账号
         */
        SUBACCOUNT(4, "工厂子账号");

        private int value;
        private String name;

        UserAttachType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 轮播图关联类型
     */
    public enum SowingLinkType {
        SINGLEPRODUCT(1, "关联产品"), CATEGORY(2, "关联分类");

        private int value;
        private String name;

        SowingLinkType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    public enum TypeOfStore {
        ORDINARY(1, "普通门店"), COLLECTION(2, "集合门店");

        private int value;
        private String name;

        TypeOfStore(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    public enum CommodityType {
        ISHOTSALE(1, "爆款"), ISNEWEST(2, "新品");

        private int value;
        private String name;

        CommodityType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    public enum AccountPrefix {
        QIANGREN(1, "qr");

        private int id;
        private String value;

        AccountPrefix(int id, String value) {
            this.id = id;
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

    }

}
