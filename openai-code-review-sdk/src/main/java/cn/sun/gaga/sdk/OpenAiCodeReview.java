package cn.sun.gaga.sdk;
import cn.sun.gaga.sdk.domain.model.ChatCompletionRequest;
import cn.sun.gaga.sdk.domain.model.ChatCompletionSyncResponse;
import cn.sun.gaga.sdk.domain.model.Message;
import cn.sun.gaga.sdk.domain.model.Model;
import cn.sun.gaga.sdk.domain.service.impl.OpenAiCodeReviewService;
import cn.sun.gaga.sdk.infrastructure.git.GitCommand;
import cn.sun.gaga.sdk.infrastructure.openai.IOpenAI;
import cn.sun.gaga.sdk.infrastructure.openai.impl.ChatGLM;
import cn.sun.gaga.sdk.infrastructure.weixin.WeiXin;
import cn.sun.gaga.sdk.types.utils.BearerTokenUtils;
import cn.sun.gaga.sdk.types.utils.WXAccessTokenUtils;
import com.alibaba.fastjson2.JSON;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;


public class OpenAiCodeReview {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiCodeReview.class);

    // 配置配置
    private String weixin_appid = "wx3e757551ebe7133e";
    private String weixin_secret = "8a67bafda92906fe893f149ca428916e";
    private String weixin_touser = "owICi6cm1pfbDIH45eIl2P-ODwKw";
    private String weixin_template_id = "pfjekTkVdA9OpL3qli5wmu5doY68A9KvWYjl5U_5jTM";

    // ChatGLM 配置
    private String chatglm_apiHost = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
    private String chatglm_apiKeySecret = "";

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
                getEnv("WEIXIN_APPID"),
                getEnv("WEIXIN_SECRET"),
                getEnv("WEIXIN_TOUSER"),
                getEnv("WEIXIN_TEMPLATE_ID")
        );



        IOpenAI openAI = new ChatGLM(getEnv("CHATGLM_APIHOST"), getEnv("CHATGLM_APIKEYSECRET"));

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
