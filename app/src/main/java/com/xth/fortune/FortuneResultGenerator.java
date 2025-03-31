package com.xth.fortune;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 占卜结果生成器
 */
public class FortuneResultGenerator {
    
    // 预设的结果集
    private static final Map<Integer, FortuneResult> PRESET_RESULTS = new HashMap<>();
    
    static {
        // 初始化一些预设的结果
        initPresetResults();
    }
    
    /**
     * 根据签号获取占卜结果
     * 
     * @param number 签号
     * @return 占卜结果
     */
    public static FortuneResult getResult(int number) {
        // 如果有预设结果，则返回预设结果
        if (PRESET_RESULTS.containsKey(number)) {
            return PRESET_RESULTS.get(number);
        }
        
        // 否则生成随机结果
        return generateRandomResult(number);
    }
    
    /**
     * 初始化预设结果
     */
    private static void initPresetResults() {
        // 第23签：宿缘相逢
        FortuneResult result23 = new FortuneResult();
        result23.setNumber(23);
        result23.setTitle("宿缘相逢");
        result23.setContent("前世修来今世缘，莫把良缘误。\n昨夜梧桐雨，今朝杨柳风。\n纵有千山万水阻，终能相守相依。");
        result23.setExplanation("此签表示有良好的缘分将至，可能是感情上的，也可能是事业上的合作伙伴。最近的人际关系将会有所突破，长久分离的人可能会重逢。\n\n过去的努力将会开花结果，耐心等待便会有好的收获。遇到困难不要退缩，坚持走下去，终会看到希望。\n\n在感情方面尤为顺利，若有心仪对象可大胆表达，成功率很高。已有伴侣的人感情会更加稳固。");
        result23.setLoveLevel(5); // 感情运：上上
        result23.setWealthLevel(2); // 财运：中
        result23.setCareerLevel(3); // 事业运：中上
        result23.setHealthLevel(4); // 健康运：上
        PRESET_RESULTS.put(23, result23);
        
        // 第8签：财源广进
        FortuneResult result8 = new FortuneResult();
        result8.setNumber(8);
        result8.setTitle("财源广进");
        result8.setContent("财源滚滚似水来，努力耕耘硕果栽。\n付出终得好回报，不枉此生辛勤忙。\n善用财富广积德，福气寿命自然长。");
        result8.setExplanation("此签预示近期财运颇佳，可能会有意外之财或投资回报。过去的付出将在近期得到回报，收入可能会有所增加。\n\n在工作方面也会有不错的发展，可能会获得领导赏识或有升职加薪的机会。不过切记，财不可贪，得财之后宜积德行善，方能福寿绵长。\n\n投资理财方面可以适当进行，但仍需谨慎，不宜投入过大。日常消费也要合理，避免铺张浪费。");
        result8.setLoveLevel(2); // 感情运：中
        result8.setWealthLevel(5); // 财运：上上
        result8.setCareerLevel(4); // 事业运：上
        result8.setHealthLevel(3); // 健康运：中上
        PRESET_RESULTS.put(8, result8);
        
        // 第42签：风雨同舟
        FortuneResult result42 = new FortuneResult();
        result42.setNumber(42);
        result42.setTitle("风雨同舟");
        result42.setContent("同舟共济显真情，风雨路上见人心。\n山重水复疑无路，柳暗花明又一村。\n携手并肩向前行，定能共创好未来。");
        result42.setExplanation("此签表示当前或即将面临一些挑战和困难，但通过与他人的合作与互助，终将度过难关。\n\n近期可能会遇到一些挫折，看似困难重重，实则是对你耐心和毅力的考验。与其独自面对，不如寻求志同道合者的帮助，齐心协力才能共渡难关。\n\n工作中注意团队协作，家庭中互相扶持，感情上也要学会包容与理解。你付出的每一分努力，终将换来柳暗花明的好结果。");
        result42.setLoveLevel(3); // 感情运：中上
        result42.setWealthLevel(2); // 财运：中
        result42.setCareerLevel(4); // 事业运：上
        result42.setHealthLevel(3); // 健康运：中上
        PRESET_RESULTS.put(42, result42);
        
        // 第65签：厄运将至
        FortuneResult result65 = new FortuneResult();
        result65.setNumber(65);
        result65.setTitle("厄运将至");
        result65.setContent("黑云压城雨欲来，一时困顿难解脱。\n暂且忍耐守本心，静待时机再前行。");
        result65.setExplanation("此签表示近期运势较为低迷，可能会遇到一些挫折与困难。工作中可能会遇到阻碍，计划难以推进。\n\n建议保持低调，不要盲目冒进或做重大决定。现阶段应当专注于稳固已有的成果，做好风险防范。\n\n身体健康方面需要特别注意，保持良好的作息习惯和饮食习惯。心情不佳时，可以通过适当的方式放松自己，保持平和的心态。");
        result65.setLoveLevel(1); // 感情运：中下
        result65.setWealthLevel(0); // 财运：下
        result65.setCareerLevel(2); // 事业运：中
        result65.setHealthLevel(1); // 健康运：中下
        PRESET_RESULTS.put(65, result65);
        
        // 第31签：财源广进
        FortuneResult result31 = new FortuneResult();
        result31.setNumber(31);
        result31.setTitle("财源广进");
        result31.setContent("春雨浇灌千树绿，财源滚滚入家门。\n付出终有回报时，不须忧心度日月。");
        result31.setExplanation("此签表示近期财运亨通，收入可能会有显著增加。可能会收到意外之财，或是投资回报丰厚。\n\n工作上的辛勤付出将得到相应的回报，可能会有加薪或奖金的机会。投资理财方面可以适当大胆一些，但仍需谨慎分析，不可盲目跟风。\n\n不过也要注意，财富增加的同时，量力而行，不要过度消费。同时可以考虑做一些慈善活动，以善行增长福报。");
        result31.setLoveLevel(3); // 感情运：中上
        result31.setWealthLevel(5); // 财运：上上
        result31.setCareerLevel(4); // 事业运：上
        result31.setHealthLevel(3); // 健康运：中上
        PRESET_RESULTS.put(31, result31);
    }
    
    /**
     * 生成随机占卜结果
     * 
     * @param number 签号
     * @return 随机生成的占卜结果
     */
    private static FortuneResult generateRandomResult(int number) {
        Random random = new Random();
        
        // 随机生成运势等级
        int loveLevel = random.nextInt(6);    // 0-5
        int wealthLevel = random.nextInt(6);  // 0-5
        int careerLevel = random.nextInt(6);  // 0-5
        int healthLevel = random.nextInt(6);  // 0-5
        
        // 根据运势等级生成签题
        String title = generateTitle(loveLevel, wealthLevel, careerLevel, healthLevel);
        
        // 生成签文和解签
        String content = generateContent(title, loveLevel, wealthLevel, careerLevel, healthLevel);
        String explanation = generateExplanation(title, loveLevel, wealthLevel, careerLevel, healthLevel);
        
        // 创建结果对象
        FortuneResult result = new FortuneResult();
        result.setNumber(number);
        result.setTitle(title);
        result.setContent(content);
        result.setExplanation(explanation);
        result.setLoveLevel(loveLevel);
        result.setWealthLevel(wealthLevel);
        result.setCareerLevel(careerLevel);
        result.setHealthLevel(healthLevel);
        
        return result;
    }
    
    /**
     * 根据运势等级生成签题
     */
    private static String generateTitle(int loveLevel, int wealthLevel, int careerLevel, int healthLevel) {
        // 计算总体运势
        int totalLevel = loveLevel + wealthLevel + careerLevel + healthLevel;
        
        // 根据总体运势选择签题
        String[] goodTitles = {"紫气东来", "鸿运当头", "吉星高照", "万事亨通", "锦绣前程"};
        String[] normalTitles = {"平稳前行", "中正安和", "静待花开", "循序渐进", "守正待时"};
        String[] badTitles = {"谨慎行事", "暂且忍耐", "避险远祸", "守中求稳", "谦卑自省"};
        
        if (totalLevel >= 14) {
            // 好运势
            return goodTitles[new Random().nextInt(goodTitles.length)];
        } else if (totalLevel >= 7) {
            // 一般运势
            return normalTitles[new Random().nextInt(normalTitles.length)];
        } else {
            // 差运势
            return badTitles[new Random().nextInt(badTitles.length)];
        }
    }
    
    /**
     * 生成签文
     */
    private static String generateContent(String title, int loveLevel, int wealthLevel, int careerLevel, int healthLevel) {
        // 简化处理，根据签题选择对应的签文
        switch (title) {
            case "紫气东来":
                return "紫气东来福满门，顺风顺水到永远。\n贵人相助共襄事，万事亨通步步欢。";
            case "鸿运当头":
                return "鸿运当头喜事多，逢凶化吉乐呵呵。\n贵人相助好运到，诸事顺利展宏图。";
            case "吉星高照":
                return "吉星高照福运来，事事顺心人欢喜。\n贵人相助添喜气，前途光明步步高。";
            case "万事亨通":
                return "万事亨通喜气满，好运连连福无边。\n心想事成皆如愿，吉祥如意乐无边。";
            case "锦绣前程":
                return "锦绣前程光明在，一步一个脚印行。\n只要努力不放弃，终将收获好前程。";
            case "平稳前行":
                return "平稳前行不惊慌，稳扎稳打有收获。\n踏实做事心安然，终将迎来好时机。";
            case "中正安和":
                return "中正安和守本心，不卑不亢行大道。\n静心修行守本分，福祸相依看淡然。";
            case "静待花开":
                return "静待花开不焦躁，适时而动顺其然。\n万物有灵皆有序，耐心等待花自开。";
            case "循序渐进":
                return "循序渐进不急躁，一步一个脚印行。\n水滴石穿非一日，功到自然成大事。";
            case "守正待时":
                return "守正待时不浮躁，厚积薄发有光明。\n时机未到需修行，时机一到显神通。";
            case "谨慎行事":
                return "谨慎行事多思量，三思而后再行动。\n小心驶得万年船，稳中求胜保平安。";
            case "暂且忍耐":
                return "暂且忍耐莫着急，守得云开见月明。\n时运不济须忍耐，待到时来运自转。";
            case "避险远祸":
                return "避险远祸保平安，明哲保身是智慧。\n暂时退让非怯懦，为了将来更长远。";
            case "守中求稳":
                return "守中求稳保平安，不贪不冒稳前行。\n急功近利多风险，稳中求进保安宁。";
            case "谦卑自省":
                return "谦卑自省修内心，退一步海阔天空。\n静心修行守本分，厚积薄发待时机。";
            default:
                return "天道酬勤心莫急，万事有时自有缘。\n顺应自然心安然，修身养性度时光。";
        }
    }
    
    /**
     * 生成解签
     */
    private static String generateExplanation(String title, int loveLevel, int wealthLevel, int careerLevel, int healthLevel) {
        StringBuilder explanation = new StringBuilder();
        
        // 总体运势解释
        explanation.append("此签")
                   .append(generateOverallExplanation(loveLevel, wealthLevel, careerLevel, healthLevel))
                   .append("\n\n");
        
        // 感情运解释
        explanation.append(generateLoveExplanation(loveLevel)).append("\n\n");
        
        // 财富运解释
        explanation.append(generateWealthExplanation(wealthLevel)).append("\n\n");
        
        // 事业运解释
        explanation.append(generateCareerExplanation(careerLevel));
        
        return explanation.toString();
    }
    
    /**
     * 生成总体运势解释
     */
    private static String generateOverallExplanation(int loveLevel, int wealthLevel, int careerLevel, int healthLevel) {
        int totalLevel = loveLevel + wealthLevel + careerLevel + healthLevel;
        
        if (totalLevel >= 14) {
            return "表示近期运势非常不错，多个方面都有好的表现，是个上上签。积极主动把握机会，能够取得更好的成果。";
        } else if (totalLevel >= 7) {
            return "表示近期运势平稳，各方面发展有好有坏，是个中签。保持平和心态，稳步前行，能够维持良好状态。";
        } else {
            return "表示近期运势较为低迷，可能会遇到一些挫折与困难，是个下签。建议保持低调，不要盲目冒进，做好风险防范。";
        }
    }
    
    /**
     * 生成感情运解释
     */
    private static String generateLoveExplanation(int loveLevel) {
        switch (loveLevel) {
            case 5:
                return "感情方面运势极佳，单身的人有机会遇到理想伴侣，是段良缘；有伴侣的人感情会更加甜蜜和谐，关系更进一步。";
            case 4:
                return "感情方面运势不错，单身的人桃花运旺盛，容易吸引异性；有伴侣的人感情稳定，可能会有令人惊喜的浪漫经历。";
            case 3:
                return "感情方面运势较好，单身的人可能会遇到让自己心动的人；有伴侣的人感情平稳，偶有小摩擦但很快能够解决。";
            case 2:
                return "感情方面运势一般，单身的人异性缘普通，需要主动一些才有机会；有伴侣的人关系稳定，但需要更多的沟通和理解。";
            case 1:
                return "感情方面运势稍差，单身的人可能会遇到一些不合适的人；有伴侣的人可能会有一些误会或冲突，需要耐心处理。";
            case 0:
                return "感情方面运势较差，单身的人桃花运低迷，很难遇到合适的人；有伴侣的人可能会遇到一些严重的问题，需要双方共同努力解决。";
            default:
                return "感情方面运势一般，维持稳定状态，不宜做出重大改变。";
        }
    }
    
    /**
     * 生成财富运解释
     */
    private static String generateWealthExplanation(int wealthLevel) {
        switch (wealthLevel) {
            case 5:
                return "财运极佳，可能会有意外之财或是投资回报丰厚。工作上的辛勤付出将得到丰厚回报，可能有加薪或奖金。";
            case 4:
                return "财运不错，收入稳定且有所增长。投资方面可以尝试一些有把握的项目，有望获得不错的回报。";
            case 3:
                return "财运较好，收入能够满足日常开支，并有一定结余。投资方面保持谨慎，选择风险较低的项目为宜。";
            case 2:
                return "财运一般，收入稳定但增长有限。投资方面宜保守，避免冒险，以保值为主要目标。";
            case 1:
                return "财运稍差，可能会有一些额外开支或是投资不顺。建议控制支出，避免非必要的花销。";
            case 0:
                return "财运较差，可能会遇到资金紧张或是投资亏损的情况。建议严格控制预算，避免任何风险投资。";
            default:
                return "财运一般，收入支出平衡，宜量入为出，避免冒险。";
        }
    }
    
    /**
     * 生成事业运解释
     */
    private static String generateCareerExplanation(int careerLevel) {
        switch (careerLevel) {
            case 5:
                return "事业运极佳，可能会有升职加薪的机会，或是项目取得重大成功。你的才能将得到充分发挥和认可。";
            case 4:
                return "事业运不错，工作顺利，能够出色完成任务并得到上级赏识。可能会有新的发展机会。";
            case 3:
                return "事业运较好，工作进展顺利，虽有小困难但能够克服。保持积极态度，会有不错的表现。";
            case 2:
                return "事业运一般，工作稳定但没有太大突破。踏实做好本职工作，积累经验和能力，等待更好的机会。";
            case 1:
                return "事业运稍差，工作中可能会遇到一些挫折或挑战。需要耐心面对，避免冲动决策。";
            case 0:
                return "事业运较差，工作中可能会遇到较大的困难或阻碍。建议低调行事，避免与人冲突，专注解决问题。";
            default:
                return "事业运一般，工作稳定，宜守不宜攻，踏实做好当前工作。";
        }
    }
}
 