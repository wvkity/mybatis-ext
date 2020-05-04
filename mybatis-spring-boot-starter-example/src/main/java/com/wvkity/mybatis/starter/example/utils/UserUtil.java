package com.wvkity.mybatis.starter.example.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public final class UserUtil {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(fluent = true, chain = true)
    public static class UserInfo {
        private String userName;
        private int sex;
        private int age;
        private String email;
        private String phone;
        private String address;
        private LocalDateTime birthday;
    }

    private UserUtil() {
    }

    //百家姓
    private static final String[] SURNAME = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
            "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
            "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷",
            "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和",
            "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",
            "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季", "麻", "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭", "梅", "盛", "林", "刁", "钟",
            "徐", "邱", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝", "管", "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应",
            "宗", "丁", "宣", "贲", "邓", "郁", "单", "杭", "洪", "包", "诸", "左", "石", "崔", "吉", "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀",
            "羊", "于", "惠", "甄", "曲", "家", "封", "芮", "羿", "储", "靳", "汲", "邴", "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓", "牧", "隗", "山",
            "谷", "车", "侯", "宓", "蓬", "郗", "班", "仰", "秋", "仲", "伊", "宫", "宁", "仇", "栾", "暴", "甘", "钭", "厉", "戎", "祖", "武", "符", "刘", "景",
            "詹", "束", "龙", "叶", "幸", "司", "韶", "郜", "黎", "蓟", "溥", "印", "宿", "白", "怀", "蒲", "邰", "从", "鄂", "索", "咸", "籍", "赖", "卓", "蔺", "屠",
            "蒙", "池", "乔", "阴", "郁", "胥", "能", "苍", "双", "闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申", "扶", "堵", "冉", "宰", "郦", "雍", "却",
            "璩", "桑", "桂", "濮", "牛", "寿", "通", "边", "扈", "燕", "冀", "浦", "尚", "农", "温", "别", "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹", "习",
            "宦", "艾", "鱼", "容", "向", "古", "易", "慎", "戈", "廖", "庾", "终", "暨", "居", "衡", "步", "都", "耿", "满", "弘", "匡", "国", "寇", "广", "禄",
            "阙", "东", "欧", "殳", "沃", "利", "蔚", "越", "夔", "隆", "巩", "聂", "晁", "敖", "冷", "訾", "辛", "阚", "那", "简", "饶",
            "曾", "毋", "养", "鞠", "须", "丰", "巢", "关", "蒯", "相", "查", "后", "荆", "红", "游", "郏", "竺", "权", "逯", "盖", "益", "桓", "公", "仉",
            "督", "岳", "帅", "缑", "亢", "况", "郈", "有", "琴", "归", "海", "晋", "楚", "闫", "法", "汝", "鄢", "涂", "钦", "商", "牟", "佘", "佴", "伯", "赏", "墨",
            "哈", "谯", "篁", "年", "爱", "阳", "佟", "言", "福", "南", "火", "铁", "迟", "漆", "官", "冼", "真", "展", "繁", "檀", "祭", "密", "敬", "揭", "舜", "楼", "浑", "挚", "高", "皋", "原", "弥", "仓", "覃", "召",
            "木", "郇", "杞", "麦", "庆", "端", "邝",
            "闾", "司马", "上官", "欧阳", "诸葛", "东方", "皇甫", "太叔", "公孙", "轩辕", "令狐", "钟离", "宇文", "长孙", "慕容", "司徒", "司空",
            "公良", "拓跋", "百里", "东郭", "西门", "南宫", "贺兰"};
    /**
     * 女孩名
     */
    private static final String GIRL = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂" +
            "娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽" +
            "琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
    /**
     * 男孩姓名
     */
    private static final String BOY = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子" +
            "杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮" +
            "翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
    /**
     * 邮箱
     */
    private static final String[] EMAIL_SUFFIX = ("@gmail.com,@yahoo.com,@msn.com,@ask.com,@live.com," +
            "@163.com,@163.net,@yeah.net,@126.com,@sina.com,@sohu.com,@yahoo.com.cn").split(",");
    /**
     * 返回手机号码
     */
    private static final String[] TEL_FIRST = ("134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156," +
            "133,153,172,178,182,183,184,187,188,198,173,177,180,181,189,191,193,199,166,175,176,185,186").split(",");
    private static final String BASE = "0123456789";
    private static final String MIX_BASE = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int USER_NAME_SIZE = SURNAME.length - 1;
    private static final int GIRL_SIZE = GIRL.length() - 2;
    private static final int TEL_SIZE = TEL_FIRST.length - 1;
    /**
     * 2000年开始(时间戳)
     */
    private static final long START_TIME = 946656000L;
    private static final long YEAR_SECONDS = 31449600L;

    public static String generate() {
        int highPos, lowPos;
        //区码，0xA0打头，从第16区开始，即0xB0=11*16=176,16~55一级汉字，56~87二级汉字
        highPos = (176 + Math.abs(RandomUtil.nextInt(72)));
        //位码，0xA0打头，范围第1~94列
        lowPos = 161 + Math.abs(RandomUtil.nextInt(94));
        byte[] bArr = new byte[2];
        bArr[0] = (new Integer(highPos)).byteValue();
        bArr[1] = (new Integer(lowPos)).byteValue();
        try {
            return new String(bArr, "GB2312");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 返回Email
     * @param lMin 最小长度
     * @param lMax 最大长度
     * @return 邮箱
     */
    public static String getEmail(int lMin, int lMax) {
        boolean isQQ = RandomUtil.nextInt(6) % 2 == 0;
        StringBuilder sb = new StringBuilder();
        if (isQQ) {
            sb.append(RandomUtil.nextInt(1, 9));
            int length = RandomUtil.nextInt(6, 9);
            for (int i = 0; i < length; i++) {
                int number = (int) (Math.random() * BASE.length());
                sb.append(BASE.charAt(number));
            }
            sb.append("@qq.com");
        } else {
            int length = RandomUtil.nextInt(lMin, lMax);
            for (int i = 0; i < length; i++) {
                int number = (int) (Math.random() * MIX_BASE.length());
                sb.append(MIX_BASE.charAt(number));
            }
            sb.append(EMAIL_SUFFIX[(int) (Math.random() * EMAIL_SUFFIX.length)]);
        }
        return sb.toString();
    }

    public static String getTel() {
        int index = RandomUtil.nextInt(TEL_SIZE);
        String first = TEL_FIRST[index];
        String second = String.valueOf(RandomUtil.nextInt(1, 888) + 10000).substring(1);
        String third = String.valueOf(RandomUtil.nextInt(1, 9100) + 10000).substring(1);
        return first + second + third;
    }

    public static UserInfo generate(int minAge, int maxAge) {
        StringBuilder builder = new StringBuilder(6);
        builder.append(SURNAME[RandomUtil.nextInt(USER_NAME_SIZE)]);
        // 产生男女比例
        int i = RandomUtil.nextInt(3);
        int j = RandomUtil.nextInt(GIRL_SIZE);
        int sex;
        if (i == 2) {
            if (j % 2 == 0) {
                builder.append(GIRL, j, j + 2);
            } else {
                builder.append(GIRL, j, j + 1).append(generate());
            }
            sex = 0;
        } else {
            if (j % 2 == 0) {
                builder.append(BOY, j, j + 2);
            } else {
                builder.append(BOY, j, j + 1).append(generate());
            }
            sex = 1;
        }
        int age = RandomUtil.nextInt(minAge, maxAge);
        // 计算出生年月
        return new UserInfo().userName(builder.toString()).sex(sex).phone(getTel()).email(getEmail(8, 15))
                .age(age).address(AddressUtil.getRoad());
    }
}
