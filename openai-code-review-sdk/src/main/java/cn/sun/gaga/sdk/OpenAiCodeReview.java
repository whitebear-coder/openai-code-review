package cn.sun.gaga.sdk;
import cn.sun.gaga.sdk.domain.service.impl.OpenAiCodeReviewService;
import cn.sun.gaga.sdk.infrastructure.git.GitCommand;
import cn.sun.gaga.sdk.infrastructure.openai.IOpenAI;
import cn.sun.gaga.sdk.infrastructure.openai.impl.ChatGLM;
import cn.sun.gaga.sdk.infrastructure.weixin.WeiXin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OpenAiCodeReview {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiCodeReview.class);

    // 配置配置
    private static final String weixin_appid = "wx3e757551ebe7133e";
    private static final String weixin_secret = "8a67bafda92906fe893f149ca428916e";
    private static final String weixin_touser = "owICi6cm1pfbDIH45eIl2P-ODwKw";
    private static final String weixin_template_id = "pfjekTkVdA9OpL3qli5wmu5doY68A9KvWYjl5U_5jTM";

    // ChatGLM 配置
    private static final String chatglm_apiHost = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
    private static final String chatglm_apiKeySecret = "";

    // Github 配置
    private String github_review_log_uri;
    private String github_token;

    // 工程配置 - 自动获取
    private String github_project;
    private String github_branch;
    private String github_author;

    public static void main(String[] args) throws Exception {
        GitCommand gitCommand = new GitCommand(
                getEnv("GITHUB_REVIEW_LOG_URI"),
                getEnv("GITHUB_TOKEN"),
                getEnv("COMMIT_PROJECT"),
                getEnv("COMMIT_BRANCH"),
                getEnv("COMMIT_AUTHOR"),
                getEnv("COMMIT_MESSAGE")
        );

        /**
         * 项目：{{repo_name.DATA}} 分支：{{branch_name.DATA}} 作者：{{commit_author.DATA}} 说明：{{commit_message.DATA}}
         */
        WeiXin weiXin = new WeiXin(
                getEnv("WEIXIN_APPID")!=null?getEnv("WEIXIN_APPID"):weixin_appid,
                getEnv("WEIXIN_SECRET")!=null?getEnv("WEIXIN_SECRET"):weixin_secret,
                getEnv("WEIXIN_TOUSER")!=null?getEnv("WEIXIN_TOUSER"):weixin_touser,
                getEnv("WEIXIN_TEMPLATE_ID")!=null?getEnv("WEIXIN_TEMPLATE_ID"):weixin_template_id
        );



        IOpenAI openAI = new ChatGLM(getEnv("CHATGLM_APIHOST")!=null?getEnv("CHATGLM_APIHOST"):chatglm_apiHost, getEnv("CHATGLM_APIKEYSECRET")!=null?getEnv("CHATGLM_APIKEYSECRET"):chatglm_apiKeySecret);

        OpenAiCodeReviewService openAiCodeReviewService = new OpenAiCodeReviewService(gitCommand, openAI, weiXin);
        openAiCodeReviewService.exec();

        logger.info("openai-code-review done!");
    }

    private static String getEnv(String key) {
        String value = System.getenv(key);
        if (null == value || value.isEmpty()) {
            throw new RuntimeException("value is null");
        }
        return value;
    }


}
